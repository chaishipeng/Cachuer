package com.chai.cache.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/2/9.
 */
public class HashMapKvStore<T> implements KVStore<T> {

    private Map<String, T> map = new HashMap();

    @Override
    public T get(String key) {
        return map.get(key);
    }

    @Override
    public boolean put(String key, T o) {
        map.put(key, o);
        return false;
    }

    @Override
    public List<T> values() {
        return new ArrayList<T>(map.values());
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }
}
