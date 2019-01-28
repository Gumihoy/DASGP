package com.gumihoy.dasgp.pigeon.client.directory;

import com.gumihoy.dasgp.pigeon.common.api.Server;
import com.gumihoy.dasgp.pigeon.common.api.ServerConn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
public class DirectDirectory extends AbstractDirectory {

    public static final String NAME = "Direct";

    private List<ServerConn> serverConns = new ArrayList<>();

    public DirectDirectory(Set<Server> servers) {
        if (servers == null
                || servers.size() == 0) {
            throw new IllegalArgumentException("servers is empty.");
        }

        for (Server server : servers) {
            ServerConn serverConn = new ServerConn();
            serverConn.setSocketPool(this.createSocketPool(this.async, server.getIp(), server.getPort(), socketTimeout, connTimeOut, config));
            serverConn.setServer(server);
            serverConns.add(serverConn);
        }
    }

    @Override
    public List<ServerConn> doList() {
        return serverConns;
    }

}
