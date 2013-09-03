package com.mattbertolini.camclient.net.support.urlconnection;

import java.util.List;
import java.util.Map;

/**
 * @param <K>
 * @param <V>
 * @author Matt Bertolini
 */
public interface MultivaluedMap<K, V> extends Map<K, List<V>> {
    void add(K key, V value);
    void addAll(K key, List<V> values);
    void addAll(K key, V... values);
    void addFirst(K key, V value);
    V getFirst(K key);
    void putSingle(K key, V value);
}
