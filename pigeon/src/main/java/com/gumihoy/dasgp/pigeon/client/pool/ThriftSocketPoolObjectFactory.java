package com.gumihoy.dasgp.pigeon.client.pool;

import com.gumihoy.dasgp.pigeon.common.util.ThriftUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public class ThriftSocketPoolObjectFactory extends BasePooledObjectFactory<TTransport> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftSocketPoolObjectFactory.class);

    private boolean async;
    private String host;
    private int port;
    private int socketTimeout;
    private int connTimeOut;


    public ThriftSocketPoolObjectFactory(boolean async, String host, int port, int socketTimeout, int connTimeOut) {
        this.async = async;
        this.host = host;
        this.port = port;
        this.socketTimeout = socketTimeout;
        this.connTimeOut = connTimeOut;
    }

    @Override
    public TTransport create() throws Exception {
        int retry = 3;

        TTransport socket = null;
        boolean connectSuccess = false;
        Exception exception = null;
        while (retry-- > 0) {
            try {
                if (this.async) {
                    socket = new TNonblockingSocket(this.host, this.port, this.socketTimeout);
                } else {
                    socket = new TSocket(this.host, this.port, this.socketTimeout, this.connTimeOut);
                    socket.open();
                }
                connectSuccess = true;
                return socket;
            } catch (Exception e) {
                if (e instanceof SocketTimeoutException) {
                    exception = e;
                    continue;
                }
                throw e;
            } finally {
                // SocketTimeoutException Socket is still valid.
                if (!connectSuccess
                        && socket != null) {
                    ThriftUtils.close(socket);
                }
            }
        }

        throw new RuntimeException(exception);
    }

    @Override
    public PooledObject<TTransport> wrap(TTransport x) {
        return new DefaultPooledObject<>(x);
    }

    @Override
    public boolean validateObject(PooledObject<TTransport> x) {
        try {
            TTransport socket = x.getObject();
            if (socket instanceof TSocket) {
                if (socket.isOpen()) {
                    return true;
                } else {
                    LOGGER.warn("TTransport validate fail : {}", ((TSocket) socket).getSocket());
                    return false;
                }
            } else if (socket instanceof TNonblockingSocket) {
                if (socket.isOpen()) {
                    return true;
                } else {
                    LOGGER.warn("TTransport validate fail : {}", ((TNonblockingSocket) socket).getSocketChannel().socket());
                    return false;
                }
            } else {
                LOGGER.warn("TTransport validate fail : unknown object");
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("TTransport validate fail : {}", e.getMessage());
        }
        return false;
    }


}
