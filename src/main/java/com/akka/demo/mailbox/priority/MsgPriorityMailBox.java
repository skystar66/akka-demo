package com.akka.demo.mailbox.priority;

import akka.actor.ActorSystem;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedStablePriorityMailbox;
import com.typesafe.config.Config;


/**
 * 自定义优先级
 */
public class MsgPriorityMailBox extends UnboundedStablePriorityMailbox {

    public MsgPriorityMailBox(ActorSystem.Settings settings, Config config) {
        // Create a new PriorityGenerator, lower prio means more important
        super(
                new PriorityGenerator() {
                    @Override
                    public int gen(Object message) {
                        if (message.equals("张三")) {
                            return 0;// 高优
                        } else if (message.equals("李四")) {
                            return 1;// 在高优和中优之间
                        } else if (message.equals("王五")) {
                            return 2;// 低优
                        } else {
                            return 3;
                        }
                    }
                });
    }

}
