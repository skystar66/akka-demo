package com.akka.demo.router.custom;

import akka.actor.ActorRef;
import akka.routing.Routee;

public class TestRoutee implements Routee {

    private int routerNum;
    public TestRoutee(int routerNum) {
        this.routerNum = routerNum;
    }

    @Override
    public void send(Object message, ActorRef sender) {
        System.out.println(sender + ",send:" + message);
        sender.tell(message, ActorRef.noSender());
    }

    public int getRouterNum() {
        return routerNum;
    }

    public void setRouterNum(int routerNum) {
        this.routerNum = routerNum;
    }
}
