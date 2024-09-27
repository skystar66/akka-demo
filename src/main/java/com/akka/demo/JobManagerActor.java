package com.akka.demo;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.akka.demo.request.InitRequest;
import com.akka.demo.request.TaskManagerRequest;

import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JobManagerActor extends AbstractActor {


    //心跳,每个actor上次上报的心跳时间戳
    private ConcurrentHashMap<ActorRef, Long> heartbeat = new ConcurrentHashMap<>();


    private volatile boolean isRunning = false;

    /**
     * Actor 创建时自动异步启动
     */
    @Override
    public void preStart() throws Exception {
        System.out.println("jobManager actor 启动");

        heartbeat = new ConcurrentHashMap<>();

        //开启线程检测心跳
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        if (isRunning) {
                            checkHeartbeat();
                        }
                        //10s一次
                        TimeUnit.SECONDS.sleep(10);
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
        System.out.println("jobManager " + self() + " 关闭中...");
    }

    /**
     * Actor 重启前调用，用于清理崩溃的数据
     */
    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        System.out.println("jobManager 重启开始...");

    }

    /**
     * Actor 重启后调用，用于崩溃后的初始化，默认调用 preStart
     */
    @Override
    public void postRestart(Throwable reason) throws Exception {
        System.out.println("jobManager 重启完成...");
    }

    /**
     * 心跳检测，超过5s没有心跳，自动关闭
     */
    public void checkHeartbeat() {
        long currentTime = System.currentTimeMillis();
        Enumeration<ActorRef> keys = heartbeat.keys();
        while (keys.hasMoreElements()) {
            ActorRef actorRef = keys.nextElement();
            if (currentTime - heartbeat.get(actorRef) > 5000) {
                System.out.println(actorRef + " 已经挂掉了...尝试关闭它");
                getContext().stop(actorRef);
            }
        }

    }

    /**
     * 关闭所有taskManager actor
     */
    public void stopTaskManagerActor() {
        Enumeration<ActorRef> keys = heartbeat.keys();
        while (keys.hasMoreElements()) {
            ActorRef actorRef = keys.nextElement();
            System.out.println(actorRef + " 尝试给taskManager发送stop指令，关闭它.");
            actorRef.tell("stop", self());
        }
    }


    /**
     * 提供静态的props配置管理，同时也可以传入一些参数，用于JobManagerActor初始化管理
     */
    static Props props() {
        return Props.create(JobManagerActor.class, JobManagerActor::new);
    }


    /**
     * 重写接收消息的方法
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()

                //初始化taskManager
                .match(InitRequest.class, initRequest -> {
                    System.out.println("jobManager 开始初始化...");
                    for (int i = 0; i < initRequest.getTaskManagerNumber(); i++) {
                        //创建taskManager actor
                        getContext().actorOf(TaskManagerActor.props(), "taskManager-" + i);
                    }
                })
                //收到taskManager 上报[初始化、心跳]
                .match(TaskManagerRequest.class, taskManagerRequest -> {
                    if (taskManagerRequest.getType().equals("init")) {
                        System.out.println("jobManager 收到 sender " + getSender() + " 初始化请求");
                        heartbeat.put(getSender(), taskManagerRequest.getTs());
                        //只要有一个taskManager开启了，就开启心跳检测
                        if (!isRunning) isRunning = true;
                    }

                    if (taskManagerRequest.getType().equals("heartbeat")) {
                        System.out.println("jobManager 收到 sender " + getSender() + " 心跳上报请求");
                        heartbeat.put(getSender(), taskManagerRequest.getTs());
                    }
                })
                //收到停服请求
                .matchEquals("stop", stopRequest -> {
                    System.out.println("jobManager 收到 sender " + getSender() + " stop 请求，开始关闭sender");
                    isRunning = false;
                    //关闭taskManager
                    stopTaskManagerActor();
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception e) {
                    }
                    //关闭自己
                    getContext().stop(self());
                })
//                .matchEquals("init",
//                        message -> System.out.printf("hello word %s,i am %s", getSender().path().name(), getSelf().path().name()))
                .build();
    }
}
