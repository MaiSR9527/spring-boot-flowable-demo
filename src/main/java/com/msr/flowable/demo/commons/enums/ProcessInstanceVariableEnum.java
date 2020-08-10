package com.msr.flowable.demo.commons.enums;

/**
 * 工作流申请全局变量枚举
 */
public enum ProcessInstanceVariableEnum {
    PROCESSSHEET("process","流程单"),
    APPLYPERSON("applyPerson","流程申请人"),
    ;

    private String code;
    private String description;

    private  ProcessInstanceVariableEnum(String code, String description) {
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
