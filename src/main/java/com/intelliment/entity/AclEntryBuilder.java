package com.intelliment.entity;

import com.intelliment.control.AddressAnalyzer;

public class AclEntryBuilder {

    public final AddressAnalyzer analyzer;
    private IpAddress source;
    private IpAddress destination;
    private Protocol protocol;
    private Object action;

    public AclEntryBuilder(AddressAnalyzer analyzer) {
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

    public Protocol getProtocol() {
        return protocol;
    }

    public void protocol(Protocol protocol) {
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
