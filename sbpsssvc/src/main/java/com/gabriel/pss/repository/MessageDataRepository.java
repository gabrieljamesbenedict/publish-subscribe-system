package com.gabriel.pss.repository;

import com.gabriel.pss.entity.MessageData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageDataRepository extends CrudRepository<MessageData,Integer> {
    Iterable<MessageData> findAllBySubscriberName(String subscriberName);

     void deleteAllBySubscriberName(String subscriberName);
}
