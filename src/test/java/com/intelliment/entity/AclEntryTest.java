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
    public void equals() throws Exception {
        fail();
    }

    @Test
    public void hashCodeTest() throws Exception {
        fail();
    }

    @Test
    public void toStringTest() throws Exception {
        fail();
    }

    private AclEntry buildEntry() {
        return new AclEntryBuilder(new SubnetUtilsAnalyzer())
                .source("192.168.0.0/24")
                .destination("48.0.0.0/8")
                .setId(1)
                .protocol(Protocol.valueOf("udp/any"))
                .action(AclEntry.ActionType.ALLOW)
                .build();
    }

}