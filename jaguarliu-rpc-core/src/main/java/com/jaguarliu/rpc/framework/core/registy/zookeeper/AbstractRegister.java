package com.jaguarliu.rpc.framework.core.registy.zookeeper;

import com.jaguarliu.rpc.framework.core.registy.RegistryService;
import com.jaguarliu.rpc.framework.core.registy.URL;

import java.util.List;

import static com.jaguarliu.rpc.framework.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;
import static com.jaguarliu.rpc.framework.core.common.cache.CommonServerCache.PROVIDER_URL_SET;

public abstract class AbstractRegister implements RegistryService {


    @Override
    public void register(URL url) {
        PROVIDER_URL_SET.add(url);
    }

    @Override
    public void unRegister(URL url) {
        PROVIDER_URL_SET.remove(url);
    }

    @Override
    public void subscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.add(url.getServiceName());
    }

    /**
     * 留给子类扩展
     *
     * @param url
     */
    public abstract void doAfterSubscribe(URL url);

    /**
     * 留给子类扩展
     *
     * @param url
     */
    public abstract void doBeforeSubscribe(URL url);

    /**
     * 留给子类扩展
     *
     * @param serviceName
     * @return
     */
    public abstract List<String> getProviderIps(String serviceName);


    @Override
    public void doUnSubscribe(URL url) {
        SUBSCRIBE_SERVICE_LIST.remove(url.getServiceName());
    }
}
