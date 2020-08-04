package com.msr.flowable.demo;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/4 19:36
 */

public class HolidayRequest {

    public static void main(String[] args) {
        //1.创建流程引擎
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        //2.流程定义：resources下xml定义流程
        //3.部署流程
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
        //4.通过api查询引擎是否已经部署了自定义的流程，通过repositoryService创建新的ProcessDefinitionQuery对象来完成
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("I found a process definition name: "+processDefinition.getName());
        System.out.println("I found a process definition version: "+processDefinition.getVersion());
        System.out.println("I found a process definition ID: "+processDefinition.getId());
        //5.启动流程实例,提供初始化变量。一般是通过页面的表单或者REST API来获取
        //5.1输入初始化变量
        Scanner scanner = new Scanner(System.in);
        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holiday do want to request?");
        Integer days = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();
        //5.1 通过RuntimeService启动实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("employee", employee);
        map.put("nrOfHolidays", days);
        map.put("description", description);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", map);


    }
}
