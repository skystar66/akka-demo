package com.akka.demo;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.akka.demo.request.InitRequest;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ApplicationMaster {
    public static void main(String[] args) {

        //创建一个actor系统，用来管理内部的actor创建,name 为actor的根目录,项目名称
        ActorSystem actorSystem = ActorSystem.create("test");
        //创建 JobManagerActor actor
        ActorRef jobManagerActorRef = actorSystem.actorOf(JobManagerActor.props(), "jobManager");
        //打印 JobManagerActor actor path
        System.out.println(jobManagerActorRef);


        //给jobManager发送一个init消息,ActorRef.noSender 表示没有 Actor,是一个死信的actor
//        jobManagerActorRef.tell("init", ActorRef.noSender());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");
            String op = scanner.nextLine();
            switch (op) {
                case "init":
                    jobManagerActorRef.tell(new InitRequest(3), ActorRef.noSender());
                    break;
                case "fail":
                    ActorPath child = jobManagerActorRef.path().child("taskManager-" + new Random().nextInt(3));
                    actorSystem.actorSelection(child).tell("fail", ActorRef.noSender());
                    break;
                case "stop":
                    jobManagerActorRef.tell("stop", ActorRef.noSender());

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    }catch (Exception e) {}
                    System.exit(0);
            }
        }
    }
}