package com.intelliment.it;

import com.intelliment.boundary.Resource;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        String source = "119.214.86.17";
        String destination = "97.76.152.55";
        Protocol protocol = Protocol.valueOf("udp/44840");
        Request request = buildRequest(source, destination, protocol);
        Response matches = resource.match(request);
        AclEntry isAllowed = extractFromResponse(matches);
        assertNotNull(isAllowed);
        assertEquals(70, isAllowed.id);
    }

    @Test
    public void deny() {
        String source = "88.120.186.190";
        String destination = "3.104.58.75";
        Protocol protocol = Protocol.valueOf("udp/4961");
        Request request = buildRequest(source, destination, protocol);
        Response matches = resource.match(request);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),  matches.getStatus());
    }

    @Test
    public void notAllowed() {
        String source = "72.0.0.2";
        String destination = "48.59.6.58";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),  matches.getStatus());
    }

    @Test
    public void anyAllow() {
        String source = "72.0.0.2";
        String destination = "81.245.77.144";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        AclEntry isAllowed = extractFromResponse(matches);
        assertNotNull(isAllowed);
        assertEquals(24, isAllowed.id);
    }

    private Request buildRequest(String source, String destination, Protocol protocol) {
        return new Request(source, destination, protocol);
    }

    private <T> T extractFromResponse(Response response) {
        return (T)response.getEntity();
    }

}
