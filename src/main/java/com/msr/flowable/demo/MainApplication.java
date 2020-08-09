package com.msr.flowable.demo;

import com.msr.flowable.demo.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 14:48
 */
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return strings -> {
//            System.out.println("Number of process definitions : "
//                    + repositoryService.createProcessDefinitionQuery().count());
//            System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//            runtimeService.startProcessInstanceByKey("oneTaskProcess");
//            System.out.println("Number of tasks after process start: "
//                    + taskService.createTaskQuery().count());
//        };
//    }

    @Bean
    public CommandLineRunner init(final MyService myService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                myService.createDemoUsers();
            }
        };
    }
}
