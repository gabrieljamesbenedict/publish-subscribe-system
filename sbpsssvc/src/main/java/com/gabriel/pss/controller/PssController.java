package com.gabriel.pss.controller;

import com.gabriel.pss.model.Message;
import com.gabriel.pss.model.Subscriber;
import com.gabriel.pss.service.MessageService;
import com.gabriel.pss.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PssController {

    Logger logger = LoggerFactory.getLogger(PssController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("api/pss/publish")
    public ResponseEntity<?> publish(@RequestBody Message message){
        logger.info("publish Input >> message.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            messageService.publish(message);
            response = ResponseEntity.ok(message.getTopic());
        }
        catch( Exception ex)
        {
            logger.error("Failed to publish topic  : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/pss/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody Subscriber subscriber){
        logger.info("subscribe Input >> subscriber.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            subscriberService.subscribe(subscriber.getName(), subscriber.getTopic());
            response = ResponseEntity.ok(subscriber.getTopic());
        }
        catch( Exception ex)
        {
            logger.error("Failed to publish topic  : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }


    @GetMapping("api/pss/{subscriber}")
    public ResponseEntity<?> getMessages(@PathVariable final String subscriber){
        logger.info("Input subscriber  name >> subscriber.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Message[] messages  = messageService.getMessages(subscriber);
            response = ResponseEntity.ok(messages);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }


}
