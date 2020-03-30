package com.yliu.algorithm.list;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU
 * https://juejin.im/post/5d382c8de51d4577407b1e26
 */
public class LRUCache<k,v> extends LinkedHashMap<k,v> {
    private final int MAX_CACHE_SIZE;

    public LRUCache(int cacheSize) {
        // 使用构造方法 public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
        // initialCapacity、loadFactor都不重要
        // accessOrder要设置为true，按访问排序
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        MAX_CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        // 超过阈值时返回true，进行LRU淘汰
        return size() > MAX_CACHE_SIZE;
    }
}
