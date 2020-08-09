package com.litsoft.flowable.commons.enums;

/**
 * 人员角色枚举
 */
public enum  PersonRoleMenu {

    BOSS("boss","老板"),
    MEANAGER("master","经理"),
    STAFF("slave","员工")
    ;

    private String code;
    private String description;

    private  PersonRoleMenu(String code, String description) {
        this.code=code;
        this.description=description;
    }


    public String getCode() {
        return this.code;
    }

    public String getDescription(){
        return this.description;
    }
}
