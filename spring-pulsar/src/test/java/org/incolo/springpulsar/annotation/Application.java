package org.incolo.springpulsar.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author Charvak Patel
 */
@SpringJUnitConfig
public class Application {

    @Autowired
    PulsarListenerAnnotationBeanPostProcessor processor;

    @Autowired
	Config config;

    @Test
    public void init() {
        System.out.println("hello");
    }


    @Configuration
    @EnablePulsar
    public static class Config {

    	@PulsarListener
    	public void testing() {

		}
    }
}
