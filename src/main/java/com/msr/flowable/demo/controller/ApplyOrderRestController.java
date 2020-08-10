package com.msr.flowable.demo.controller;

import com.litsoft.flowable.bussiness.applyorder.entity.ApplyOrder;
import com.litsoft.flowable.bussiness.applyorder.service.IApplyOrderService;
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
 * applyorder RestController
 */
@RestController
@RequestMapping("/applyorders")
@Slf4j
@Api("报销单业务")
public class ApplyOrderRestController {

    @Autowired
    private IApplyOrderService applyorderService;

    /**
     * 获取单个报销单信息
     */
    @ApiOperation(value = "获取单个报销单信息")
    @GetMapping
    public BaseResponse getApplyOrder(Integer applyorderId){
        ApplyOrder applyorder = applyorderService.selectOne(applyorderId);
        //响应
        return BaseResponse.ok(applyorder);
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "报销审批列表分页查询")
    @GetMapping("/pagequery")
    public BaseResponse pageQuery(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                  ApplyOrder applyorder){
        PageUtils pageUtils =  applyorderService.selectPageList(applyorder, new RowBounds(offset, limit));
        return BaseResponse.ok(pageUtils);
    }


    /**
     * 添加
     */
    @ApiOperation(value = "添加报销审批信息")
    @PostMapping
    public BaseResponse addApplyOrder(@RequestBody ApplyOrder applyorder){
        applyorder.setCreateTime(new Date());
        applyorderService.add(applyorder);
        return BaseResponse.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改报销审批信息")
    @PutMapping
    public BaseResponse modifyApplyOrder(@RequestBody ApplyOrder applyorder){
        applyorderService.modify(applyorder);
        return BaseResponse.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除报销审批信息")
    @DeleteMapping("/{applyOrderId}")
    public BaseResponse deleteApplyOrder(@PathVariable("applyOrderId") Integer applyOrderId){
        applyorderService.delete(applyOrderId);
        return BaseResponse.ok();
    }
}
