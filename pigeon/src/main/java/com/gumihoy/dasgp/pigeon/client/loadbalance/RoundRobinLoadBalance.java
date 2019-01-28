package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.common.api.Invocation;

import java.util.List;

/**
 * 轮询，按公约后的权重设置轮询比率
 *
 * @author kongtong.ouyang on 2018/9/27.
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, Invocation invocation) {
        return null;
    }

}
