package com.gabriel.pss.model;

import lombok.Data;

@Data
public class Message{
    int id;
    String payload;
    String topic;
}
