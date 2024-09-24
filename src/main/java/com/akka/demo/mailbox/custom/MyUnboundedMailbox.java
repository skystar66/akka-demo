package com.akka.demo.mailbox.custom;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.ProducesMessageQueue;
import com.typesafe.config.Config;
import scala.Option;

public class MyUnboundedMailbox implements MailboxType, ProducesMessageQueue<MyMessageQueue> {


    //此构造函数签名必须存在，它将被 Akka 调用
    public MyUnboundedMailbox(ActorSystem.Settings settings, Config config) {
        //在此处输入您的初始化代码
    }


    @Override
    public MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MyMessageQueue();
    }
}
