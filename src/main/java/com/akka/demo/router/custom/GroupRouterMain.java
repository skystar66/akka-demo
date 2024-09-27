package com.akka.demo.router.custom;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.ArrayList;
import java.util.List;

public class GroupRouterMain {

    public static void main(String[] args) {


        ActorSystem actorSystem = ActorSystem.create("group-router-sys");

        List<String> paths = new ArrayList<>();

        //创建actor，并收集actor paths
        for (int i = 0; i < 10; i++) {
            actorSystem.actorOf(WorkerActor.props(), "worker-" + i);
            paths.add("/user/worker-" + i);
        }


        //创建router actor
        ActorRef router = actorSystem.actorOf(new GroupRouter(paths, 3).props(), "group-router");

        //通过路由发送消息
        for (int i = 0; i < 10; i++) {
            router.tell("msg-" + i, ActorRef.noSender());
        }
    }
}
