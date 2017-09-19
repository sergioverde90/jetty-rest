package com.intelliment.it;

import com.intelliment.boundary.Resource;
import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AclIT {

    // SUT
    Resource resource;

    @Before
    public void init() {
         resource = new Resource();
         resource.onInit(); // @PostConstruct invocation
    }

    @Test
    public void allowed() {
        String source = "72.0.0.2";
        String destination = "48.59.6.58";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        String isAllowed = extractFromResponse(matches, String.class);
        assertEquals("{allowed : true}", isAllowed);
    }

    @Test
    public void notAllowed() {
        String source = "72.0.0.2";
        String destination = "48.59.6.58";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        String isAllowed = extractFromResponse(matches, String.class);
        assertEquals("{allowed : false}", isAllowed);
    }

    private <T> T extractFromResponse(Response response, Class<T> clazz) {
        return (T)response.getEntity();
    }

}
