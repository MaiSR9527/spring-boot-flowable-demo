package com.msr.flowable.demo.commons.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 返回
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private static final int RESULT_CODE_SUCCESS = 0;

    private static final String RESULT_MSG_SUCCESS = "success";

    private int code;

    private String message;

    private Object result;

    private BaseResponse() {}

    private BaseResponse(int code, String message, Object result){
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == RESULT_CODE_SUCCESS;
    }
    public <T> T  getResultObject(Class<T> clazz){
        return JSON.parseObject(JSON.toJSONString(this.getResult()), clazz);
    }

    public static BaseResponse ok(){
        return new BaseResponse(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS, new Object());
    }

    public static BaseResponse ok(Object result){
        return new BaseResponse(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS, result);
    }

    public static BaseResponse error(int code, String message){
        return new BaseResponse(code, message, new Object());
    }

    public static BaseResponse error(int code, String message, Object object){
        return new BaseResponse(code, message, object);
    }
}
