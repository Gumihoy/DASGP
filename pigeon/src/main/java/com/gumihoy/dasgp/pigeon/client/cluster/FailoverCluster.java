//
//package com.kent.dasgp.pigeon.client.cluster;
//
//
//import com.kent.dasgp.pigeon.client.directory.IDirectory;
//import com.kent.dasgp.pigeon.client.invoker.Invoker;
//
///**
// * 失败转移，当出现失败，重试其它服务器，通常用于读操作，但重试会带来更长延迟。
// * <p>
// * <a href="http://en.wikipedia.org/wiki/Failover">Failover</a>
// */
//public class FailoverCluster implements ICluster {
//
//    public final static String NAME = "Failover";
//
//    public <T> Invoker<T> join(IDirectory<T> directory) throws Exception {
//        return new FailoverClusterInvoker<T>(directory);
//    }
//
//}