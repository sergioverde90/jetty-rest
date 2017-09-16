package com.intelliment.entity;

import com.intelliment.control.AddressAnalyzer;

import java.util.Objects;

public class AclEntry {

    public final IpAddress source;
    public final IpAddress destination;
    public final Protocol protocol;
    public final Object action;
    private final AddressAnalyzer analyzer;

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
        Protocol requestProtocol = request.protocol;
        if(!protocol.isInRange(requestProtocol)) return false;
        // TODO: action allowed here (must be the last computation)
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AclEntry aclEntry = (AclEntry) o;
        return Objects.equals(source, aclEntry.source) &&
                Objects.equals(destination, aclEntry.destination) &&
                Objects.equals(protocol, aclEntry.protocol) &&
                Objects.equals(action, aclEntry.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, protocol, action);
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
