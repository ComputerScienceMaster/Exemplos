package com.computersciecemaster.ActiveMQ.http;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "queue.compras")
    public void onReceiverQueue(String str) {
        System.out.println( str );
    }
}
