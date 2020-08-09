package com.msr.flowable.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.nio.charset.Charset;
import java.util.List;


/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:05
 */

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    DataInterceptor dataInterceptor;

    @Autowired
    SessionInterceptor sessionInterceptor;


    /**
     * 添加json转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /**
         * 1.定义一个Converter 转换消息 2.添加fastjson配置信息, 比如 是否格式化数据 3.在Converter中添加配置信息
         * 4.将Converter 添加到 List converters当中
         */

        // 1.定义一个Converter 转换消息
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastjson配置信息, 比如 是否格式化数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        // 3.在Converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将Converter 添加到 List converters当中
        converters.add(fastConverter);

    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(dataInterceptor);
        //SpringBoot2.x 静态资源被拦截器拦截
        interceptorRegistration.excludePathPatterns("/static/**");
        interceptorRegistration.excludePathPatterns("/plugin/**");
        interceptorRegistration.excludePathPatterns("/images/**");
        interceptorRegistration.excludePathPatterns("/js/**");
        interceptorRegistration.addPathPatterns("/**");

        InterceptorRegistration sessioninterceptorRegistration = registry.addInterceptor(sessionInterceptor);
        sessioninterceptorRegistration.excludePathPatterns("/static/**");
        sessioninterceptorRegistration.excludePathPatterns("/plugin/**");
        sessioninterceptorRegistration.excludePathPatterns("/images/**");
        sessioninterceptorRegistration.excludePathPatterns("/js/**");
        sessioninterceptorRegistration.addPathPatterns("/**");
    }


    /**
     * 配置静态资源
     * 此处可使用 properties 文件配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(2592000).resourceChain(true)
                .addResolver(versionResourceResolver);
    }


}
