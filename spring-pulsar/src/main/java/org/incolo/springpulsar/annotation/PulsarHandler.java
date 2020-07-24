package org.incolo.springpulsar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Charvak Patel
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PulsarHandler {

    /**
     * When true, designate that this is the default fallback method if the payload type
     * matches no other {@link PulsarHandler} method. Only one method can be so designated.
     * @return true if this is the default method.
     */
    boolean isDefault() default false;
}
