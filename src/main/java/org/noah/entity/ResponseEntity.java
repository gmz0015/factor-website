package org.noah.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.noah.enums.CommonError;
import org.noah.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Data
public class ResponseEntity<T> {

    private static final Logger log = LoggerFactory.getLogger(ResponseEntity.class);

    public static final String SUCCESSFUL_CODE = "00000";
    public static final String SUCCESSFUL_MESG = "处理成功";

    private final static String DATE_STRING_FORMAT = "yyyyMMddHHmmss";

    private String instanceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;
    private String respCode;
    private String respDesc;
    private String timestamp;

    public ResponseEntity() {}

    /**
     * @param commonError
     */
    public ResponseEntity(CommonError commonError) {
        this.respCode = commonError.getErrCode();
        this.respDesc = commonError.getErrMsg();
        this.timestamp = DateUtil.getCurrentDate(DATE_STRING_FORMAT);
    }

    /**
     * @param commonError
     * @param result
     */
    public ResponseEntity(CommonError commonError, T result) {
        this(commonError);
        this.result = result;
        this.timestamp = DateUtil.getCurrentDate(DATE_STRING_FORMAT);
    }
    /**
     * 内部使用，用于构造成功的结果
     *
     * @param respCode
     * @param respDesc
     * @param result
     */
    public ResponseEntity(String respCode, String respDesc, T result) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.result = result;
        this.timestamp = DateUtil.getCurrentDate(DATE_STRING_FORMAT);
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param result
     * @return ResponseEntity
     */
    public static <T> ResponseEntity success(T result) {
        return new ResponseEntity<T>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, result);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static ResponseEntity success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return ResponseEntity
     */
    public static ResponseEntity fail() {
        return new ResponseEntity<>(CommonError.REQUEST_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param commonException
     * @return ResponseEntity
     */
    public static ResponseEntity fail(CommonException commonException) {
        return fail(commonException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param result
     * @return ResponseEntity
     */
    public static ResponseEntity fail(CommonException commonException, Object result) {
        return new ResponseEntity<>(commonException.getErrCode(), commonException.getErrMsg(), result);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param commonError
     * @param result
     * @return ResponseEntity
     */
    public static ResponseEntity fail(CommonError commonError, Object result) {
        return new ResponseEntity<>(commonError, result);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param respCode
     * @param respDesc
     * @param result
     * @return ResponseEntity
     */
    public static ResponseEntity fail(String respCode, String respDesc, Object result) {
        return new ResponseEntity<>(respCode, respDesc, result);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param commonError
     * @return ResponseEntity
     */
    public static ResponseEntity fail(CommonError commonError) {
        return ResponseEntity.fail(commonError, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param result
     * @return ResponseEntity
     */
    public static ResponseEntity fail(Object result) {
        return new ResponseEntity<>(CommonError.REQUEST_ERROR, result);
    }

    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.respCode);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}

