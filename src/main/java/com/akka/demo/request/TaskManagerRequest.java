package com.akka.demo.request;

/**
 * 初始化 taskManger 请求
 */
public class TaskManagerRequest {


    //类型
    private final String type;

    //时间戳
    private final long ts;

    public TaskManagerRequest(String type, long ts) {
        this.type = type;
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public long getTs() {
        return ts;
    }


}
