package com.akka.demo.request;


/**
 * 初始化jobManager 请求，用来初始化jobManager以及taskManager actor
 * */
public class InitRequest {
    //初始化的task actor的 数量
    private final int taskManagerNumber;

    public InitRequest(int taskManagerNumber) {
        this.taskManagerNumber = taskManagerNumber;
    }

    public int getTaskManagerNumber() {
        return taskManagerNumber;
    }
}
