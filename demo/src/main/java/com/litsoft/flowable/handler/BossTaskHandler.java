package com.litsoft.flowable.handler;


import com.litsoft.flowable.bussiness.department.entity.Department;
import com.litsoft.flowable.bussiness.department.service.IDepartmentService;
import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.bussiness.person.service.IPersonService;
import com.litsoft.flowable.commons.enums.PersonRoleMenu;
import com.litsoft.flowable.config.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 老板审批流程监听器
 */
@Slf4j
public class BossTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("进来动态设定老板的监听器了");
        IPersonService personService = SpringUtils.getBean("personService");

        //动态查询老板级别的部门ID
        IDepartmentService departmentService = SpringUtils.getBean("departmentService");
        Department selectDepartmentObj = new Department();
        selectDepartmentObj.setDepartmentCode(PersonRoleMenu.BOSS.getCode());
        Department departmentResult = departmentService.selectOne(selectDepartmentObj);

        //动态查询老板级别的部门下的老板人员
        Person person = new Person();
        person.setDepartmentId(departmentResult.getDepartmentId());
        List<Person> personList = personService.selectList(person);
        List<String> bossList = new ArrayList<String>();
        for (Person p: personList){
            bossList.add(p.getPersonName());
        }
        delegateTask.addCandidateUsers(bossList);
    }
}
