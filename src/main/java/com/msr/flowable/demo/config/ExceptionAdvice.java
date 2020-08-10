package com.msr.flowable.demo.config;


import com.litsoft.flowable.commons.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 异常控制处理器
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {



    /**
     * Exception异常
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse validationError(Exception ex) {
        log.error("-----兜底异常处理-----", ex);
        String errmsg = ex.getMessage();
        return  BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), errmsg);
    }

}
