package com.intelliment.control;

import com.intelliment.Main;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AclFileLoaderTest {

    // SUT
    AclLoader loader = new AclFileLoader(new StringRequestMapper());

    @Test
    public void readSourcesNotNull() throws Exception {
        List<AclEntry> entries = loader.readSources();
        assertNotNull(entries);
    }

    @Test
    public void readSourcesNotEmpty() throws Exception {
        List<AclEntry> entries = loader.readSources();
        assertNotEmpty(entries);
    }

    @Test
    public void readSourcesLength() throws Exception {
        List<AclEntry> entries = loader.readSources();
        assertEquals(3, entries.size());
    }

    @Test
    public void readSourcesEquals() throws Exception {
        List<AclEntry> entries = loader.readSources();
        AclEntry entry = entries.get(0);
        AclEntry expected = getExpectedEntry();
        assertEquals(expected, entry);
    }

    private void assertNotEmpty(List<AclEntry> entries) {
        assertFalse(entries.isEmpty());
    }

    public AclEntry getExpectedEntry() {
        AclEntryBuilder builder = new AclEntryBuilder(new Main.SubnetUtilsAnalyzer());
        builder.source("43.0.0.0/8");
        builder.destination("any");
        builder.protocol("udp/53839,49944,58129,21778");
        builder.action("deny");
        return builder.build();
    }
}