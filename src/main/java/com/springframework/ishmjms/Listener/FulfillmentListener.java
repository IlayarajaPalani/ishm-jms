package com.springframework.ishmjms.Listener;

import com.springframework.ishmjms.Model.PaymentFulfillment;
import com.springframework.ishmjms.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FulfillmentListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.QUEUE)
    public void Listen(@Payload PaymentFulfillment payload, @Headers MessageHeaders headers, Message message){
        System.out.println("I got a message");
        System.out.println(payload);

        //throw new RuntimeException("run time exception occured");
        // in case of this exception message will be re queued to queue again by jms until client reads the message

    }

    @JmsListener(destination = JmsConfig.TWO_WAY_QUEUE)
    public void sendAndListen(@Payload PaymentFulfillment payload, @Headers MessageHeaders headers, Message message) throws JMSException {

        PaymentFulfillment response = PaymentFulfillment
                .builder()
                .uuid(UUID.randomUUID())
                .message("Response for the request")
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(),response);

        System.out.println("message from request "+response.getMessage());
        //throw new RuntimeException("run time exception occured");
        // in case of this exception message will be re queued to queue again by jms until client reads the message

    }
}
