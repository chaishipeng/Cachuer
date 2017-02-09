package com.chai.cache.pool;

import com.chai.cache.common.Cache;
import com.chai.cache.common.CacheKey;
import com.chai.cache.common.DefaultCache;
import com.chai.cache.store.HashMapKvStore;
import com.chai.cache.store.KVStore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by chaishipeng on 2017/2/9.
 */
public class SimpleCachePool extends AbstractCachePool {
    @Override
    String getRealKey(CacheKey cacheKey) {
        return null;
    }

    @Override
    boolean isCanPut(CacheKey cacheKey) {
        return false;
    }

    @Override
    boolean canDrop(Cache cache) {
        return false;
    }

    @Override
    Cache buildCache(CacheKey cacheKey, Object value) {
        Cache cache = new DefaultCache();
        cache.setValue(value);
        cache.setCacheKey(cacheKey);
        return cache;
    }

    @Override
    KVStore<Cache> buildKVStore() {
        return new HashMapKvStore();
    }

    @Override
    BlockingQueue<CacheKey> buildClearExecuteQueue() {
        return new LinkedBlockingDeque<CacheKey>();
    }
}
