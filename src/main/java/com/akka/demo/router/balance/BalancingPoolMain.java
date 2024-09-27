package com.akka.demo.router.balance;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.BalancingPool;

public class BalancingPoolMain {


    public static void main(String[] args) {


        ActorSystem system = ActorSystem.create("balancingPoolSys");

        // 他负责管理一组actor实例，并且这一组的actor实例都共享一个邮箱队列📮
        ActorRef balancingPoolActor = system.actorOf(new BalancingPool(5).props(BalancingPoolActor.props()), "balancingPoolActor");



        //发送消息
        balancingPoolActor.tell("hello world 1", ActorRef.noSender());
        balancingPoolActor.tell("hello world 2", ActorRef.noSender());
        balancingPoolActor.tell("hello world 3", ActorRef.noSender());
        balancingPoolActor.tell("hello world 4", ActorRef.noSender());
        balancingPoolActor.tell("hello world 5", ActorRef.noSender());
        balancingPoolActor.tell("hello world 6", ActorRef.noSender());


        //停止actor系统
        system.terminate();


    }


}
