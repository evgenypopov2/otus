package ru.otus.cachehw;

import java.util.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private static final int CACHE_MAX_SIZE = 10;

    private Map<K, V> cacheMap = new WeakHashMap<>();
    private List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        if (cacheMap.size() == CACHE_MAX_SIZE) {
            Iterator<K> iterator = cacheMap.keySet().iterator();
            if (iterator.hasNext()) {
                cacheMap.remove(iterator.next());
            }
        }
        listeners.forEach(listener -> listener.notify(key, value, "put"));
        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        listeners.forEach(listener -> listener.notify(key, cacheMap.get(key), "remove"));
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        var val = cacheMap.get(key);
        listeners.forEach(listener -> listener.notify(key, val, "get"));
        return val;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    @Override
    public int size() {
        return cacheMap.size();
    }
}
