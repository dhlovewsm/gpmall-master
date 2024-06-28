package com.gpmall.commons.result;

public class ResponseUtil <T>{


    private ResponseData<T> responseData;

    public ResponseUtil(){
        responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setMessage("success");
        responseData.setCode(200);
    }

    public ResponseData<T> setData(T data){
        responseData.setData(data);
        responseData.setSuccess(true);
        responseData.setCode(200);
        return responseData;
    }


    public ResponseData<T> setData(T data,String message){
        responseData.setData(data);
        responseData.setMessage(message);
        responseData.setCode(200);
        responseData.setSuccess(true);
        return responseData;
    }


    public ResponseData<T> setErrorMessage(String message){
        responseData.setMessage(message);
        responseData.setCode(500);
        responseData.setSuccess(false);
        return responseData;
    }


    public ResponseData<T> setErrorMessage(int code, String message){
        responseData.setMessage(message);
        responseData.setCode(code);
        responseData.setSuccess(false);
        return responseData;
    }

}
