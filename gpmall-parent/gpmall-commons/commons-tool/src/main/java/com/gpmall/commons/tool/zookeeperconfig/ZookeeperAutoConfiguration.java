package com.gpmall.commons.tool.zookeeperconfig;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ZookeeperClientProperties.class)
@ConditionalOnClass(value = ZookeeperClientProperties.class)
public class ZookeeperAutoConfiguration {

    public static final Logger logger = LoggerFactory.getLogger(ZookeeperAutoConfiguration.class);

    @Autowired
    ZookeeperClientProperties zookeeperClientProperties;


    @Bean
    public CuratorFrameworkClient curatorFrameworkClient(){
        logger.info(zookeeperClientProperties.toString());
        return new CuratorFrameworkClient(zookeeperClientProperties);
    }

}
