package com.intelliment.control;

import com.intelliment.entity.IpAddress;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SubnetUtilsAnalyzerTest {

    // SUT
    AddressAnalyzer analyzer = new SubnetUtilsAnalyzer();

    @Test
    public void valueOf() throws Exception {
        IpAddress generated = analyzer.valueOf("192.26.5.2/12");
        IpAddress expected = expected();
        assertEquals(expected, generated);
    }

    @Test
    public void isInRange() throws Exception {
        assertTrue(analyzer.isInRange("192.31.255.252", expected()));
    }

    @Test
    public void isNotInRange() throws Exception {
        assertFalse(analyzer.isInRange("191.168.255.214", expected()));
    }

    private IpAddress expected() {
        return new IpAddress.IpAddressBuilder()
                .setCidr("192.26.5.2/12")
                .setMask("255.240.0.0")
                .setBroadcast("192.31.255.255")
                .setMaxHostDir("192.31.255.254")
                .setMinHostDir("192.16.0.1")
                .setNetIdentifier("192.16.0.0")
                .setTotalHostsInNet(1048574)
                .build();
    }
}