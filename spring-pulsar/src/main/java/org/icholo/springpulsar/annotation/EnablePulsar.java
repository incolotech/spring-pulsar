package org.icholo.springpulsar.annotation;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Charvak Patel
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PulsarListenerConfigurationSelector.class)
public @interface EnablePulsar {
}
