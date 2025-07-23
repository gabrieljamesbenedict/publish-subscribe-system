package com.gabriel.pss.repository;

import com.gabriel.pss.entity.SubscriberData;
import com.gabriel.pss.model.Subscriber;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberDataRepository extends CrudRepository<SubscriberData, Integer>{
    Iterable<SubscriberData> findSubscribersByTopic(String topic);
}
