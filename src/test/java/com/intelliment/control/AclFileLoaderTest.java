package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Protocol;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AclFileLoaderTest {

    // SUT
    AclLoader loader = new AclFileLoader();

    @Test
    public void readSourcesNotNull() throws Exception {
        List<AclEntry> entries = loader.sources();
        assertNotNull(entries);
    }

    @Test
    public void readSourcesNotEmpty() throws Exception {
        List<AclEntry> entries = loader.sources();
        assertNotEmpty(entries);
    }

    @Test
    public void readSourcesLength() throws Exception {
        List<AclEntry> entries = loader.sources();
        assertEquals(1000, entries.size());
    }

    @Test
    public void readSourcesEquals() throws Exception {
        List<AclEntry> entries = loader.sources();
        AclEntry entry = entries.get(0);
        AclEntry expected = getExpectedEntry();
        assertEquals(expected, entry);
    }

    private void assertNotEmpty(List<AclEntry> entries) {
        assertFalse(entries.isEmpty());
    }

    public AclEntry getExpectedEntry() {
        AclEntryBuilder builder = new AclEntryBuilder(new SubnetUtilsAnalyzer());
        builder.source("43.0.0.0/8");
        builder.destination("0.0.0.0/32");
        builder.protocol(Protocol.valueOf("udp/53839,49944,58129,21778"));
        builder.action("deny");
        return builder.build();
    }
}