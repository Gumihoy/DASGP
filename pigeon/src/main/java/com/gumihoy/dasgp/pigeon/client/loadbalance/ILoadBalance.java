package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.common.api.Invocation;
import com.gumihoy.dasgp.pigeon.common.spi.SPI;

import java.util.List;

/**
 * @author kongtong.ouyang on 2018/9/27.
 */
@SPI(WeightRandomLoadBalance.NAME)
public interface ILoadBalance {

    <T> Invoker<T> select(List<Invoker<T>> invokers, Invocation invocation) throws Exception;
}
