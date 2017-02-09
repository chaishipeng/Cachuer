package com.chai.cache.common;

/**
 * Created by chaishipeng on 2017/2/7.
 */
public class DefaultCache implements Cache{

    private Object value;

    private long lastUseTime;

    public void setValue(Object value) {
        this.value = value;
    }

    public long getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(long lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    @Override
    public boolean isInQueue() {
        return false;
    }

    @Override
    public void setInQueue(boolean flag) {

    }

    @Override
    public CacheKey getKey() {
        return null;
    }

    @Override
    public void setCacheKey(CacheKey cacheKey) {

    }

    @Override
    public Object getValue() {
        return null;
    }
}
