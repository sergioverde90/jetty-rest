package com.intelliment.entity;

public class Request {

    public final String source;
    public final String destination;
    public final Protocol protocol; // FIXME: this must be a Java enum

    public Request(String source, String destination, Protocol protocol) {
        this.destination = destination;
        this.source = source;
        this.protocol = protocol;
    }

}
