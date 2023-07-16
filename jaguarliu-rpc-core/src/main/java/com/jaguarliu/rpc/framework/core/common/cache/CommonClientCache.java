package com.jaguarliu.rpc.framework.core.common.cache;

import com.jaguarliu.rpc.framework.core.common.ChannelFutureWrapper;
import com.jaguarliu.rpc.framework.core.common.RpcInvocation;
import com.jaguarliu.rpc.framework.core.common.config.ClientConfig;
import com.jaguarliu.rpc.framework.core.registy.URL;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CommonClientCache {
    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue(100);
    public static Map<String,Object> RESP_MAP = new ConcurrentHashMap<>();
    public static ClientConfig CLIENT_CONFIG;
    //provider名称 --> 该服务有哪些集群URL
    public static List<String> SUBSCRIBE_SERVICE_LIST = new ArrayList<>();
    public static Map<String, List<URL>> URL_MAP = new ConcurrentHashMap<>();
    public static Set<String> SERVER_ADDRESS = new HashSet<>();
    //每次进行远程调用的时候都是从这里面去选择服务提供者
    public static Map<String, List<ChannelFutureWrapper>> CONNECT_MAP = new ConcurrentHashMap<>();

}
