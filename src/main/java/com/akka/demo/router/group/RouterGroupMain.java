package com.akka.demo.router.group;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import com.akka.demo.router.pool.RouterPool;

import java.util.ArrayList;
import java.util.List;

public class RouterGroupMain {


    public static void main(String[] args) {


        ActorSystem system = ActorSystem.create("routerSys");

        ActorRef actorRef1 = system.actorOf(RouterGroup.props(), "routerGroup-1");
        ActorRef actorRef2 =   system.actorOf(RouterGroup.props(), "routerGroup-2");
        ActorRef actorRef3 =  system.actorOf(RouterGroup.props(), "routerGroup-3");
        ActorRef actorRef4 =  system.actorOf(RouterGroup.props(), "routerGroup-4");
        ActorRef actorRef5 =  system.actorOf(RouterGroup.props(), "routerGroup-5");


        List<String> paths=new ArrayList<>();
        paths.add(actorRef1.path().toStringWithoutAddress());
        paths.add(actorRef2.path().toStringWithoutAddress());
        paths.add(actorRef3.path().toStringWithoutAddress());
        paths.add(actorRef4.path().toStringWithoutAddress());
        paths.add(actorRef5.path().toStringWithoutAddress());





        //group路由器通过接收一组现有的actor路径来管理分发消息，使用轮训策略
        ActorRef routerGroup = system.actorOf(new RoundRobinGroup(paths).props(), "routerGroup");


        //发送几条消息
        routerGroup.tell("hello router", ActorRef.noSender());
        routerGroup.tell("hello router1", ActorRef.noSender());
        routerGroup.tell("hello router2", ActorRef.noSender());
        routerGroup.tell("hello router3", ActorRef.noSender());
    }


}
