package com.gabriel.pss.service;

import com.gabriel.pss.model.Message;

public interface MessageService {
    void publish(Message message);
    Message[] getMessages(String subscriberName);
}
