package com.msr.flowable.demo.common.dto;



import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:38
 */

public class Result<T> implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean success = true;
    private int code;
    private String msg;
    private T data;


    public Result() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Result(boolean success, int code, String msg, T data) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 成功构造方法
     *
     * @param data
     */
    private Result(T data) {
        this.code = CodeMsg.COMMON_SUCCESS.getCode();
        this.data = data;
    }

    /**
     * 失败构造
     *
     * @param codeMsg
     */
    private Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.success = false;
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(String exMsg) {
        return new Result<T>(new CodeMsg(exMsg));
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("success", this.success);
        map.put("code", this.code);
        map.put("msg", this.msg);
        map.put("data", this.data);
        return map;
    }


}

