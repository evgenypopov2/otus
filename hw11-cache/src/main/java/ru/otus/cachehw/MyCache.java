package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private static final int CACHE_MAX_SIZE = 15;

    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final Map<K, ValueWrapper<V>> cacheMap = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    private static class ValueWrapper<V> {
        private V value;
        private Date time;
        protected ValueWrapper(V value) {
            this.value = value;
            this.time = new Date();
        }
    }

    @Override
    public void put(K key, V value) {
        if (cacheMap.size() == CACHE_MAX_SIZE) {
            K keyToRemove = null;
            Date minTime = new Date();
            // find earliest
            for (Map.Entry<K, ValueWrapper<V>> element : cacheMap.entrySet()) {
                if (element.getValue().time.before(minTime)) {
                    minTime = element.getValue().time;
                    keyToRemove = element.getKey();
                }
            }
            if (keyToRemove == null) {  // if nothing found then remove first
                Iterator<K> iterator = cacheMap.keySet().iterator();
                if (iterator.hasNext()) {
                    keyToRemove = iterator.next();
                }
            }
            if (keyToRemove != null) {
                cacheMap.remove(keyToRemove);
            }
        }
        cacheMap.put(key, new ValueWrapper<>(value));
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = getValueFromCache(key);
        cacheMap.remove(key);
        notifyListeners(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = getValueFromCache(key);
        notifyListeners(key, value, "get");
        return value;
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

    private void notifyListeners(K key, V value, String action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                logger.error("Error calling listener: {}", e.getMessage());
            }
        });
    }

    private V getValueFromCache(K key) {
        V value = null;
        ValueWrapper<V> valueWrapper = cacheMap.get(key);
        if (valueWrapper != null) {
            value = valueWrapper.value;
        }
        return value;
    }
}
