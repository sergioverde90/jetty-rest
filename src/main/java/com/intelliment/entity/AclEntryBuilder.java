package com.intelliment.entity;

import com.intelliment.control.AddressAnalyzer;

public class AclEntryBuilder {

    private final AddressAnalyzer analyzer;
    private int id;
    private IpAddress source;
    private IpAddress destination;
    private Protocol protocol;
    private AclEntry.ActionType action;

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

    public Protocol getProtocol() {
        return protocol;
    }

    public AclEntry.ActionType getAction() {
        return action;
    }

    int getId() {
        return id;
    }

    AddressAnalyzer getAnalyzer() {
        return analyzer;
    }

    public AclEntryBuilder protocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public AclEntryBuilder source(String source) {
        this.source = analyzer.valueOf(source);
        return this;
    }

    public AclEntryBuilder action(AclEntry.ActionType action) {
        this.action = action;
        return this;
    }

    public IpAddress getDestination() {
        return destination;
    }

    public AclEntryBuilder destination(String destination) {
        this.destination = analyzer.valueOf(destination);
        return this;
    }

    public AclEntryBuilder setId(int id) {
        this.id = id;
        return this;
    }
}
