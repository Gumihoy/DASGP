package com.gumihoy.dasgp.pigeon.client.directory;

import com.gumihoy.dasgp.pigeon.common.api.Server;
import com.gumihoy.dasgp.pigeon.common.api.ServerConn;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
public class ConsulDirectory extends AbstractDirectory {

    public static final String NAME = "Consul";

    private ConcurrentHashMap<Server, ServerConn> SERVER_MAP = new ConcurrentHashMap<>();


    @Override
    public List<ServerConn> doList() {
        return null;
    }



}
