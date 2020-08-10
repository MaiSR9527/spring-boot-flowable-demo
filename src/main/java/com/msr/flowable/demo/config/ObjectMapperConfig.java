package com.msr.flowable.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
*
 */

@Component
public class ObjectMapperConfig {

    /**
     * 关闭FAIL_ON_EMPTY_BEANS， 解决返回Error异常是，JSON转换问题
     */
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }


}
