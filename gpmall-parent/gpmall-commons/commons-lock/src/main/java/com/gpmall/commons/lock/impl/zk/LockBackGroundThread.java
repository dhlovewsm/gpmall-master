package com.gpmall.commons.lock.impl.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class LockBackGroundThread extends Thread{


    private Logger logger = LoggerFactory.getLogger(LockBackGroundThread.class);

    CuratorFramework client;


    protected LockBackGroundThread(CuratorFramework client) {
        this.client = client;
        this.setDaemon(true);
        this.setName("ZkMuteDistributedLock---background");
    }


    @Override
    public void run() {
        super.run();
        try {
            while (true){
                LockBackGroundConf conf = new LockBackGroundConf();
                deleteInvalidNode(conf);

                Thread.currentThread().wait(conf.getFrequency() * 1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteInvalidNode(LockBackGroundConf conf) throws Exception{

        String proDir = ZkMutexDistributedLockFactory.LOCK_PATH + ZkMutexDistributedLockFactory.projectName;
        Stat exitDir = client.checkExists().forPath(proDir);
        if (exitDir == null){
            logger.error("根目录尚未创建，本次清理结束--" + proDir);
            return;
        }
        List<String> paths = client.getChildren().forPath(proDir);
        Date date = new Date();
        paths.forEach(currentPath ->{
            try {
                Stat stat = new Stat();
                if (stat.getMtime() < (date.getTime() - (conf.getBeforeTime() * 1000)) && stat.getNumChildren() == 0){
                    client.delete().forPath(proDir + currentPath);
                    logger.info("删除路径： " + proDir + currentPath);
                }
            } catch (Exception e){
                logger.error("删除节点失败：" + e);
            }
        });

    }
}
