package com.yliu.algorithm.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class LRUCache<K, V> implements Iterable<K> {
    int MAX = 3;
    LinkedHashMap<K,V> map = new LinkedHashMap<>();
    public void cache(K key, V value){
        if (map.containsKey(key)){
            map.remove(key);
        }else if (map.size() >= MAX){
            Iterator<K> it = map.keySet().iterator();
            K first = it.next();
            map.remove(first);
        }
        map.put(key,value);
    }

    @Override
    public Iterator<K> iterator() {
        Iterator<K> it = map.keySet().iterator();
        return new Iterator<K>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public K next() {
                return it.next();
            }
        };
    }
}
