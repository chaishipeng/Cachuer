package com.chai.cache.store;

import java.util.List;

/**
 * Created by chaishipeng on 2017/2/8.
 */
public interface KVStore<T> {

    T get(String key);

    boolean put(String key, T o);

    List<T> values();

    void remove(String key);

}
