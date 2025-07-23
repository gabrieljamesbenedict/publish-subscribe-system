package com.gabriel.pss.serviceimpl;

import com.gabriel.pss.controller.PssController;
import com.gabriel.pss.entity.MessageData;
import com.gabriel.pss.entity.SubscriberData;
import com.gabriel.pss.model.Message;
import com.gabriel.pss.model.Subscriber;
import com.gabriel.pss.repository.MessageDataRepository;
import com.gabriel.pss.repository.SubscriberDataRepository;
import com.gabriel.pss.service.MessageService;
import com.gabriel.pss.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageDataRepository messageDataRepository;

    @Autowired
    SubscriberDataRepository subscriberDataRepository;

    @Override
    public void publish(Message message) {
        List<SubscriberData> subscriberData = new ArrayList<>();
        List<Subscriber> subscribers = new ArrayList<>();
        subscriberDataRepository.findSubscribersByTopic(message.getTopic()).forEach(subscriberData::add);
        Iterator<SubscriberData> it = subscriberData.iterator();
        while(it.hasNext()){
            SubscriberData subscriberDatum = it.next();
            logger.info(subscriberDatum.getSubscriberName());
            MessageData messageData = new MessageData();
            messageData.setTopic(message.getTopic());
            messageData.setPayload(message.getPayload());
            messageData.setSubscriberName(subscriberDatum.getSubscriberName());
            messageDataRepository.save(messageData);
        }
    }

    @Override
    public Message[] getMessages(String subscriberName) {
        List<Message> messages = new ArrayList<>();
        List<MessageData> messageData = new ArrayList<>();
        messageDataRepository.findAllBySubscriberName(subscriberName).forEach(messageData::add);
        Iterator<MessageData> it = messageData.iterator();
        while(it.hasNext()){
            MessageData msgData = it.next();
            Message message = new Message();
            message.setTopic(msgData.getTopic());
            message.setId(msgData.getId());
            message.setPayload(msgData.getPayload());
            messages.add(message);
        }
      //  messageDataRepository.deleteAllBySubscriberName(subscriberName);
        return messages.toArray(Message[]::new);
    }
}
