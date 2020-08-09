package com.litsoft.flowable.bussiness.applyorder.service;

import com.litsoft.flowable.bussiness.applyorder.entity.ApplyOrder;
import com.litsoft.flowable.commons.service.IBaseService;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IApplyOrderService extends IBaseService<ApplyOrder> {

    /**
     * 查询单个
     */
    public ApplyOrder selectOne(int applyOrderId);

    /**
     * 条件查询
     */
    public List<ApplyOrder> selectList(ApplyOrder applyOrder);

    /**
     * 分页查询查询
     */
    public PageUtils selectPageList(ApplyOrder applyOrder, RowBounds rowBounds);

    /**
     * 添加
     */
    public int add(ApplyOrder applyOrder);

    /**
     * 删除
     */
    public int delete(int applyOrderId);

    /**
     * 修改
     */
    public int modify(ApplyOrder applyOrder);
}
