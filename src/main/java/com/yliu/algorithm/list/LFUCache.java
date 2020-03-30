package com.yliu.algorithm.list;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * LFU
 */
public class LFUCache<k,v> {
    private final int capacity;

    private Map<k, v> cache = new HashMap<>();

    private Map<k, HitRate> count = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(k key, v value) {
        v v = cache.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                removeElement();
            }
            count.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            addHitCount(key);
        }
        cache.put(key, value);
    }

    public v get(k key) {
        v value = cache.get(key);
        if (value != null) {
            addHitCount(key);
            return value;
        }
        return null;
    }

    //移除元素
    private void removeElement() {
        HitRate hr = Collections.min(count.values());
        cache.remove(hr.key);
        count.remove(hr.key);
    }

    //更新访问元素状态
    private void addHitCount(k key) {
        HitRate hitRate = count.get(key);
        hitRate.hitCount = hitRate.hitCount + 1;
        hitRate.lastTime = System.nanoTime();
    }

    //内部类
    class HitRate implements Comparable<HitRate> {
        private k key;
        private int hitCount;
        private long lastTime;

        private HitRate(k key, int hitCount, long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        @Override
        public int compareTo(HitRate o) {
            int compare = Integer.compare(this.hitCount, o.hitCount);
            return compare == 0 ? Long.compare(this.lastTime, o.lastTime) : compare;
        }
    }
}
