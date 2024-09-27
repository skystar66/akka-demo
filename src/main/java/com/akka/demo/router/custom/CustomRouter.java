//package com.akka.demo.router.custom;
//
//import akka.routing.Routee;
//import akka.routing.RoutingLogic;
//import scala.collection.immutable.IndexedSeq;
//
//import java.util.List;
//
//public class CustomRouter implements RoutingLogic {
//
//    private final List<Routee> routees;
//
//    public CustomRouter(List<Routee> routees) {
//        this.routees = routees;
//    }
//
//    @Override
//    public Routee select(Object message, IndexedSeq<Routee> routees) {
//
//        //根据消息内容选择路由
//        if (message instanceof String) {
//            String msg = (String) message;
//            int index = msg.hashCode() % routees.size();
//
//        }
//        return null; // 如果没有匹配的，返回空列表
//
//    }
//}
