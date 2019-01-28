package com.gumihoy.dasgp.pigeon.client.directory;

import com.gumihoy.dasgp.pigeon.common.api.ServerConn;
import com.gumihoy.dasgp.pigeon.common.spi.SPI;

import java.util.List;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
@SPI(ConsulDirectory.NAME)
public interface IDirectory<T> {

    List<ServerConn> list();
}
