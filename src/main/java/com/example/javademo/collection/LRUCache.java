package com.example.javademo.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> implements Iterable<K> {

    private final LinkedHashMap<K,V> cache = new LinkedHashMap<>();

    private static int MAX = 3;

    public void cache(K k,V v){
        if (cache.containsKey(k)) {
            cache.remove(k);
        }else if (cache.size() >= MAX) {
            Iterator<K> iterator = cache.keySet().iterator();
            K first = iterator.next();
            cache.remove(first);
        }
        cache.put(k,v);
    }

    @Override
    public Iterator<K> iterator() {
        Iterator<Map.Entry<K, V>> iterator = cache.entrySet().iterator();
        return new Iterator<K>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public K next() {
                return iterator.next().getKey();
            }
        };
    }
}
