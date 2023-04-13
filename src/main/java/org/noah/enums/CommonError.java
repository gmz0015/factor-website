package org.noah.enums;

public enum CommonError {
    // 成功码
    SUCCESS("00000", "成功"),
    FAIL("00001", "失败"),
    NOT_AUTH("00002", "授权校验失败"),
    PARAMS_ERROR("00003", "参数错误"),
    LOGIN_LOST("00004", "登陆失效"),
    LOGIN_ERROR("00005", "登录失败"),
    REQUEST_ERROR("00006", "请求处理失败"),

    // 用户相关
    NO_USER("10000", "用户不存在"),

    // 文件相关
    FILE_EXTENSION_ERROR("13000", "文件扩展名不正确");

    private String errCode;
    private String errMsg;

    private CommonError(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
