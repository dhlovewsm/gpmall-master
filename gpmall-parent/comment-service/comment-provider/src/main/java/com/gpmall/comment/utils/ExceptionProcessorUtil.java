package com.gpmall.comment.utils;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.ExceptionUtil;

public class ExceptionProcessorUtil {


    public ExceptionProcessorUtil() {
    }


    public static void handleException(AbstractResponse response, Exception e){

        try {
            ExceptionUtil.handlerException4biz(response,e);
        }catch (Exception ex){
            response.setCode(CommentRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(CommentRetCode.SYSTEM_ERROR.getMessage());
        }

    }
}
