package com.gabriel.pss.serviceimpl;

import com.gabriel.pss.entity.SubscriberData;
import com.gabriel.pss.model.Subscriber;
import com.gabriel.pss.repository.SubscriberDataRepository;
import com.gabriel.pss.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl  implements SubscriberService {


    @Autowired
    SubscriberDataRepository subscriberDataRepository;

    @Override
    public void subscribe(String subscriberName, String topic) {
        SubscriberData subscriberData = new SubscriberData();
        subscriberData.setSubscriberName(subscriberName);
        subscriberData.setTopic(topic);
        subscriberDataRepository.save(subscriberData);
    }
}
