package com.gumihoy.dasgp.pigeon.common.spi;

import java.lang.annotation.*;

/**
 * @author kongtong.ouyang on 2018/10/23.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPI {

    String value();

}
