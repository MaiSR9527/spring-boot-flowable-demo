package com.litsoft.flowable.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * Created: 2019-11-04 09:38
 * author: liqi
 * desc: Swagger2
 * version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${spring.application.name: flowable}")
    private String service;
    @Value("${swagger.title: 使用Swagger2构建 API接口文档}")
    private String title;
    @Value("${swagger.description: 相关接口}")
    private String description;
    @Value("${swagger.version: 1.0}")
    private String version;
    @Value("${swagger.contactName: 工作流}")
    private String contactName;


    @Bean
    public Docket createRestApi() {
        return getDocket();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(service + "\n" + title)
                .description(description)
                .termsOfServiceUrl("")
                .contact(new Contact(contactName, "", ""))
                .version(version)
                .build();
    }

    protected Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.litsoft"))
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
