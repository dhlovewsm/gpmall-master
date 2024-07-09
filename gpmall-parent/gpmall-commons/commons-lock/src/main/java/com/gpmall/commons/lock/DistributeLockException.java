package com.gpmall.commons.lock;

public class DistributeLockException extends Exception{


    private static final long serialVersionUID = -593362292104627671L;

    public DistributeLockException() {super();}


    public DistributeLockException(String message){super(message);}



    public DistributeLockException(String message, Throwable cause){super(message, cause);}


    public DistributeLockException(Throwable cause){super(cause);}


    protected DistributeLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
