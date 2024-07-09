package com.gpmall.commons.tool.redisconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.redisson", ignoreUnknownFields = false)
public class RedissonProperties {

    private String address;

    private int database;


    private int timeout;

    private String password;

    private RedissonPoolProperties pool;
}
