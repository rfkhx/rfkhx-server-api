package edu.upc.mishuserverapi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitAccess {

    // 访问频率
    int frequency() default 10;

    // 时间段内（毫秒）
    int millisecond() default 1000;
}