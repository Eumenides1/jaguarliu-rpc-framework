package com.jaguarliu.rpc.framework.core.proxy.javassist.demo;

public interface HelloService {

    void say(String msg);

    String echo(String msg);

    String[] getHobbies();

}