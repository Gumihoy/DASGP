package com.gumihoy.dasgp.pigeon.common.api;

import org.apache.commons.pool2.ObjectPool;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
public class ServerConn {

    private Server server;
    private ObjectPool socketPool;


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ObjectPool getSocketPool() {
        return socketPool;
    }

    public void setSocketPool(ObjectPool socketPool) {
        this.socketPool = socketPool;
    }

}
