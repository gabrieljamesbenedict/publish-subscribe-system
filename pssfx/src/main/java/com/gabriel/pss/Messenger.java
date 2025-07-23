package com.gabriel.pss;

public interface Messenger {
    void subscribe(String topic, Subscriber subscriber);
    void publish(Message message);
   }
