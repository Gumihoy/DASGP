//package com.kent.dasgp.pigeon.client.cluster;
//
//
//import com.kent.dasgp.pigeon.client.directory.IDirectory;
//import com.kent.dasgp.pigeon.client.loadbalance.ILoadBalance;
//import com.kent.dasgp.pigeon.common.api.Invocation;
//import com.kent.dasgp.pigeon.client.invoker.Invoker;
//import com.kent.dasgp.pigeon.common.utils.NetUtils;
//import com.kent.dasgp.pigeon.common.utils.VersionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * @author kongtong.ouyang on 2018/9/27.
// */
//public abstract class AbstractClusterInvoker<T> implements Invoker<T> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractClusterInvoker.class);
//
//    protected final IDirectory<T> directory;
//
//    protected boolean availablecheck;
//
//    private AtomicBoolean destroyed = new AtomicBoolean(false);
//
//    private volatile Invoker<T> stickyInvoker = null;
//
//    public AbstractClusterInvoker(IDirectory<T> directory) {
//        if (directory == null) {
//            throw new IllegalArgumentException("service directory == null");
//        }
//
//        this.directory = directory;
//    }
//
//    public Class<T> getInterface() {
//        return directory.getInterface();
//    }
//
////    public boolean isAvailable() {
////        Invoker<T> invoker = stickyInvoker;
////        if (invoker != null) {
////            return invoker.isAvailable();
////        }
////        return directory.isAvailable();
////    }
//
////    public void destroy() {
////        if (destroyed.compareAndSet(false, true)) {
////            directory.destroy();
////        }
////    }
//
//    /**
//     * 使用loadbalance选择invoker.</br>
//     * a)先lb选择，如果在selected列表中 或者 不可用且做检验时，进入下一步(重选),否则直接返回</br>
//     * b)重选验证规则：selected > available .保证重选出的结果尽量不在select中，并且是可用的
//     *
//     * @param availablecheck 如果设置true，在选择的时候先选invoker.available == true
//     * @param selected       已选过的invoker.注意：输入保证不重复
//     */
//    protected Invoker<T> select(ILoadBalance loadBalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected) throws Exception {
//        if (invokers == null || invokers.size() == 0)
//            return null;
//        String methodName = invocation == null ? "" : invocation.getMethodName();
//
//        boolean sticky = invokers.get(0).getUrl().getMethodParameter(methodName, Constants.CLUSTER_STICKY_KEY, Constants.DEFAULT_CLUSTER_STICKY);
//        {
//            //ignore overloaded method
//            if (stickyInvoker != null && !invokers.contains(stickyInvoker)) {
//                stickyInvoker = null;
//            }
//            //ignore cucurrent problem
//            if (sticky && stickyInvoker != null && (selected == null || !selected.contains(stickyInvoker))) {
//                if (availablecheck && stickyInvoker.isAvailable()) {
//                    return stickyInvoker;
//                }
//            }
//        }
//        Invoker<T> invoker = doselect(loadBalance, invocation, invokers, selected);
//
//        if (sticky) {
//            stickyInvoker = invoker;
//        }
//        return invoker;
//    }
//
//    private Invoker<T> doselect(ILoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected) throws Exception {
//        if (invokers == null || invokers.size() == 0) {
//            return null;
//        }
//        if (invokers.size() == 1) {
//            return invokers.get(0);
//        }
//        // 如果只有两个invoker，退化成轮循
//        if (invokers.size() == 2 && selected != null && selected.size() > 0) {
//            return selected.get(0) == invokers.get(0) ? invokers.get(1) : invokers.get(0);
//        }
//
//        Invoker<T> invoker = loadbalance.select(invokers, invocation);
//
//        //如果 selected中包含（优先判断） 或者 不可用&&availablecheck=true 则重试.
//        if ((selected != null && selected.contains(invoker))
//                || (!invoker.isAvailable() && availablecheck)) {
//            try {
//                Invoker<T> rinvoker = reselect(loadbalance, invocation, invokers, selected, availablecheck);
//                if (rinvoker != null) {
//                    invoker = rinvoker;
//                } else {
//                    //看下第一次选的位置，如果不是最后，选+1位置.
//                    int index = invokers.indexOf(invoker);
//                    try {
//                        //最后在避免碰撞
//                        invoker = index < invokers.size() - 1 ? invokers.get(index + 1) : invoker;
//                    } catch (Exception e) {
//                        LOGGER.warn(e.getMessage() + " may because invokers list dynamic change, ignore.", e);
//                    }
//                }
//            } catch (Throwable t) {
//                LOGGER.error("clustor relselect fail reason is :" + t.getMessage() + " if can not slove ,you can set cluster.availablecheck=false in url", t);
//            }
//        }
//        return invoker;
//    }
//
//    /**
//     * 重选，先从非selected的列表中选择，没有在从selected列表中选择.
//     *
//     * @param loadBalance
//     * @param invocation
//     * @param invokers
//     * @param selected
//     * @return
//     * @throws Exception
//     */
//    private Invoker<T> reselect(ILoadBalance loadBalance, Invocation invocation,
//                                List<Invoker<T>> invokers, List<Invoker<T>> selected, boolean availablecheck) throws Exception {
//
//        //预先分配一个，这个列表是一定会用到的.
//        List<Invoker<T>> reselectInvokers = new ArrayList<Invoker<T>>(invokers.size() > 1 ? (invokers.size() - 1) : invokers.size());
//
//        //先从非select中选
//        if (availablecheck) { //选isAvailable 的非select
//            for (Invoker<T> invoker : invokers) {
//                if (invoker.isAvailable()) {
//                    if (selected == null || !selected.contains(invoker)) {
//                        reselectInvokers.add(invoker);
//                    }
//                }
//            }
//            if (reselectInvokers.size() > 0) {
//                return loadBalance.select(reselectInvokers, getUrl(), invocation);
//            }
//        } else { //选全部非select
//            for (Invoker<T> invoker : invokers) {
//                if (selected == null || !selected.contains(invoker)) {
//                    reselectInvokers.add(invoker);
//                }
//            }
//            if (reselectInvokers.size() > 0) {
//                return loadBalance.select(reselectInvokers, getUrl(), invocation);
//            }
//        }
//        //最后从select中选可用的.
//        {
//            if (selected != null) {
//                for (Invoker<T> invoker : selected) {
//                    if ((invoker.isAvailable()) //优先选available
//                            && !reselectInvokers.contains(invoker)) {
//                        reselectInvokers.add(invoker);
//                    }
//                }
//            }
//            if (reselectInvokers.size() > 0) {
//                return loadBalance.select(reselectInvokers, getUrl(), invocation);
//            }
//        }
//        return null;
//    }
//
//    public T invoke(final Invocation invocation) throws Exception {
//
//        checkWhetherDestroyed();
//
//        ILoadBalance loadBalance;
//
//        List<Invoker<T>> invokers = list(invocation);
//        if (invokers != null
//                && invokers.size() > 0) {
//            loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(invokers.get(0).getUrl()
//                    .getMethodParameter(invocation.getMethodName(), Constants.LOADBALANCE_KEY, Constants.DEFAULT_LOADBALANCE));
//        } else {
//            loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(Constants.DEFAULT_LOADBALANCE);
//        }
//
//        return doInvoke(invocation, invokers, loadBalance);
//    }
//
//    protected void checkWhetherDestroyed() {
//
//        if (destroyed.get()) {
//            throw new RpcException("Rpc cluster invoker for " + getInterface() + " on consumer " + NetUtils.getLocalHost()
//                    + " use dubbo version " + Version.getVersion()
//                    + " is now destroyed! Can not invoke any more.");
//        }
//    }
//
//    protected void checkInvokers(List<Invoker<T>> invokers, Invocation invocation) {
//        if (invokers == null
//                || invokers.size() == 0) {
//            throw new IllegalArgumentException("Failed to invoke the method "
//                    + invocation.getMethodName() + " in the service " + getInterface().getName()
//                    + ". No provider available for the service " + directory.getUrl().getServiceKey()
//                    + " from registry " + directory.getUrl().getAddress()
//                    + " on the consumer " + NetUtils.getLocalIpV4()
//                    + " using the pigeon version " + VersionUtils.getVersion()
//                    + ". Please check if the providers have been started and registered.");
//        }
//    }
//
//    protected abstract T doInvoke(Invocation invocation, List<Invoker<T>> invokers, ILoadBalance loadBalance) throws Exception;
//
//    protected List<Invoker<T>> list(Invocation invocation) throws Exception {
//        List<Invoker<T>> invokers = directory.list(invocation);
//        return invokers;
//    }
//}