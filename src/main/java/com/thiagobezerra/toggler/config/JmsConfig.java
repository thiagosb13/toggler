package com.thiagobezerra.toggler.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class JmsConfig {
    private final String toggleQueue;

    public JmsConfig(@Value("queue.toggle") String toggleQueue) {
        this.toggleQueue = toggleQueue;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(toggleQueue);
    }
}
