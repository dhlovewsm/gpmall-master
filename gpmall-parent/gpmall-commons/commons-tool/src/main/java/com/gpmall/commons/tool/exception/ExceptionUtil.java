package com.gpmall.commons.tool.exception;

import com.gpmall.commons.result.AbstractResponse;

public class ExceptionUtil {


    public static AbstractResponse handlerException4biz(AbstractResponse response, Exception e) throws Exception {

        Exception ex = null;
        if (!(e instanceof  Exception)){
            return  null;
        }
        if (e instanceof  ValidateException){
            response.setCode(((ValidateException) e).getErrorCode());
        } else if (e instanceof ProcessException) {
            response.setCode(((ProcessException) e).getErrorCode());
        } else if (e instanceof BizException){
            response.setCode(((BizException) e).getErrorCode());
        } else if (e instanceof Exception){
            throw e;
        }

        return response;

    }


}
