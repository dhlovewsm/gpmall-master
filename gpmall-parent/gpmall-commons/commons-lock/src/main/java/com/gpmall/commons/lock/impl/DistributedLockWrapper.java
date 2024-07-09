package com.gpmall.commons.lock.impl;

import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.DistributeLockException;

import java.util.concurrent.TimeUnit;

public class DistributedLockWrapper implements DistributedLock {


    private DistributedLock distributedLock;


    public DistributedLockWrapper(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }


    @Override
    public void lock(String key) throws DistributeLockException {
        distributedLock.lock(key);
    }

    @Override
    public boolean tryLock(String key) throws DistributeLockException {
        return distributedLock.tryLock(key);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributeLockException {
        distributedLock.lock(lockKey, unit, timeout);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributeLockException {
        return distributedLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }

    @Override
    public void unlock(String lockKey) throws DistributeLockException {
        distributedLock.unlock(lockKey);
    }
}
