package com.chai.cache.pool;

import com.chai.cache.common.CacheKey;

/**
 * Created by chaishipeng on 2017/2/7.
 */
public interface CachePool {

    Object getCache(CacheKey cacheKey);

    boolean putCache(CacheKey cacheKey, Object obj);

    void clear();

}
