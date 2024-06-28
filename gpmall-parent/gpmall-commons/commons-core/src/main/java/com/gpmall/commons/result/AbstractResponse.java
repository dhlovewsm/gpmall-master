package com.gpmall.commons.result;

import java.io.Serializable;

public class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 750558974450580L;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
