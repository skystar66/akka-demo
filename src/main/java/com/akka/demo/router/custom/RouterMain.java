package com.akka.demo.router.custom;

import akka.actor.ActorSystem;
import akka.routing.Routee;
import akka.routing.SeveralRoutees;
import scala.collection.immutable.IndexedSeq;

import java.util.ArrayList;
import java.util.List;

import static akka.japi.Util.immutableIndexedSeq;

public class RouterMain {


    public static void main(String[] args) {


//        ActorSystem system = ActorSystem.create("router");


        RedundancyRoutingLogic redundancyRoutingLogic = new RedundancyRoutingLogic(3);


        List<Routee> routeeList = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            routeeList.add(new TestRoutee(i));
        }


        IndexedSeq<Routee> routeeIndexedSeq = immutableIndexedSeq(routeeList);


        for (int i = 0; i < 5; i++) {
            //选择路由
            SeveralRoutees severalRoutees = (SeveralRoutees) redundancyRoutingLogic.select("msg" + i, routeeIndexedSeq);
            List<Routee> routees = severalRoutees.getRoutees();

            System.out.println("=================start====================");
            for (Routee routee : routees) {
                TestRoutee testRoutee = (TestRoutee) routee;
//                routee.send();
                System.out.println("router num:" + testRoutee.getRouterNum());
            }
            System.out.println("=================end====================");


        }


    }

}
