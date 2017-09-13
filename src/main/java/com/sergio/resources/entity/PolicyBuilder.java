package com.sergio.resources.entity;

import com.sergio.resources.Main;

public class PolicyBuilder {

    public final Main.AddressAnalyzer analyzer;
    private IpAddress source;
    private IpAddress destination;
    private Object protocol;
    private Object action;

    public PolicyBuilder(Main.AddressAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public AclEntry build() {
        if(null == source) throw new IllegalStateException("source must be declared");
        if(null == destination) throw new IllegalStateException("destination must be declared");
        if(null == protocol) throw new IllegalStateException("destination must be declared");
        if(null == action) throw new IllegalStateException("destination must be declared");
        return new AclEntry(this);
    }

    public IpAddress getSource() {
        return source;
    }

    public void source(String source) {
        this.source = analyzer.valueOf(source);
    }

    public Object getProtocol() {
        return protocol;
    }

    public void protocol(Object protocol) {
        this.protocol = protocol;
    }

    public Object getAction() {
        return action;
    }

    public void action(Object action) {
        this.action = action;
    }

    public IpAddress getDestination() {
        return destination;
    }

    public void destination(String destination) {
        this.destination = analyzer.valueOf(destination);
    }

}
