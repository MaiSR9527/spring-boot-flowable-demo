package com.msr.flowable.demo.bussiness.department.service;

import com.litsoft.flowable.bussiness.department.entity.Department;
import com.litsoft.flowable.commons.service.IBaseService;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IDepartmentService  extends IBaseService<Department> {
    /**
     * 查询单个
     */
    public Department selectOne(int departmentId);

    /**
     * 条件查询
     */
    public List<Department> selectList(Department department);

    /**
     * 分页查询查询
     */
    public PageUtils selectPageList(Department department, RowBounds rowBounds);

    /**
     * 添加
     */
    public int add(Department department);

    /**
     * 删除
     */
    public int delete(int departmentId);

    /**
     * 修改
     */
    public int modify(Department department);
}
