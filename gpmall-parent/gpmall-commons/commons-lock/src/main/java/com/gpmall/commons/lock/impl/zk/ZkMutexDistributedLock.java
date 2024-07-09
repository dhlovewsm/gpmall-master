package com.gpmall.commons.lock.impl.zk;

import com.gpmall.commons.lock.DistributeLockException;
import com.gpmall.commons.lock.DistributedLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public class ZkMutexDistributedLock implements DistributedLock {



    private InterProcessMutex interProcessMutex;

    private int defaultTimeoutSeconds = 30;


    @Override
    public void lock(String key) throws DistributeLockException {
        try {
            interProcessMutex.acquire();;
        } catch (Exception e){
            throw new DistributeLockException("zk加锁异常：" + e);
        }
    }

    @Override
    public boolean tryLock(String key) throws DistributeLockException {
        try {
            init(key);
            return interProcessMutex.acquire(defaultTimeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e){
            throw new DistributeLockException("zk加锁异常：" + e);
        }
    }

    private void init(String lockKey) {
        if (interProcessMutex == null){
            interProcessMutex = ZkMutexDistributedLockFactory.getInterProcessMutex(lockKey);
        }
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributeLockException {
        try {
            init(lockKey);
            interProcessMutex.acquire(timeout, unit);
        } catch (Exception e){
            throw new DistributeLockException("zk加锁异常：" + e);
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributeLockException {
        try {
            init(lockKey);
            return interProcessMutex.acquire(waitTime, unit);
        } catch (Exception e){
            throw new DistributeLockException("zk加锁异常：" + e);
        }
    }

    @Override
    public void unlock(String lockKey) throws DistributeLockException {
        try {
            init(lockKey);
            interProcessMutex.release();
        } catch (Exception e){
            throw new DistributeLockException("zk解锁异常：" + e);
        }
    }



}
