package com.jaguarliu.rpc.framework.core.proxy.javassist.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linhao
 * @Date created in 9:28 上午 2021/12/5
 */
public class Demo {

    public void doTest(){
        System.out.println("this is demo");
    }

    public String findStr(){
        return "success";
    }

    public List<String> findList(){
        return new ArrayList<>();
    }
}
