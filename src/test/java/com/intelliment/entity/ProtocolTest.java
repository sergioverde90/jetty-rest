package com.intelliment.entity;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;

public class ProtocolTest {

    @Test
    public void newInstance() throws Exception {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, new HashSet<>(Arrays.asList(80, 8080)));
        assertNotNull(protocol);
    }

    @Test
    public void newInstanceSecondOption() throws Exception {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, 80, 8080);
        assertNotNull(protocol);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newInstanceMaxPortReached() throws Exception {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, new HashSet<>(Collections.singleton(70000)));
        assertNotNull(protocol);
    }

    @Test
    public void equalsTest() {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol toCompare = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        assertEquals(protocol, toCompare);
    }

    @Test
    public void hashCodeTest() {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol toCompare = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        assertEquals(protocol.hashCode(), toCompare.hashCode());
    }

    @Test
    public void toStringTest() {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        String expected = "{\"Protocol\":{\"type\":\"TCP\", \"ports\":80}}";
        assertEquals(expected, protocol.toString());
    }

    @Test
    public void valueOf() throws Exception {
        Protocol protocol = Protocol.valueOf("udp/50");
        Protocol expected = Protocol.newInstance(Protocol.ProtocolType.UDP, 50);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfMultiplePort() throws Exception {
        Protocol protocol = Protocol.valueOf("udp/80,8080,9090");
        Protocol expected = Protocol.newInstance(Protocol.ProtocolType.UDP, 80,8080,9090);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfAny() throws Exception {
        Protocol protocol = Protocol.valueOf("tcp/any");
        Protocol expected = Protocol.newInstance(Protocol.ProtocolType.TCP, Constants.ANY_PORT);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfOnlyAny() throws Exception {
        Protocol protocol = Protocol.valueOf(Constants.OPEN_WORLD_LABEL);
        Protocol expected = Protocol.newInstance(Protocol.ProtocolType.ANY, Constants.ANY_PORT);
        assertEquals(expected, protocol);
    }

    @Test
    public void isInRange() throws Exception {
        Protocol in = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = Protocol.newInstance(Protocol.ProtocolType.ANY, Constants.ANY_PORT);
        assertTrue(inRange.isInRange(in));
    }

    @Test
    public void isBetweenRanges() throws Exception {
        Protocol in = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = Protocol.newInstance(Protocol.ProtocolType.ANY, 8080, 9090, 80, 6201);
        assertTrue(inRange.isInRange(in));
    }

    @Test
    public void isNotInRangeByProtocol() throws Exception {
        Protocol in = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = Protocol.newInstance(Protocol.ProtocolType.UDP, 80);
        assertFalse(inRange.isInRange(in));
    }

    @Test
    public void isNotInRangeByPort() throws Exception {
        Protocol in = Protocol.newInstance(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = Protocol.newInstance(Protocol.ProtocolType.TCP, 8080);
        assertFalse(inRange.isInRange(in));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfIllegalFormat() throws Exception {
        Protocol.valueOf("udp/50,");
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfProtocol() throws Exception {
        Protocol.valueOf("*dp/50");
    }

}