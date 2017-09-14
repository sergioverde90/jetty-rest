package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringRequestMapperTest {

    StringRequestMapper mapper = new StringRequestMapper();
    List<String> sources;
    @Before
    public void init() {
        sources = new ArrayList<>();
        sources.add("1 from 43.0.0.0/8 to any with udp/53839,49944,58129,21778 => deny");
    }

    @Test
    public void map() throws Exception {
        for (String source : sources) {
            AclEntry AclEntry = mapper.map(source);
        }
    }

}