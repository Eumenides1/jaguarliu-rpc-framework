package com.jaguarliu.rpc.framework.core.common.event;

import com.jaguarliu.rpc.framework.core.common.event.listener.ServiceUpdateListener;
import com.jaguarliu.rpc.framework.core.common.utils.CommonUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.reflect.Type;

public class IRpcListenerLoader {

    private static List<IRpcListener> iRpcListenerList = new ArrayList<>();

    private static ExecutorService eventThreadPool = Executors.newFixedThreadPool(2);

    public static void registerListener(IRpcListener iRpcListener) {
        iRpcListenerList.add(iRpcListener);
    }

    public void init() {
        registerListener(new ServiceUpdateListener());
    }
    /**
     * 获取接口上的泛型T
     *
     * @param o     接口
     */
    public static Class<?> getInterfaceT(Object o) {
        // 首先，通过调用o.getClass().getGenericInterfaces()获取o对象实现的接口的泛型类型数组
        Type[] types = o.getClass().getGenericInterfaces();
        // 获取数组中的第一个元素，并将其强制转换为ParameterizedType类型，赋值给parameterizedType变量。
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        // 通过调用parameterizedType.getActualTypeArguments()获取泛型参数的类型数组。
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        return null;
    }

    public static void sendEvent(IRpcEvent iRpcEvent) {
        if(CommonUtils.isEmptyList(iRpcListenerList)){
            return;
        }
        for (IRpcListener<?> iRpcListener : iRpcListenerList) {
            Class<?> type = getInterfaceT(iRpcListener);
            if(type.equals(iRpcEvent.getClass())){
                eventThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iRpcListener.callBack(iRpcEvent.getData());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

}
