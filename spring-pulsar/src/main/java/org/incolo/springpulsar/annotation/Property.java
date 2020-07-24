package org.incolo.springpulsar.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Charvak Patel
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    String key() default "";
    String value()  default "";
}
