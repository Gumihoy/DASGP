package com.gumihoy.dasgp.pigeon.common.api;

import java.util.Date;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public class Server {

    private static final int DEFAULT_WEIGHT = 100;

    private String appKey;
    private String ip;
    private int port;
    private int weight;
    private Date startTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null
                || getClass() != o.getClass()) {
            return false;
        }

        Server server = (Server) o;

        if (port != server.port) {
            return false;
        }
        return ip != null ? ip.equals(server.ip) : server.ip == null;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
