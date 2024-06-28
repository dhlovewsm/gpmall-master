package com.gpmall.user.constants;


/**
 * user-service 统一错误码
 */
public enum SysRetCodeConstants {


    SUCCESS("000000", "成功"),
    USERNAME_OR_PASSWORD_ERROR("003001", "用户名或密码错误"),
    TOKEN_VALID_FAILED("003002", "token验证失败"),
    USERNAME_ALREADY_EXIST("003003", "用户名已存在"),
    USER_REGISTER_FAILED("003004", "注册失败,请联系管理员"),
    VALID_CODE_ERROR("003005", "验证码错误"),
    USERNAME_NOT_VERIFY("003006", "用户名尚未激活"),
    USER_REGISTER_VERIFY_FAILED("003007", "用户注册失败插入验证数据失败"),
    USER_INFO_INVALID("003008", "用户信息非法"),
    REQUEST_FORMAT_ILLEGAL("003060", "请求格式非法"),
    REQUEST_IP_ILLEGAL("003061", "请求IP非法"),
    REQUEST_CHECK_FAILED("003062", "请求参数校验失败"),
    DATA_NOT_EXIST("003070", "数据不存在"),
    DATA_REPEATED("003071", "数据重复"),
    REQUEST_DATA_NOT_EXIST("003072", "请求数据为空"),
    REQUEST_DATA__ERROR("003073", "请求数据错误"),
    REQUISITE_PARAMETER_NOT_EXIST("003074", "必填参数为空"),

    PERMISSION_DENIED("003091", "权限不足"),
    DB_EXCEPTION("003097", "数据库异常"),
    SYSTEM_TIMEOUT("003098", "系统超时"),
    SYTEM_ERROR("003099", "系统错误"),
    REGISTRY_VERIFY_INFO_ILLEGAL("003200", "注册验证信息非法")
    ;




    private String code;
    private String message;

    SysRetCodeConstants(String code, String message) {

        this.code = code;
        this.message = message;

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String code){
        for(SysRetCodeConstants s : SysRetCodeConstants.values()){
            if (code == null){
                break;
            }
            if (s.code.equals(code)){
                return s.message;
            }
        }

        return null;
    }

}
