package com.thiagobezerra.toggler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

import static java.lang.String.format;

@Service
public class MessagingNotificationService implements NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingNotificationService.class);

    private final Queue queue;
    private final JmsTemplate jmsTemplate;

    public MessagingNotificationService(Queue queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void notify(Object object) {
        LOGGER.info(format("Sending to queue: %s", object.toString()));

        jmsTemplate.convertAndSend(queue, object.toString());
    }
}
