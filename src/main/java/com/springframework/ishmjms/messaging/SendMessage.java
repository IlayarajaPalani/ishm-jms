package com.springframework.ishmjms.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.ishmjms.Model.PaymentFulfillment;
import com.springframework.ishmjms.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.json.stream.JsonParsingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendMessage {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

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

    @Scheduled(fixedRate = 2000)
    public void sendAndRecieveMessage() throws JMSException {

        PaymentFulfillment message = PaymentFulfillment
                .builder()
                .uuid(UUID.randomUUID())
                .message("Messaging for send and Received")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.TWO_WAY_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message srMesage = null;
                try {
                    srMesage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    srMesage.setStringProperty("_type","com.springframework.ishmjms.Model.PaymentFulfillment");

                    return srMesage;

                } catch (JsonProcessingException e) {
                    throw new JMSException("JMS");
                }
            }
            });
        System.out.println("message from response "+ receivedMsg.getBody(String.class));
        }

}
