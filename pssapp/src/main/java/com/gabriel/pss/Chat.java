package com.gabriel.pss;

import java.util.List;

public class Chat implements Subscriber {

    private String subscriberName;

    String topic;
    Messenger proxy;

    public Chat(String subscriberName){
        this.subscriberName = subscriberName;
        init();
    }

    void init(){
        proxy = new ProxyMessenger();
        topic = "Playboy";
        proxy.subscribe(topic, this);
    }

    @Override
    public void receive(Message[] messages) {
        for(Message message : messages) {
            System.out.println(message.getPayload());
        }
    }

    @Override
    public String getName() {
        return subscriberName;
    }
}

