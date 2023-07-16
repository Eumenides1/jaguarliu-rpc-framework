package com.jaguarliu.rpc.framework.core.proxy.javassist;


import com.jaguarliu.rpc.framework.core.proxy.ProxyFactory;

/**
 * @Author linhao
 * @Date created in 5:32 下午 2021/12/4
 */
public class JavassistProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Class clazz) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                clazz, new JavassistInvocationHandler(clazz));
    }
}
