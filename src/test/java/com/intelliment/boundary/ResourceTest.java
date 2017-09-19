package com.intelliment.boundary;

import com.intelliment.DiscardAnyAllowedLoader;
import com.intelliment.control.AclEntryJsonParser;
import com.intelliment.control.AclFileLoader;
import com.intelliment.control.AclService;
import com.intelliment.control.AclServiceImpl;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ResourceTest {

    // SUT
    Resource resource;
    AclEntryJsonParser parser;

    @Before
    public void init() {
        AclService loader = new AclServiceImpl(new DiscardAnyAllowedLoader(new AclFileLoader()));
        resource = new Resource(loader, new AclEntryJsonParser());
        parser = new AclEntryJsonParser();
    }

    @Test
    public void allowed() {
        String source = "119.214.86.17";
        String destination = "97.76.152.55";
        Protocol protocol = Protocol.valueOf("udp/44840");
        Request request = buildRequest(source, destination, protocol);
        Response matches = resource.match(request);
        String matchesFound = extractFromResponse(matches);
        assertNotNull(matchesFound);
        AclEntry entry = extractAclEntry(matchesFound);
        assertEquals(70, entry.id);
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
        String matchesFound = extractFromResponse(matches);
        assertNotNull(matchesFound);
        AclEntry entry = extractAclEntry(matchesFound);
        assertEquals(16, entry.id);
    }

    @Test
    public void anyAllow() {
        String source = "72.0.0.2";
        String destination = "81.245.77.144";
        Protocol protocol = Protocol.valueOf("tcp/8080,80,90");
        Request request = new Request(source, destination, protocol);
        Response matches = resource.match(request);
        String matchesFound = extractFromResponse(matches);
        assertNotNull(matchesFound);
        AclEntry entry = extractAclEntry(matchesFound);
        assertEquals(24, entry.id);
    }

    @Test
    public void aclEntry() {
        Response response = resource.aclEntry(998);
        String matchesFound = extractFromResponse(response);
        assertNotNull(matchesFound);
    }

    @Test
    public void aclEntryMatch() {
        Response response = resource.aclEntry(998);
        String matchesFound = extractFromResponse(response);
        assertNotNull(matchesFound);
        AclEntry entry = extractAclEntry(matchesFound);
        assertEquals(998, entry.id);
    }

    @Test(expected = NoSuchElementException.class)
    public void aclEntryNotFound() {
        resource.aclEntry(1598);
    }

    @Test
    public void aclNotNull() {
        Response response = resource.acl();
        String entries = extractFromResponse(response);
        assertNotNull(entries);
    }

    @Test
    public void aclNoEmpty() {
        Response response = resource.acl();
        String entries = extractFromResponse(response);
        Collection<AclEntry> acl = extractAclEntries(entries);
        assertThat(acl.isEmpty(), is(false));
    }

    private Request buildRequest(String source, String destination, Protocol protocol) {
        return new Request(source, destination, protocol);
    }

    private String extractFromResponse(Response response) {
        return (String) response.getEntity();
    }

    private AclEntry extractAclEntry(String jsonEntry) {
        return parser.parse(jsonEntry);
    }

    private Collection<AclEntry> extractAclEntries(String entries) {
        return parser.parseEntries(entries);
    }
}