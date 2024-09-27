package com.akka.demo.router.balance;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class BalancingPoolActor extends AbstractActor {



    public static Props props() {
        return Props.create(BalancingPoolActor.class, BalancingPoolActor::new);
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
