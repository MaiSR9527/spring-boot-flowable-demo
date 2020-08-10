package com.msr.flowable.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * spring获取bean工具类
 */
@Component
public class SpringUtils  implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SpringUtils.class);
    private static ApplicationContext applicationContext;
    private static volatile SpringUtils instance = null;

    private SpringUtils() {
    }

    public static SpringUtils getInstance() {
        try {
            if (instance == null) {
                Thread.sleep(300L);
                Class var0 = SpringUtils.class;
                synchronized(SpringUtils.class) {
                    if (instance == null) {
                        instance = new SpringUtils();
                    }
                }
            }
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return instance;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
        log.info("<<<<<<<<<<<<<<< SpringUtils 配置成功 >>>>>>>>>>>>>>>>>>");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return getApplicationContext().isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return getApplicationContext().getType(name);
    }

    public static Environment getEnvironment() {
        return getApplicationContext().getEnvironment();
    }
}
