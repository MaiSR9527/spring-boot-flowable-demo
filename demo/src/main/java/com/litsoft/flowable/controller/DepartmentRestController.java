package com.litsoft.flowable.controller;

import com.litsoft.flowable.bussiness.department.entity.Department;
import com.litsoft.flowable.bussiness.department.service.IDepartmentService;
import com.litsoft.flowable.commons.response.BaseResponse;
import com.litsoft.flowable.commons.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * department RestController
 */
@RestController
@RequestMapping("/departments")
@Slf4j
@Api("部门业务")
public class DepartmentRestController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 获取单部门信息
     */
    @ApiOperation(value = "获取单个部门信息")
    @GetMapping
    public BaseResponse getDepartment(Integer departmentId){
        Department department = departmentService.selectOne(departmentId);
        //响应
        return BaseResponse.ok(department);
    }
    
    /**
     * 分页查询
     */
    @ApiOperation(value = "部门列表分页查询")
    @GetMapping("/pagequery")
    public BaseResponse pageQuery(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                  Department department){
        PageUtils pageUtils =  departmentService.selectPageList(department, new RowBounds(offset, limit));
        return BaseResponse.ok(pageUtils);
    }


    /**
     * 添加
     */
    @ApiOperation(value = "添加部门信息")
    @PostMapping
    public BaseResponse addDepartment(@RequestBody Department department){
        department.setCreateTime(new Date());
        int resultCode = departmentService.add(department);
        if(resultCode==0){
            return BaseResponse.error(5001,"部门数据已存在");
        }
        return BaseResponse.ok("添加成功");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改部门信息")
    @PutMapping
    public BaseResponse modifyDepartment(@RequestBody Department department){
        departmentService.modify(department);
        return BaseResponse.ok("修改成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除部门信息")
    @DeleteMapping("/{departmentId}")
    public BaseResponse deleteDepartment(@PathVariable("departmentId") Integer departmentId){
        departmentService.delete(departmentId);
        return BaseResponse.ok("删除成功");
    }
}
