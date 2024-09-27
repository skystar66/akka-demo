package com.akka.demo;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.akka.demo.request.TaskManagerRequest;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TaskManagerActor extends AbstractActor {

    private volatile boolean isRunning = false;

    @Override
    public void preStart() throws Exception {
        System.out.println("taskManager 开始启动 " + self());
        //actor创建好之后，开始向 jobManager 发送初始化指令
        TimeUnit.SECONDS.sleep((int) Math.random() * 5);
        //因为taskManager是jobManager创建的，所以jobManager的注册目录是自身的父目录，直接获取父目录就拿到了jobManager了
        getContext().actorSelection(getSelf().path().parent())
                .tell(new TaskManagerRequest("init", System.currentTimeMillis()), self());

        System.out.println("taskManager 启动完成 " + sender());
        isRunning = true;
        //启动一个线程，定时给jobManager发送心跳
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isRunning) sendHeartbeat();
                    try {
                        //5秒发一次
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();

    }


    /**
     * getContext.stop(ActorRef)时调用
     */
    @Override
    public void postStop() throws Exception {
        System.out.println("taskManager " + self() + " 关闭中...");
    }


    public void sendHeartbeat() {
        System.out.println("taskManager " + self() + " 发送心跳数据.");
        getContext().actorSelection(getSelf().path().parent())
                .tell(new TaskManagerRequest("heartbeat", System.currentTimeMillis()), self());

    }

    /**
     * 重启前调用
     */
    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        System.out.println("taskManager 重启开始...");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        System.out.println("taskManager 重启完成...");
    }

    static Props props() {
        return Props.create(TaskManagerActor.class, TaskManagerActor::new);
    }


    /**
     * 重写接收消息的方法
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("stop", stopMessage -> {
                    System.out.println("taskManager 收到 sender " + sender() + " stop 指令，开始关闭自己 " + self());
                    isRunning = false;
                    getContext().stop(self());
                })
                .matchEquals("fail", failMessage -> {
                    isRunning = false;
                    throw new RuntimeException("i am failed!");
                })
                .build();
    }
}
