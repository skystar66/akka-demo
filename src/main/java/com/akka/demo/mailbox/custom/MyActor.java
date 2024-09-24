package com.akka.demo.mailbox.custom;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class MyActor extends AbstractActor {

    public static Props props() {
        return Props.create(MyActor.class,MyActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println("receive msg:"+msg);
                })

                .build();
    }
}
