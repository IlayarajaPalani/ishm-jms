package com.springframework.ishmjms.messaging;

import com.springframework.ishmjms.Model.PaymentFulfillment;
import com.springframework.ishmjms.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendMessage {

    private final JmsTemplate jmsTemplate;
    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        System.out.println("I am sending message");
        PaymentFulfillment message = PaymentFulfillment.builder()
                .uuid(UUID.randomUUID())
                .message("Messing dropped into thge jms queue")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.QUEUE,message);
        System.out.println("Message Sent!");
    }
}
