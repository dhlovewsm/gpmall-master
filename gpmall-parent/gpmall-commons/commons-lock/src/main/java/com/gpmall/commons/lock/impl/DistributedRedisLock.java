package com.gpmall.commons.lock.impl;

import com.gpmall.commons.lock.ApplicationContextUtils;
import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.DistributeLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLock implements DistributedLock {


    private RedissonClient redissonClient;

    public DistributedRedisLock(RedissonClient redissonClient) {
        this.redissonClient = ApplicationContextUtils.getClass(RedissonClient.class);
    }
    @Override
    public void lock(String key) throws DistributeLockException {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public boolean tryLock(String key) throws DistributeLockException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributeLockException {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributeLockException {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            throw new DistributeLockException("redis加锁异常：", e);
        }
    }

    @Override
    public void unlock(String lockKey) throws DistributeLockException {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock != null && lock.isHeldByCurrentThread()){
            lock.unlock();
        }
    }
}
