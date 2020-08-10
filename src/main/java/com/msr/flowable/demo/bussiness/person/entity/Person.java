package com.msr.flowable.demo.bussiness.person.entity;

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
 * 人员表
 */
@Data
@Table(name = "bus_person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer personId;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "role")
    private String role;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
