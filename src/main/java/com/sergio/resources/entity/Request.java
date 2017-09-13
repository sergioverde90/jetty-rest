package com.sergio.resources.entity;

public class Request {

    final String source;
    final String destination;
    final Object protocol;

    public Request(String source, String destination, Object protocol) {
        this.destination = destination;
        this.source = source;
        this.protocol = protocol;
    }

}
