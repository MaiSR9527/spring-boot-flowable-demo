package com.msr.flowable.demo.config;

import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为解决flowable图片中的中文乱码
 *
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {


    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customIdGeneratorConfigurer() {
        return engineConfiguration -> {
            //设置全局事件监听
            engineConfiguration.setTypedEventListeners(this.getGlobalFlowableEventListener());
        };
    }
    /**
     * 设置系统级别监听器
     *
     * @return
     */
    private Map<String, List<FlowableEventListener>> getGlobalFlowableEventListener() {
        Map<String, List<FlowableEventListener>> typedListeners = new HashMap<String, List<FlowableEventListener>>();

        List<FlowableEventListener> processCompleteList = new ArrayList<FlowableEventListener>();
        processCompleteList.add(new ProcessEndListener());
        typedListeners.put("PROCESS_COMPLETED", processCompleteList);
        return typedListeners;

    }
}
