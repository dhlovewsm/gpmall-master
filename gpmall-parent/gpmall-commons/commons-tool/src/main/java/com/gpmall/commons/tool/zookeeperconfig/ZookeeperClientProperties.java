package com.gpmall.commons.tool.zookeeperconfig;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lock.zookeeper", ignoreInvalidFields = true)
public class ZookeeperClientProperties {

    private String zkHosts;

    private int sessionTimeout = 30000;

    private int connectionTimeout = 30000;


    private boolean singleton = true;

    private String namespace;


    @Override
    public String toString() {
        return "ZookeeperClientProperties{" +
                "zkHosts='" + zkHosts + '\'' +
                ", sessionTimeout=" + sessionTimeout +
                ", connectionTimeout=" + connectionTimeout +
                ", singleton=" + singleton +
                ", namespace='" + namespace + '\'' +
                '}';
    }
}
