package com.jaguarliu.rpc.framework.core.common.event;

public interface IRpcListener<T> {
    void callBack(Object t);
}
