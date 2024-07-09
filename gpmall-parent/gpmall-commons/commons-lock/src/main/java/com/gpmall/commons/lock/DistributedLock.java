package com.gpmall.commons.lock;


import com.gpmall.commons.lock.extension.annotation.LockSpi;

import java.util.concurrent.TimeUnit;

@LockSpi(value = "redis")
public interface DistributedLock {


    void lock(String key) throws DistributeLockException;


    boolean tryLock(String key) throws DistributeLockException;


    void lock(String lockKey, TimeUnit unit, int timeout) throws DistributeLockException;



    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributeLockException;


    void unlock(String lockKey) throws DistributeLockException;

}
