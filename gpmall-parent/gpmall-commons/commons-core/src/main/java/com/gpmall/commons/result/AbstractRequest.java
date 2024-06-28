package com.gpmall.commons.result;

import java.io.Serializable;

public abstract class AbstractRequest implements Serializable {


    private static final long serialVersionUID = 171744627931855057L;

    public abstract  void requestCheck();

    @Override
    public String toString() {
        return "AbstractRequest{}";
    }
}
