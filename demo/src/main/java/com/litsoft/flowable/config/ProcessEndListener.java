package com.litsoft.flowable.config;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

/**
 * 流程结束修改状态
 */
public class ProcessEndListener implements FlowableEventListener {

    private static final long serialVersionUID = 1L;


    @Override
    public void onEvent(FlowableEvent event) {
        System.out.println("执行结束了");
    }


    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}