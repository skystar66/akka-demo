package com.akka.demo.router.custom;

import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.SeveralRoutees;
import scala.collection.immutable.IndexedSeq;

import java.util.ArrayList;
import java.util.List;

public class RedundancyRoutingLogic implements RoutingLogic {


    private final int copySize;

    public RedundancyRoutingLogic(int copySize) {
        this.copySize = copySize;
    }

    //使用轮训负载算法
    RoundRobinRoutingLogic roundRobin = new RoundRobinRoutingLogic();


    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {

        List<Routee> routeeList = new ArrayList<>();
        //将该条消息 message ，从路由中挑选copySize个路由，并将消息转发给这几个路由
        for (int i = 0; i < copySize; i++) {
            routeeList.add(roundRobin.select(message, routees));
        }
        //SeveralRoutees 将消息发送到所有提供的路由 routees中
        return new SeveralRoutees(routeeList);
    }
}
