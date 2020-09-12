package org.incolo.springpulsar.annotation;

import org.incolo.springpulsar.core.PulsarListenerContainerRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;

/**
 * @author Charvak Patel
 */
@SpringJUnitConfig
public class Application {

    @Autowired
    PulsarListenerAnnotationBeanPostProcessor processor;

    @Autowired
	PulsarListenerContainerRegistry registry;

    @Autowired
	Config config;

    @Test
    public void init() {
    }


    @Configuration
    @EnablePulsar
    public static class Config {

    	@PulsarListener
    	public void testing(@Payload String msg, @Header("key") String key) {

		}
    }
}
