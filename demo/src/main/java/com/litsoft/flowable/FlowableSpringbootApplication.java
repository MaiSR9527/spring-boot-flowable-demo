package com.litsoft.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * FlowableSpringbootApplication
 *
 * @author liqi
 * @date 2019/11/06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.litsoft"})
@tk.mybatis.spring.annotation.MapperScan("com.litsoft.flowable.bussiness.*.mapper")
public class FlowableSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableSpringbootApplication.class, args);
    }

}

