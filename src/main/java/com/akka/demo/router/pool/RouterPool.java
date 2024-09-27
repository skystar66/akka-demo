package com.akka.demo.router.pool;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class RouterPool extends AbstractActor {


    public static Props props() {
        return Props.create(RouterPool.class, RouterPool::new);
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
