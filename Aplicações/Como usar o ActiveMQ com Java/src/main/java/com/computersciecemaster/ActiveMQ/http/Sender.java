package com.computersciecemaster.ActiveMQ.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class Sender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("send/{msg}")
    public void sendMessage(@PathVariable(value="msg") String msg){
        jmsTemplate.convertAndSend("queue.compras", "{user: 'vinicius'}" + msg);
    }
}
