package com.gabriel.pss;
import org.springframework.http.*;
import org.springframework.scheduling.Trigger;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ProxyMessenger implements Messenger{

    Subscriber subscriber;

    public ProxyMessenger() {
    }

    @Override
    public void subscribe(String topic, Subscriber subscriber) {
        this.subscriber = subscriber;
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "http://localhost:8080/api/pss";
        String requestBody = "{\"topic\":\"" + topic + "\", \"name\":\"" + subscriber.getName() +"\"}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody,httpHeaders);

        ResponseEntity<String> response
                = restTemplate.postForEntity(url + "/subscribe",  entity, String.class);
        init();
    }

    @Override
    public void publish(Message message) {
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "http://localhost:8080/api/pss";
        String requestBody = "{\"topic\":\"" + message.getTopic() + "\", \"payload\":\"" + message.getPayload() +"\"}";


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> response   = restTemplate.postForEntity(url + "/publish",  request, String.class);
        String responseBody = response.getBody();
    }

    public Message[] getMessages(String  subscriberName) {
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "http://localhost:8080/api/pss";
        ResponseEntity<Message[]> response
                = restTemplate.getForEntity(url + "/" + subscriberName, Message[].class);
        return response.getBody();

    }

    @Scheduled(cron = "0/10 * * * * ?") // Every 10 seconds
    public void executeTask(){
        Message[] messages = getMessages(subscriber.getName());
        subscriber.receive((Message[]) messages);
     }

    public void init() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task run ");
                executeTask();
            }
        };
        timer.schedule(task, 0, 10000); // Schedule the task to run every 1 second
    }

}
