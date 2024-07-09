package com.gpmall.commons.tool.redisconfig;

import lombok.Data;

@Data
public class RedissonPoolProperties {

    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWaitTime;

}
