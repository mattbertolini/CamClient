package com.mattbertolini.camclient.net.support.urlconnection;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matt Bertolini
 */
public class MultivaluedHashMap<K, V> implements MultivaluedMap<K, V> {
    private Map<K, List<V>> map;

    public MultivaluedHashMap() {
        this.map = new HashMap<K, List<V>>();
    }

    public MultivaluedHashMap(Map<K, List<V>> map) {
        if(map == null) {
            throw new IllegalArgumentException("Input is null.");
        }
        this.map = new HashMap<K, List<V>>(map);
    }

    public MultivaluedHashMap(MultivaluedHashMap<K, V> map) {
        if(map == null) {
            throw new IllegalArgumentException("Input is null.");
        }
        this.map = map;
    }

    @Override
    public void add(K key, V value) {
        List<V> values = this.getValues(key);
        if(value != null) {
            values.add(value);
        }
    }

    @Override
    public void addAll(K key, List<V> values) {
        if(values == null) {
            throw new IllegalArgumentException("List is null.");
        }
        if(values.isEmpty()) {
            return;
        }
        List<V> list = this.getValues(key);
        for(V value : values) {
            if(value != null) {
                list.add(value);
            }
        }
    }

    @Override
    public void addAll(K key, V... values) {
        if(values == null) {
            throw new IllegalArgumentException("Array is null.");
        }
        if(values.length == 0) {
            return;
        }
        List<V> list = this.getValues(key);
        for(V value : values) {
            if(value != null) {
                list.add(value);
            }
        }
    }

    @Override
    public void addFirst(K key, V value) {
        List<V> values = this.getValues(key);
        if(value != null) {
            values.add(0, value);
        }
    }

    @Override
    public V getFirst(K key) {
        List<V> list = this.map.get(key);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void putSingle(K key, V value) {
        List<V> values = this.getValues(key);
        values.clear();
        if(value != null) {
            values.add(value);
        }
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public List<V> get(Object key) {
        return this.map.get(key);
    }

    @Override
    public List<V> put(K key, List<V> value) {
        return this.map.put(key, value);
    }

    @Override
    public List<V> remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    private List<V> getValues(K key) {
        List<V> list = this.map.get(key);
        if(list == null) {
            list = new LinkedList<V>();
            this.map.put(key, list);
        }
        return list;
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
