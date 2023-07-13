package com.jaguarliu.rpc.framework.core.proxy.jdk;

import com.jaguarliu.rpc.framework.core.common.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static com.jaguarliu.rpc.framework.core.common.cache.CommonClientCache.RESP_MAP;
import static com.jaguarliu.rpc.framework.core.common.cache.CommonClientCache.SEND_QUEUE;

public class JDKClientInvocationHandler implements InvocationHandler {

    private final static Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setArgs(args);
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setTargetServiceName(clazz.getName());
        rpcInvocation.setUuid(UUID.randomUUID().toString());

        RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        SEND_QUEUE.add(rpcInvocation);

        long beginTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - beginTime < 3*1000) {
            Object object = RESP_MAP.get(rpcInvocation.getUuid());
            if (object instanceof RpcInvocation) {
                return ((RpcInvocation)object).getResponse();
            }
        }
        throw new TimeoutException("client wait server's response timeout!");
    }
}
