package com.jaguarliu.rpc.framework.core.proxy.jdk;

import com.jaguarliu.rpc.framework.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(final Class clazz) throws Throwable {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKClientInvocationHandler(clazz));
    }
}
