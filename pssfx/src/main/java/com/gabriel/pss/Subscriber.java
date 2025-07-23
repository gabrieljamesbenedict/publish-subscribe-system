package com.gabriel.pss;

import java.util.List;

public interface Subscriber {
   void receive(Message[] messages);
   String getName();
}
