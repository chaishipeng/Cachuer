package com.chai.cache.pool;

import com.chai.cache.common.Cache;
import com.chai.cache.common.CacheKey;
import com.chai.cache.store.KVStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by chaishipeng on 2017/2/7.
 */
public abstract class AbstractCachePool implements CachePool{

    private long mainThreadTimeInteval = 1 * 1000;

    private volatile boolean runFlag = true;

    private KVStore<Cache> kvStore;

    private BlockingQueue<CacheKey> clearExecuteQueue;

    public AbstractCachePool() {
        startMainThread();
        startClearThread();
    }

    protected void startMainThread() {

        Thread mainThread = new Thread(new Runnable(){
            @Override
            public void run() {

                while(runFlag) {

                    long startTimeLong = System.currentTimeMillis();
                    for (Cache cache : kvStore.values()) {

                        boolean isInUse = isInUse(cache);
                        if (!isInUse) {
                            executeCacheAfterNotUse(cache);
                        }

                    }
                    long endTimeLong = System.currentTimeMillis();
                    long sleepTime = mainThreadTimeInteval - (endTimeLong - startTimeLong);
                    try {
                        Thread.currentThread().sleep(sleepTime <= 0 ? sleepTime/10 : sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        mainThread.setName("CachePool-MainThread");
        mainThread.start();
    }

    protected void startClearThread() {
        Thread clearThread = new Thread(new Runnable(){
            @Override
            public void run() {
                while(runFlag) {
                    try {
                        CacheKey cacheKey = clearExecuteQueue.take();
                        clearCache(cacheKey);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        clearThread.setName("CachePool-ClearThread");
        clearThread.start();
    }

    protected void clearCache(CacheKey cachekey) {
        kvStore.remove(getRealKey(cachekey));
    }

    public Object getCache(CacheKey cacheKey){
        String key = getRealKey(cacheKey);
        Cache cache = kvStore.get(key);
        if (cache == null) {
            return null;
        }
        boolean isInUse = isInUse(cache);
        if (isInUse) {
            cache.setLastUseTime(System.currentTimeMillis());
            return cache.getValue();
        }
        executeCacheAfterNotUse(cache);
        return null;
    }

    private boolean isInUse(Cache cache) {
        if (cache.isInQueue()) {
            return false;
        }
        return canDrop(cache);
    }

    private void executeCacheAfterNotUse(Cache cache) {
        if (cache.isInQueue()) {
            return;
        }
        cache.setInQueue(true);
        clearExecuteQueue.offer(cache.getKey());
    }

    abstract String getRealKey(CacheKey cacheKey);

    public boolean putCache(CacheKey cacheKey, Object obj){
        boolean canPut = isCanPut(cacheKey);
        if (!canPut) {
            return false;
        }
        Cache cache = buildCache(cacheKey, obj);
        cache.setLastUseTime(System.currentTimeMillis());
        return kvStore.put(getRealKey(cacheKey), cache);
    }

    abstract boolean isCanPut(CacheKey cacheKey);

    abstract boolean canDrop(Cache cache);

    abstract Cache buildCache(CacheKey cacheKey, Object value);

    abstract KVStore<Cache> buildKVStore();

    abstract BlockingQueue<CacheKey> buildClearExecuteQueue();

    public void clear(){

    }

}
