package com.akka.demo.mailbox.priority;

import akka.actor.AbstractActor;

public class MsgPriorityActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println(getSelf() + "--->" + msg);
                })
                .build();
    }
}
