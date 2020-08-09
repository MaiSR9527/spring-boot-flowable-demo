package com.msr.flowable.demo.common.dto;

import java.io.Serializable;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:35
 */

public class CodeMsg  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected int code;

    protected String msg;

    /**
     * 通用的错误码  1001XX
     *
     */
    public static final CodeMsg COMMON_SUCCESS = new CodeMsg(0, "success");

    public static final CodeMsg COMMON_FAIL= new CodeMsg(100100, "失败");

    public static final CodeMsg COMMON_NOT_EXIST= new CodeMsg(100101, "未查到相关数据");

    public static final CodeMsg COMMON_SERVER_ERROR = new CodeMsg(100102, "服务端异常");

    public static final CodeMsg COMMON_PARAM_ERROR = new CodeMsg(100103, "参数有误");

    public static final CodeMsg COMMON_BIND_ERROR = new CodeMsg(100104, "参数校验异常：%s");

    public static final CodeMsg COMMON_TIME_OUT = new CodeMsg(100105, "超时熔断");



    /**
     * 登录模块 2002XX
     */

    public static final CodeMsg LOGIN_SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static final CodeMsg LOGIN_PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");

    public static final CodeMsg LOGIN_MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg LOGIN_MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");

    public static final CodeMsg LOGIN_MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static final CodeMsg LOGIN_PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static final CodeMsg LOGIN_ACCOUNT_NOT_EXIST = new CodeMsg(500216, "无此用户");


    /**
     * 用户模块模块 3006XX
     */
    public static final CodeMsg USER_USERNAME_EXIST = new CodeMsg(500601, "用户名已经存在");

    public static final CodeMsg USER_PHONE_EXIST = new CodeMsg(500602, "手机号已存在");

    public static final CodeMsg USER_IDCART_EXIST = new CodeMsg(500603, "身份证号已存在");

    public static final CodeMsg USER_USER_NOT_EXIST = new CodeMsg(500604, "用户不存在");


    /**
     * chat模块  4007XX
     */

    public static final CodeMsg APP_DEVICE_ID_NOT_EMPTY = new CodeMsg(500701, "请求头中未设置deviceId");





    public CodeMsg() {
    }



    public CodeMsg(String msg) {
        this.code = CodeMsg.COMMON_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}

