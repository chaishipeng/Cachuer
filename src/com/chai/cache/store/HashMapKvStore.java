package com.chai.cache.store;

import com.chai.cache.common.Cache;

import java.util.List;

/**
 * Created by chaishipeng on 2017/2/9.
 */
public class HashMapKvStore implements KVStore<Cache> {
    @Override
    public Cache get(String key) {
        return null;
    }

    @Override
    public boolean put(String key, Cache o) {
        return false;
    }

    @Override
    public List<Cache> values() {
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
