package com.msr.flowable.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:03
 */

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private SpringProcessEngineConfiguration engineConfiguration;

    @Override
    public void run(ApplicationArguments var1) throws Exception {

        List<ProcessDefinition> definitionList = repositoryService
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().asc()
                .listPage(0, 100);
        log.info("----processDefinition size= {}", definitionList.size());

        for (ProcessDefinition processDefinition : definitionList) {
            log.info("----processDefinition = {},version = {},key ={},id = {}",
                    processDefinition,
                    processDefinition.getVersion(),
                    processDefinition.getKey(),
                    processDefinition.getId());

        }


        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");


    }
}
