/*
 * Copyright (c) 2013, Matthew Bertolini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of CamClient nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultivaluedHashMap)) return false;

        MultivaluedHashMap that = (MultivaluedHashMap) o;

        if (map != null ? !map.equals(that.map) : that.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return map != null ? map.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
