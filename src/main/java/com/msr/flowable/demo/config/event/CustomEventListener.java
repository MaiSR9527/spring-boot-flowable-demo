package com.msr.flowable.demo.config.event;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:51
 */

@Slf4j
public class CustomEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {

        FlowableEventType eventType = event.getType();

        if (FlowableEngineEventType.CUSTOM.equals(eventType)) {
            log.info("自定义事件-----流程结束------{}", event);
        }

    }

    @Override
    public String getOnTransaction() {
        return null;
    }


    @Override
    public boolean isFailOnException() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        // TODO Auto-generated method stub
        return false;
    }

}
