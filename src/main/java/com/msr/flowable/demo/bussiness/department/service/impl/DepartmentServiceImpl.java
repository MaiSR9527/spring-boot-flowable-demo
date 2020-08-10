package com.msr.flowable.demo.bussiness.department.service.impl;

import com.litsoft.flowable.bussiness.department.entity.Department;
import com.litsoft.flowable.bussiness.department.mapper.DepartmentMapper;
import com.litsoft.flowable.bussiness.department.service.IDepartmentService;
import com.litsoft.flowable.commons.service.impl.BaseServiceImpl;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl  extends BaseServiceImpl<Department> implements IDepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department selectOne(int departmentId) {
        return departmentMapper.selectByPrimaryKey(departmentId);
    }

    @Override
    public List<Department> selectList(Department department) {
        // 创建Example
        Example example = new Example(Department.class);
        return departmentMapper.selectByExample(example);
    }

    @Override
    public PageUtils selectPageList(Department department, RowBounds rowBounds) {

        // 创建Example
        Example example = new Example(Department.class);
        List<Department> personList = departmentMapper.selectByExampleAndRowBounds(example,rowBounds);
        int total = departmentMapper.selectCountByExample(example);
        return new PageUtils(personList,total);
    }

    @Override
    public int add(Department department) {
        Department findDepartmentExitObj = new Department();
        findDepartmentExitObj.setDepartmentCode(department.getDepartmentCode());
        Department departmentResult = departmentMapper.selectOne(findDepartmentExitObj);
        if(null!=departmentResult){
            return 0;
        }
        return departmentMapper.insert(department);
    }

    @Override
    public int delete(int departmentId) {
        return departmentMapper.deleteByPrimaryKey(departmentId);
    }

    @Override
    public int modify(Department department) {
        return departmentMapper.updateByPrimaryKey(department);
    }
}
