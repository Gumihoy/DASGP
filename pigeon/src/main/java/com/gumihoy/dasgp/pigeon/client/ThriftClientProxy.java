package com.gumihoy.dasgp.pigeon.client;

import com.gumihoy.dasgp.pigeon.AbstractThriftProxy;
import com.gumihoy.dasgp.pigeon.client.enums.LoadBalanceType;
import com.gumihoy.dasgp.pigeon.client.enums.ClusterType;
import com.gumihoy.dasgp.pigeon.client.enums.RegisterType;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

/**
 * @author kongtong.ouyang on 2018/9/26.
 */
public class ThriftClientProxy extends AbstractThriftProxy implements FactoryBean<Object> {

    private static final int DEFAULT_TIME_OUT = 2000;

    private String appKey;

    private boolean async = false;
    private RegisterType register = RegisterType.CONSUL;
    private ClusterType cluster = ClusterType.Failover;
    private LoadBalanceType loadblance = LoadBalanceType.WEIGHT_RANDOM;

    private String remoteServerAppKey;
    private String remoteServerIp;
    private int remoteServerPort;

    private int socketTimeout;
    private int connectTimeout;

    private Object serviceProxy;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == this.appKey
                || this.appKey.isEmpty()) {
            throw new IllegalArgumentException("appKey is empty, please check your settings.");
        }

        super.afterPropertiesSet();

        Class<?> proxyInterface = getIfaceInterface(this.async);

        TTransport socket = null;
        TProtocol protocol = new TBinaryProtocol(socket);

        getSynClientClass().getConstructor().newInstance();


//        ThriftClientMethodInterceptor interceptor = new ThriftClientMethodInterceptor();
//        serviceProxy = ProxyFactory.getProxy(proxyInterface, interceptor);
    }

    @Nullable
    @Override
    public Object getObject() throws Exception {
        return this.serviceProxy;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return this.serviceInterface == null ? null : this.getIfaceInterface(async);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public ClusterType getCluster() {
        return cluster;
    }

    public void setCluster(ClusterType cluster) {
        this.cluster = cluster;
    }


    public String getRemoteServerAppKey() {
        return remoteServerAppKey;
    }

    public void setRemoteServerAppKey(String remoteServerAppKey) {
        this.remoteServerAppKey = remoteServerAppKey;
    }

    public String getRemoteServerIp() {
        return remoteServerIp;
    }

    public void setRemoteServerIp(String remoteServerIp) {
        this.remoteServerIp = remoteServerIp;
    }

    public int getRemoteServerPort() {
        return remoteServerPort;
    }

    public void setRemoteServerPort(int remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
    }

    public Object getServiceProxy() {
        return serviceProxy;
    }

    public void setServiceProxy(Object serviceProxy) {
        this.serviceProxy = serviceProxy;
    }
}
