package com.msr.flowable.demo.bussiness.person.service.impl;

import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.bussiness.person.mapper.PersonMapper;
import com.litsoft.flowable.bussiness.person.service.IPersonService;
import com.litsoft.flowable.commons.service.impl.BaseServiceImpl;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements IPersonService {

    @Autowired
    PersonMapper personMapper;

    @Override
    public Person selectOne(int personId) {
        return personMapper.selectByPrimaryKey(personId);
    }

    @Override
    public List<Person> selectList(Person person) {
        Example example = new Example(Person.class);
        // 创建Criteria
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(person.getDepartmentId())){
            criteria.andEqualTo("departmentId",person.getDepartmentId());
            example.and(criteria);
        }
        return personMapper.selectByExample(example);
    }

    @Override
    public PageUtils selectPageList(Person person, RowBounds rowBounds) {
        Example example = new Example(Person.class);
        // 创建Criteria
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(person.getDepartmentId())){
            criteria.andEqualTo("departmentId",person.getDepartmentId());
            example.and(criteria);
        }
        List<Person> personList = personMapper.selectByExampleAndRowBounds(example,rowBounds);
        int total = personMapper.selectCountByExample(example);
        return new PageUtils(personList,total);
    }

    @Override
    public int add(Person person) {
        Person findPersonExitObj = new Person();
        findPersonExitObj.setPersonName(person.getPersonName());
        findPersonExitObj.setRole(person.getRole());
        Person selectPersonResult = personMapper.selectOne(findPersonExitObj);
        if(null!=selectPersonResult){
            return 0;
        }
        return personMapper.insert(person);
    }

    @Override
    public int delete(int personId) {
        return personMapper.deleteByPrimaryKey(personId);
    }

    @Override
    public int modify(Person person) {
        return personMapper.updateByPrimaryKey(person);
    }
}
