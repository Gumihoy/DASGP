package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.common.api.Invocation;

import java.util.List;

/**
 * 权重随机, 按权重设置随机概率
 *
 * @author kongtong.ouyang on 2018/9/27.
 */
public class WeightRandomLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "weightRandom";

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, Invocation invocation) {
        System.out.println("afafa");

        return null;
    }
}
