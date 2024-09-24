package com.akka.demo.mailbox.custom;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyMessageQueue implements MyUnboundedMessageQueueSemantics, MessageQueue {

    private final Queue<Envelope> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void enqueue(ActorRef receiver, Envelope handle) {
        System.out.println("receiver: " + receiver + " msg: " + handle);
        queue.offer(handle);
    }

    @Override
    public Envelope dequeue() {
        System.out.println("poll msg====");
        return queue.poll();
    }

    @Override
    public int numberOfMessages() {
        System.out.println("numberOfMessages: " + queue.size());
        return queue.size();
    }

    @Override
    public boolean hasMessages() {
        System.out.println("hasMessages: " + queue.size());
        return !queue.isEmpty();
    }

    @Override
    public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
        System.out.println("cleanUp: " + owner);
        for (Envelope handle : queue) {
            deadLetters.enqueue(owner, handle);
        }
    }
}
