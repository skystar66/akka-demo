package com.akka.demo.mailbox;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.demo.mailbox.custom.MyActor;
import com.akka.demo.mailbox.priority.MsgPriorityActor;

public class MailboxQueue {


    public static void main(String[] args) {


        ActorSystem sys = ActorSystem.create("system");


        //测试优先级队列
//        ActorRef ref = sys.actorOf(Props.create(MsgPriorityActor.class).withMailbox("msgprio-mailbox"),
//                "priorityActor");
//        Object[] messages = {"王五", "李四", "张三", "小二"};
//        for (Object msg : messages) {
//            ref.tell(msg, ActorRef.noSender());
//        }


        //测试自定义邮箱、自定义调度器
        ActorRef actorRef = sys.actorOf(MyActor.props()
                //自定义邮箱队列
                .withMailbox("akka.actor.mailbox.my-customer-mailbox")
                //自定义线程调度器
                .withDispatcher("akka.actor.my-dispatcher")
                , "myActor");

        actorRef.tell("hello", ActorRef.noSender());

    }


}
