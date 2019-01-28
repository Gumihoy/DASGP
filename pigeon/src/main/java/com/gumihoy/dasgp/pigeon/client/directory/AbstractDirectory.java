package com.gumihoy.dasgp.pigeon.client.directory;

import com.gumihoy.dasgp.pigeon.client.pool.ThriftSocketPoolObjectFactory;
import com.gumihoy.dasgp.pigeon.common.api.PoolConfig;
import com.gumihoy.dasgp.pigeon.common.util.NetUtils;
import com.gumihoy.dasgp.pigeon.common.api.ServerConn;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.thrift.transport.TTransport;

import java.util.List;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
public abstract class AbstractDirectory implements IDirectory {

    protected boolean async;
    protected int socketTimeout;
    protected int connTimeOut;
    protected PoolConfig config;

    @Override
    public List<ServerConn> list() {
        return null;
    }

    public abstract List<ServerConn> doList();



    protected ObjectPool createSocketPool(boolean async, String host, int port, int socketTimeout, int connTimeOut, PoolConfig config) {
        if(host.equals("127.0.0.1")
                || host.equals("localhost")) {
            host = NetUtils.getLocalIpV4();
        }

        ThriftSocketPoolObjectFactory factory = new ThriftSocketPoolObjectFactory(async, host, port, socketTimeout, connTimeOut);
        GenericObjectPool<TTransport> objectPool = new GenericObjectPool<TTransport>(factory, config.toObjectPoolConfig());
        return objectPool;
    }


    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
