package com.gumihoy.dasgp.pigeon.server;

import com.gumihoy.dasgp.pigeon.AbstractThriftProxy;
import com.gumihoy.dasgp.pigeon.common.util.ProxyFactoryUtils;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author kongtong.ouyang on 2018/9/26.
 */
public class ThriftServerProxy<T> extends AbstractThriftProxy implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ThriftServerProxy.class);

    private Class<?> serviceInterface;
    private String serviceSimpleName;
    private T serviceImpl;
    private int port;

    private int selectorThreads = 4;
    private int workerThreads = 4;

    private Object proxy;


    @Override
    public void afterPropertiesSet() throws Exception {

        ThriftServerMethodInterceptor interceptor = new ThriftServerMethodInterceptor();
        proxy = ProxyFactoryUtils.getTargetClassProxy(serviceImpl, interceptor);

        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);

        // processor: 关联处理器与Service服务的实现
        TProcessor processor = getSyncProcessorConstructor().newInstance(proxy);

        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(socket);
        args.selectorThreads(selectorThreads).workerThreads(workerThreads);
        args.processor(processor);
        TProtocolFactory factory = new TBinaryProtocol.Factory();
        args.protocolFactory(factory);
        TThreadedSelectorServer server = new TThreadedSelectorServer(args);
        server.serve();
    }


    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public T getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(T serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSelectorThreads() {
        return selectorThreads;
    }

    public void setSelectorThreads(int selectorThreads) {
        this.selectorThreads = selectorThreads;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }
}
