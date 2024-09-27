package com.akka.demo.router.pool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RoundRobinPool;

public class RouterPoolMain {


    public static void main(String[] args) {


        ActorSystem system = ActorSystem.create("routerSys");


        //自动创建5个RouterPool Actor实例，使用轮训策略
        ActorRef actorRef = system.actorOf(new RoundRobinPool(5).props(RouterPool.props()), "routerPool");


        //发送几条消息
        actorRef.tell("hello router", ActorRef.noSender());
        actorRef.tell("hello router1", ActorRef.noSender());
        actorRef.tell("hello router2", ActorRef.noSender());
        actorRef.tell("hello router3", ActorRef.noSender());
    }


}
