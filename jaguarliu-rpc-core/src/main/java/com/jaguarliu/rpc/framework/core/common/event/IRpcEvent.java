package com.jaguarliu.rpc.framework.core.common.event;

public interface IRpcEvent {

    Object getData();

    IRpcEvent setData(Object data);
}
