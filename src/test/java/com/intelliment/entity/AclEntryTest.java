package com.intelliment.entity;

import com.intelliment.control.SubnetUtilsAnalyzer;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class AclEntryTest {

    @Test
    public void build() {
        AclEntry aclEntry = buildEntry();
        assertEquals("192.168.0.0/24", aclEntry.source.cidr);
        assertEquals("48.0.0.0/8", aclEntry.destination.cidr);
        assertEquals(1, aclEntry.id);
        assertEquals(Protocol.newInstance(Protocol.ProtocolType.UDP, Constants.ANY_PORT), aclEntry.protocol);
        assertEquals(AclEntry.ActionType.ALLOW, aclEntry.action);
    }

    @Test
    public void matches() throws Exception {
        AclEntry aclEntry = buildEntry();
        boolean match = aclEntry.matches(new Request("192.168.0.235", "48.1.2.16", Protocol.valueOf("udp/80")));
        assertTrue(match);
    }

    @Test
    public void notMatchesByDestination() throws Exception {
        AclEntry aclEntry = buildEntry();
        boolean match = aclEntry.matches(new Request("192.168.0.235", "49.1.2.16", Protocol.valueOf("udp/80")));
        assertFalse(match);
    }

    @Test
    public void notMatchesByProtocol() throws Exception {
        AclEntry aclEntry = buildEntry();
        boolean match = aclEntry.matches(new Request("192.168.0.235", "49.1.2.16", Protocol.valueOf("tcp/80")));
        assertFalse(match);
    }

    @Test
    public void equals() throws Exception {
        AclEntry aclEntry = buildEntry();
        AclEntry toCompare = buildEntry();
        assertEquals(toCompare, aclEntry);
    }

    @Test
    public void hashCodeTest() throws Exception {
        AclEntry aclEntry = buildEntry();
        AclEntry toCompare = buildEntry();
        assertEquals(toCompare.hashCode(), aclEntry.hashCode());
    }

    @Test
    public void toStringTest() throws Exception {
        fail();
    }

    private AclEntry buildEntry() {
        return new AclEntryBuilder(new SubnetUtilsAnalyzer())
                .setId(1)
                .source("192.168.0.0/24")
                .destination("48.0.0.0/8")
                .protocol(Protocol.valueOf("udp/any"))
                .action(AclEntry.ActionType.ALLOW)
                .build();
    }

}