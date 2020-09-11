package com.springframework.ishmjms.Listener;

import com.springframework.ishmjms.Model.PaymentFulfillment;
import com.springframework.ishmjms.config.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class FulfillmentListener {

    @JmsListener(destination = JmsConfig.QUEUE)
    public void Listen(@Payload PaymentFulfillment payload, @Headers MessageHeaders headers, Message message){
        System.out.println("I got a message");
        System.out.println(payload);

        //throw new RuntimeException("run time exception occured");
        // in case of this exception message will be re queued to queue again by jms until client reads the message

    }
}
