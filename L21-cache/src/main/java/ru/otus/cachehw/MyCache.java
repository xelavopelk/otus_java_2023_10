package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    WeakHashMap<K, V> map = new WeakHashMap<>();
    List<HwListener<K, V>> listeners = new ArrayList<>() {
    };
    @Override
    public void put(K key, V value) {
        //listeners.forEach(l -> l.notify(key, value, "put"));
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        listeners.forEach(l -> l.notify(key, map.get(key), "remove"));
        map.remove(key);
    }

    @Override
    public V get(K key) {
        listeners.forEach(l -> l.notify(key, map.get(key), "get"));
        return map.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
    public Integer size() {
        return map.size();
    }
}
