package com.mattbertolini.camclient.net.support.urlconnection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Matt Bertolini
 */
public class MultiValuedHashMapTest {
    @Test
    public void testAdd() {
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        expected.put("key", Collections.singletonList("value"));

        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.add("key", "value");
        assertEquals(expected, map);
    }

    @Test
    public void testAddAllList() {
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        expected.put("key", Arrays.asList("one", "two", "three"));

        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.addAll("key", Arrays.asList("one", "two", "three"));
        assertEquals(expected, map);
    }

    @Test
    public void testAddAllVarargs() {
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        expected.put("key", Arrays.asList("one", "two", "three"));

        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.addAll("key", "one", "two", "three");
        assertEquals(expected, map);
    }

    @Test
    public void testAddFirst() {
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        expected.put("key", Arrays.asList("value", "one", "two", "three"));

        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.addAll("key", "one", "two", "three");
        map.addFirst("key", "value");
        assertEquals(expected, map);
    }

    @Test
    public void testGetFirst() {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.addAll("key", "one", "two", "three");
        String value = map.getFirst("key");
        assertEquals("one", value);
    }

    @Test
    public void testPutSingle() {
        Map<String, List<String>> expected = new HashMap<String, List<String>>();
        expected.put("key", Collections.singletonList("value"));

        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.putSingle("key", "value");
        assertEquals(expected, map);
    }

    @Test
    public void testSize() {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.putSingle("key", "value");
        assertEquals(1, map.size());
    }
}
