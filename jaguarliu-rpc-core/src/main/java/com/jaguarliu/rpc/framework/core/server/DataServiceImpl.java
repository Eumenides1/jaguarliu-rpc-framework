package com.jaguarliu.rpc.framework.core.server;

import com.jaguarliu.rpc.interfaces.DataService;

import java.util.ArrayList;
import java.util.List;

public class DataServiceImpl implements DataService {
    @Override
    public String sendData(String body) {
        System.out.println("己收到的参数长度："+body.length());
        return "success";
    }

    @Override
    public List<String> getList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("idea1");
        arrayList.add("idea2");
        arrayList.add("idea3");
        return arrayList;
    }
}
