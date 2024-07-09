package com.gpmall.commons.lock.impl.zk_v1;

import com.gpmall.commons.lock.ApplicationContextUtils;
import com.gpmall.commons.lock.DistributeLockException;
import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.tool.zookeeperconfig.CuratorFrameworkClient;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DistributedZookeeperReentrantLock implements DistributedLock {

    public CuratorFrameworkClient curatorFrameworkClient;

    public ConcurrentHashMap<Thread, InterProcessMutex> locksMap = new ConcurrentHashMap<>();


    public DistributedZookeeperReentrantLock(CuratorFrameworkClient curatorFrameworkClient) {
        this.curatorFrameworkClient = ApplicationContextUtils.getClass(CuratorFrameworkClient.class);
    }
    @Override
    public void lock(String path) throws DistributeLockException {
        if (locksMap.get(Thread.currentThread()) == null){
            InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFrameworkClient.getZookeeperClient(), path);

            try {
                interProcessMutex.acquire();
            } catch (Exception ex){
                throw new DistributeLockException("zk-acquire加锁异常：", ex);
            }

            locksMap.put(Thread.currentThread(), interProcessMutex);
        } else {
            InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
            try {
                interProcessMutex.acquire();
            } catch (Exception ex){
                throw new DistributeLockException("zk-acquire加锁异常：", ex);
            }
        }
    }

    @Override
    public boolean tryLock(String key) throws DistributeLockException {
        return tryLock(key, null, -1, -1);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) throws DistributeLockException {
        tryLock(lockKey, unit, timeout, 0);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws DistributeLockException {
        if (locksMap.get(Thread.currentThread()) == null){
            InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFrameworkClient.getZookeeperClient(), lockKey);

            try {
                boolean acquired = interProcessMutex.acquire(waitTime, unit);
                if (acquired){
                    locksMap.put(Thread.currentThread(), interProcessMutex);
                }else {
                    interProcessMutex = null;
                }
                return acquired;
            } catch (Exception e){
                throw new DistributeLockException("zk-acquire(waitTime, unit)加锁异常：", e);
            }
        }else {
            InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
            try {
                return interProcessMutex.acquire(waitTime, unit);
            } catch (Exception e){
                throw new DistributeLockException("zk-acquire(waitTime, unit)加锁异常：", e);
            }
        }
    }

    @Override
    public void unlock(String lockKey) throws DistributeLockException {
        InterProcessMutex interProcessMutex = locksMap.get(Thread.currentThread());
        if (interProcessMutex != null){
            try {
                interProcessMutex.release();
            } catch (Exception e){
                throw new DistributeLockException("zk-release解锁异常：", e);
            }finally {
                locksMap.remove(Thread.currentThread());
            }
        }
    }
}
