package com.chai.cache.common;

/**
 * Created by chaishipeng on 2017/2/7.
 */
public interface Cache {

    Object getValue();

    void setValue(Object value);

    long getLastUseTime();

    void setLastUseTime(long lastUseTime);

    boolean isInQueue();

    void setInQueue(boolean flag);

    CacheKey getKey();

    void setKey(CacheKey cacheKey);

    void setCacheKey(CacheKey cacheKey);

}
