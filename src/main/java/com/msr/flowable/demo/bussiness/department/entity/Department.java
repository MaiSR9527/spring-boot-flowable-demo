package com.msr.flowable.demo.bussiness.department.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *  部门业务实体类
 */
@Data
@Table(name = "bus_department")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
