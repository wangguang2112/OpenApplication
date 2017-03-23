package com.wang.autovo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangguang.
 * Date:2017/2/9
 * Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface AutoVo {
    String name() default "";
    String pack() default "";
    int count() default 0;

}
