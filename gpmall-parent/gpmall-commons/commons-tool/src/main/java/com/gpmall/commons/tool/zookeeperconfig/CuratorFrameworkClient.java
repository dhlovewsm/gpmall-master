package com.gpmall.commons.tool.zookeeperconfig;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorFrameworkClient {


    ZookeeperClientProperties zookeeperClientProperties;


    CuratorFramework curatorFramework = null;


    public CuratorFrameworkClient(ZookeeperClientProperties zookeeperClientProperties) {
        this.zookeeperClientProperties = zookeeperClientProperties;
    }


    public CuratorFramework getZookeeperClient() {
        if (curatorFramework == null) {
            return createCuratorFramework();
        } else {
            if (zookeeperClientProperties.isSingleton()){
                return curatorFramework;
            } else {
                return createCuratorFramework();
            }
        }
    }

    private CuratorFramework createCuratorFramework() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 100, 2000);
        curatorFramework = (CuratorFramework) CuratorFrameworkFactory.builder()
                .connectString(zookeeperClientProperties.getZkHosts())
                .sessionTimeoutMs(zookeeperClientProperties.getSessionTimeout())
                .namespace(zookeeperClientProperties.getNamespace())
                .retryPolicy(retryPolicy);

        curatorFramework.start();
        return curatorFramework;
    }

}
