package com.litsoft.flowable.controller;

import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.bussiness.person.service.IPersonService;
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
 * person RestController
 */
@RestController
@RequestMapping("/persons")
@Slf4j
@Api("工作流人员业务")
public class PersonRestController {

    @Autowired
    private IPersonService personService;

    /**
     * 获取单个人员信息
     */
    @ApiOperation(value = "获取单个人员信息")
    @GetMapping
    public BaseResponse getPerson(Integer personId){
        Person person = personService.selectOne(personId);
        //响应
        return BaseResponse.ok(person);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "人员列表分页查询")
    @GetMapping("/pagequery")
    public BaseResponse pageQuery(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                  Person person){
        PageUtils pageUtils =  personService.selectPageList(person, new RowBounds(offset, limit));
        return BaseResponse.ok(pageUtils);
    }

    /**
     * 添加
     */
    @ApiOperation(value = "添加人员信息")
    @PostMapping
    public BaseResponse addPerson(@RequestBody Person person){
        person.setCreateTime(new Date());
        int resultCode = personService.add(person);
        if(resultCode==0){
            return BaseResponse.error(5001,"数据已存在");
        }
        return BaseResponse.ok("添加成功");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改人员信息")
    @PutMapping
    public BaseResponse modifyPerson(@RequestBody Person person){
        personService.modify(person);
        return BaseResponse.ok("修改成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除人员信息")
    @DeleteMapping("/{personId}")
    public BaseResponse deletePerson(@PathVariable("personId") Integer personId){
        personService.delete(personId);
        return BaseResponse.ok("删除成功");
    }
}
