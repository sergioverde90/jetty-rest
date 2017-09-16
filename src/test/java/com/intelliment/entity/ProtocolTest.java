package com.intelliment.entity;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProtocolTest {

    @Test
    public void newInstance() throws Exception {
        Protocol protocol = Protocol.newInstance(Protocol.ProtocolType.TCP, new HashSet<>(Arrays.asList(80, 8080)));
        assertNotNull(protocol);
    }

    @Test
    public void valueOf() throws Exception {
        Protocol protocol = Protocol.valueOf("udp/50");
        Protocol expected = getExpected(Protocol.ProtocolType.UDP, 50);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfMultiplePort() throws Exception {
        Protocol protocol = Protocol.valueOf("udp/80,8080,9090");
        Protocol expected = getExpected(Protocol.ProtocolType.UDP, 80,8080,9090);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfAny() throws Exception {
        Protocol protocol = Protocol.valueOf("tcp/any");
        Protocol expected = getExpected(Protocol.ProtocolType.TCP, Constants.ANY_PORT);
        assertEquals(expected, protocol);
    }

    @Test
    public void valueOfOnlyAny() throws Exception {
        Protocol protocol = Protocol.valueOf("any");
        Protocol expected = getExpected(Protocol.ProtocolType.ANY, Constants.ANY_PORT);
        assertEquals(expected, protocol);
    }

    @Test
    public void isInRange() throws Exception {
        Protocol in = getExpected(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = getExpected(Protocol.ProtocolType.ANY, Constants.ANY_PORT);
        assertTrue(inRange.isInRange(in));
    }

    @Test
    public void isNotInRangeByProtocol() throws Exception {
        Protocol in = getExpected(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = getExpected(Protocol.ProtocolType.UDP, 80);
        assertFalse(inRange.isInRange(in));
    }

    @Test
    public void isNotInRangeByPort() throws Exception {
        Protocol in = getExpected(Protocol.ProtocolType.TCP, 80);
        Protocol inRange = getExpected(Protocol.ProtocolType.TCP, 8080);
        assertFalse(inRange.isInRange(in));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfIllegalFormat() throws Exception {
        Protocol.valueOf("udp/50,");
    }

    private Protocol getExpected(Protocol.ProtocolType type, int... ports) {
        Set<Integer> set = new HashSet<>();
        for (int port : ports) { set.add(port); }
        return Protocol.newInstance(type, set);
    }

}