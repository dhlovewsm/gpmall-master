package com.gpmall.commons.lock.impl.zk;

public class LockBackGroundConf {


    private Long frequency = 60 * 60L;


    private Long beforeTime = 24* 60 * 60L;


    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Long getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(Long beforeTime) {
        this.beforeTime = beforeTime;
    }
}
