package com.akka.demo.router.custom;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class WorkerActor extends AbstractActor {


    static Props props() {
        return Props.create(WorkerActor.class, WorkerActor::new);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println(getSelf()+",received worker message: " + msg);
                })
                .build();
    }
}
