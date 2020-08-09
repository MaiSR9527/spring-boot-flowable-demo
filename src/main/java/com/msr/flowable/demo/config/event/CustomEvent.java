package com.msr.flowable.demo.config.event;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:47
 */
@Slf4j
@Data
public class CustomEvent implements FlowableEvent {

    protected FlowableEventType type;
    protected String executionId;
    protected String processInstanceId;
    protected String processDefinitionId;

    public CustomEvent(String executionId, String processInstanceId, String processDefinitionId) {
        super();
        this.executionId = executionId;
        this.processInstanceId = processInstanceId;
        this.processDefinitionId = processDefinitionId;
    }

    public static void main(String[] args) {
        CustomEvent event = new CustomEvent("1", "2", "3");
        log.info("---{}", event);
    }

    @Override
    public FlowableEventType getType() {
        return FlowableEngineEventType.CUSTOM;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }


}
