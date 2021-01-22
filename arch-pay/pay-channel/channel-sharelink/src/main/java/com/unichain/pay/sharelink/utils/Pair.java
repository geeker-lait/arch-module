package com.unichain.pay.sharelink.utils;

public final class Pair<K, V> {
    public final K key;
    public final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K k, V v) {
        return new Pair(k, v);
    }

    public K first() {
        return key;
    }

    public V second() {
        return value;
    }
}
