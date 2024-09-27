package com.akka.demo.router.group;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class RouterGroup extends AbstractActor {


    public static Props props() {
        return Props.create(RouterGroup.class, RouterGroup::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println(getSelf() + ",receive msg:" + msg);
                })

                .build();
    }
}
