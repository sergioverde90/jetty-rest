package com.intelliment.control;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StringRequestMapperTest {

    // DoC
    StringRequestMapper mapper = new StringRequestMapper();
    AclLoader<String> loader = new InMemoryAclLoader(mapper);

    private List<String> sources;

    @Before
    public void init() {
        this.sources = loader.rawSources();
    }

    @Test
    public void map() throws Exception {
        for (String source : sources) {
            mapper.map(source);
        }
    }

}