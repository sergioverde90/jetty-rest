package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Constants;
import com.intelliment.entity.Protocol;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class StringRequestMapperTest {

    RequestMapper<String> mapper = new StringRequestMapper();

    @Test
    public void mapAnyAllow() throws Exception {
        String request = "905 from any to any with any => allow";
        AclEntry acl = mapper.map(request);
        AclEntry anyAllow = new AclEntryBuilder(new SubnetUtilsAnalyzer())
                .source(Constants.OPEN_WORLD_ADDRESS)
                .destination(Constants.OPEN_WORLD_ADDRESS)
                .protocol(Protocol.valueOf(Constants.OPEN_WORLD_LABEL))
                .action(AclEntry.ActionType.ALLOW)
                .build();
        assertEquals(anyAllow, acl);
    }

}