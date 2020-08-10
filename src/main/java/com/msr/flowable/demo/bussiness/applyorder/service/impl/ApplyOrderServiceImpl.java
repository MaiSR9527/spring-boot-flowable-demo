package com.msr.flowable.demo.bussiness.applyorder.service.impl;

import com.litsoft.flowable.bussiness.applyorder.entity.ApplyOrder;
import com.litsoft.flowable.bussiness.applyorder.mapper.ApplyOrderMapper;
import com.litsoft.flowable.bussiness.applyorder.service.IApplyOrderService;
import com.litsoft.flowable.commons.service.impl.BaseServiceImpl;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ApplyOrderServiceImpl  extends BaseServiceImpl<ApplyOrder> implements IApplyOrderService {

    @Autowired
    ApplyOrderMapper applyOrderMapper;

    @Override
    public ApplyOrder selectOne(int applyOrderId) {
        return applyOrderMapper.selectByPrimaryKey(applyOrderId);
    }

    @Override
    public List<ApplyOrder> selectList(ApplyOrder applyOrder) {
        Example example = new Example(ApplyOrder.class);
        return applyOrderMapper.selectByExample(example);
    }

    @Override
    public PageUtils selectPageList(ApplyOrder applyOrder, RowBounds rowBounds) {
        Example example = new Example(ApplyOrder.class);
        List<ApplyOrder> personList = applyOrderMapper.selectByExampleAndRowBounds(example,rowBounds);
        int total = applyOrderMapper.selectCountByExample(example);
        return new PageUtils(personList,total);
    }

    @Override
    public int add(ApplyOrder applyOrder) {
        return applyOrderMapper.insert(applyOrder);
    }

    @Override
    public int delete(int applyOrderId) {
        return applyOrderMapper.deleteByPrimaryKey(applyOrderId);
    }

    @Override
    public int modify(ApplyOrder applyOrder) {
        return applyOrderMapper.updateByPrimaryKey(applyOrder);
    }
}
