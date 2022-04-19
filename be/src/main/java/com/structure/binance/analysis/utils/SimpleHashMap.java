package com.structure.binance.analysis.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
This is a very simple hashmap. its support direct mapping for lowercase string.
This map doesn't support resizing
collision is possible, because of mod we may have many numbers have the same mod we can decrease it as possible
by having very large array but it will be possible too
*/
public class SimpleHashMap<K, V> {
    List<LinkedList<V>> dataArray;
    int capacity;

    public SimpleHashMap(int capacity) {
        if (capacity < 1)
            capacity = 16;
        this.capacity = capacity;
        dataArray = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            dataArray.add(new LinkedList<>());
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        int v = HashingUtil.compute_hash(key.toString().toLowerCase(), capacity);
        if (!dataArray.get(v).isEmpty()) {
            int i = dataArray.get(v).indexOf(value);
            if (i >= 0)
                dataArray.get(v).set(0, value);
        } else {
            dataArray.get(v).add(value);
        }
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        int v = HashingUtil.compute_hash(key.toString().toLowerCase(), capacity);
        return dataArray.get(v).getFirst();
    }

    public List<V> values() {
        return dataArray.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
    }
}
