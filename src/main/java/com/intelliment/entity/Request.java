package com.intelliment.entity;

import com.intelliment.entity.annotation.Immutable;

@Immutable
public class Request {

    public final String source;
    public final String destination;
    public final Protocol protocol;

    public Request(String source, String destination, Protocol protocol) {
        this.destination = destination;
        this.source = source;
        this.protocol = protocol;
    }

}
