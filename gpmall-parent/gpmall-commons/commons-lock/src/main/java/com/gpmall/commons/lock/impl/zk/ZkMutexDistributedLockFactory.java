package com.gpmall.commons.lock.impl.zk;

import com.gpmall.commons.lock.DistributeLockException;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkMutexDistributedLockFactory {


    private static Logger logger = LoggerFactory.getLogger(ZkMutexDistributedLockFactory.class);

    protected final static String LOCK_PATH = "/gpcommons_lock/curator_recipes_lock/";

    protected static String projectName;

    static CuratorFramework client = null;


    static synchronized InterProcessMutex getInterProcessMutex(String lockKey) {

        if (client == null){
            init();
        }
        InterProcessMutex mutexLock = new InterProcessMutex(client, LOCK_PATH + projectName + lockKey);
        return mutexLock;
    }


    static synchronized  CuratorFramework getCuratorClient() throws DistributeLockException {
        if (client == null){
            init();
        }

        return client;
    }


    private static synchronized void init(){
        if (client == null){
            String IPAndPort = "";

            String projectName = "";

            if (StringUtils.isEmpty(projectName) || StringUtils.isEmpty(IPAndPort)){
                logger.error("zk锁启动失败缺少配置--IP端口号或者项目名");
                throw new RuntimeException("zk锁启动异常--缺少配置--IP和端口号或者项目名");
            }

            ZkMutexDistributedLockFactory.projectName = projectName + "/";
            client = CuratorFrameworkFactory.builder()
                    .connectString(IPAndPort)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

            client.start();

            LockBackGroundThread backGroundThread = new LockBackGroundThread(client);
            backGroundThread.start();
        }
    }



}
