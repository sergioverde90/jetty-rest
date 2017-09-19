package com.intelliment.boundary;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ResourceTest {

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
        String source = "228.110.252.2";
        String destination = "48.59.6.58";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(),  matches.getStatus());
    }

    @Test
    public void inRange() {
        String source = "245.200.223.2";
        String destination = "232.255.255.253";
        Protocol protocol = Protocol.valueOf("udp/44840");
        Request request = buildRequest(source, destination, protocol);
        Response matches = resource.match(request);
        AclEntry isAllowed = extractFromResponse(matches);
        assertNotNull(isAllowed);
        assertEquals(16, isAllowed.id);
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

    @Test
    public void aclEntry() {
        Response response = resource.aclEntry(998);
        AclEntry entry = extractFromResponse(response);
        assertNotNull(entry);
    }

    @Test
    public void aclEntryMatch() {
        Response response = resource.aclEntry(998);
        AclEntry entry = extractFromResponse(response);
        assertEquals(998, entry.id);
    }

    @Test(expected = NoSuchElementException.class)
    public void aclEntryNotFound() {
        resource.aclEntry(1598);
    }

    @Test
    public void aclNotNull() {
        Response response = resource.acl();
        Collection<AclEntry> entries = extractFromResponse(response);
        assertNotNull(entries);
    }

    @Test
    public void aclNoEmpty() {
        Response response = resource.acl();
        Collection<AclEntry> entries = extractFromResponse(response);
        assertThat(entries.isEmpty(), is(false));
    }

    private Request buildRequest(String source, String destination, Protocol protocol) {
        return new Request(source, destination, protocol);
    }

    private <T> T extractFromResponse(Response response) {
        return (T)response.getEntity();
    }

}