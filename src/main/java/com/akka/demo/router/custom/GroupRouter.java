package com.akka.demo.router.custom;

import akka.actor.ActorSystem;
import akka.dispatch.Dispatchers;
import akka.routing.GroupBase;
import akka.routing.Router;

import java.util.List;

public class GroupRouter extends GroupBase {

    private final List<String> paths;

    private final int copySize;

    public GroupRouter(List<String> paths, int copySize) {
        this.paths = paths;
        this.copySize = copySize;
    }



    @Override
    public Iterable<String> getPaths(ActorSystem system) {
        return paths;
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(new RedundancyRoutingLogic(copySize));
    }

    @Override
    public String routerDispatcher() {
        return Dispatchers.DefaultDispatcherId();
    }
}
