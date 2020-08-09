package com.litsoft.flowable.bussiness.person.service;

 
import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.commons.service.IBaseService;
import com.litsoft.flowable.commons.utils.PageUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * PersonService 接口
 */
public interface IPersonService extends IBaseService<Person> {

    /**
     * 查询单个
     */
    public Person selectOne(int personId);

    /**
     * 条件查询
     */
    public List<Person> selectList(Person person);

    /**
     * 分页查询查询
     */
    public PageUtils selectPageList(Person personDto, RowBounds rowBounds);

    /**
     * 添加
     */
    public int add(Person person);

    /**
     * 删除
     */
    public int delete(int personId);

    /**
     * 修改
     */
    public int modify(Person person);

}
