package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.common.api.Invocation;

import java.util.List;

/**
 * 最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差
 *
 * @author kongtong.ouyang on 2018/9/27.
 */
public class LeastActiveLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, Invocation invocation) {
        return null;
    }

}
