package com.sergio.resources.entity;

import com.sergio.resources.Main;

public class AclEntry {

    final private IpAddress source;
    final private IpAddress destination;
    final private Object protocol;
    final private Object action;
    final Main.AddressAnalyzer analyzer;

    public AclEntry(PolicyBuilder builder) {
        this.source = builder.getSource();
        this.destination = builder.getSource();
        this.protocol = builder.getProtocol();
        this.action = builder.getAction();
        this.analyzer = builder.analyzer;
    }

    public boolean matches(Request request) {
        String requestSource = request.source;
        if(!analyzer.isInRange(requestSource, source)) return false;
        String requestDestination = request.destination;
        if(!analyzer.isInRange(requestDestination, destination)) return false;
        System.out.println("this = " + this);
        return true;
    }

    @Override
    public String toString() {
        return "{\"AclEntry\":{"
                + "\"source\":" + source
                + ", \"destination\":" + destination
                + ", \"protocol\":" + protocol
                + ", \"action\":" + action
                + ", \"analyzer\":" + analyzer
                + "}}";
    }
}
