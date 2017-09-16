package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.util.*;

/**
 * <b>This class only must be used for testing purposed</b>
 *
 * @author Sergio Verde
 */
class InMemoryAclLoader implements AclLoader {

    final RequestMapper<String> mapper;

    public InMemoryAclLoader(RequestMapper<String> mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AclEntry> sources() {
        List<String> sources = rawSources();
        return map(sources);
    }

    private List<String> rawSources() {
        String source1 = "1 from 192.168.0.10 to 192.168.0.2 with tcp/80 => deny";
        String source2 = "2 from 88.1.12.225 to 99.235.1.15 with tcp/80,8080 => deny";
        String source3 = "3 from 192.168.0.0/24 to 192.168.0.0/28 with udp/any => deny";
        String source4 = "4 from 43.0.0.0/8 to any with udp/53839,49944,58129,21778 => deny";
        return Arrays.asList(source1, source2, source3, source4);
    }

    private List<AclEntry> map(List<String> sources) {
        List<AclEntry> entries = new ArrayList<>();
        for (String source : sources) {
            AclEntry mapped = mapper.map(source);
            entries.add(mapped);
        }
        return entries;
    }
}
