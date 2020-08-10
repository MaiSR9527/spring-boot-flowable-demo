package com.msr.flowable.demo.handler;

import com.alibaba.fastjson.JSON;
import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.bussiness.person.service.IPersonService;
import com.litsoft.flowable.commons.enums.PersonRoleMenu;
import com.litsoft.flowable.commons.enums.ProcessInstanceVariableEnum;
import com.litsoft.flowable.config.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 经理审批任务动态设定人员监听器
 */
@Slf4j
public class ManagerTaskHandler implements TaskListener {
    @Autowired
    RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("进来动态设定经理的监听器了");
        IPersonService personService = SpringUtils.getBean("personService");
        //获取流程中设置的申请人信息
        Person nowApplyPerson = JSON.parseObject(JSON.toJSONString(delegateTask.getVariable(ProcessInstanceVariableEnum.APPLYPERSON.getCode())),Person.class);

        //动态查询当前人员对应的直属上司经理
        Person selectObj = new Person();
        selectObj.setRole(PersonRoleMenu.MEANAGER.getCode());
        selectObj.setDepartmentId(nowApplyPerson.getDepartmentId());
        Person manager = personService.selectOne(selectObj);
        delegateTask.setAssignee(manager.getPersonName());
    }

}
