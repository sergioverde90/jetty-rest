package com.intelliment.entity;

import com.intelliment.Main;

public class AclEntry {

    final private IpAddress source;
    final private IpAddress destination;
    final private Object protocol;
    final private Object action;
    final Main.AddressAnalyzer analyzer;

    AclEntry(AclEntryBuilder builder) {
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
