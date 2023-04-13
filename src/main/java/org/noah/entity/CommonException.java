package org.noah.entity;

import org.noah.enums.CommonError;

public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -6291624303284855691L;

    public CommonError commonError;

    public String errMsg;

    public Class<?> resultClass;

    public String getErrCode() {
        return this.commonError.getErrCode();
    }

    public CommonError getBaseError() {
        return commonError;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }
}
