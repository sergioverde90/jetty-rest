package com.intelliment.control;

import com.intelliment.control.exception.NotAllowedException;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AclServiceTest {

    // DoC
    @Mock
    AclLoader loader;

    @Mock
    AclEntry entry;

    // SUT
    AclService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(loader.sources()).thenReturn(Collections.singletonList(entry));
        when(entry.matches(any(Request.class))).thenReturn(true);
        service = new AclServiceImpl(loader);
    }

    @Test
    public void isAllowed() throws Exception {
        // Act
        AclEntry allowed = service.isAllowed(any(Request.class));
        // Assert
        assertNotNull(allowed);
    }

    @Test(expected = NotAllowedException.class)
    public void isNotAllowed() throws Exception {
        when(entry.matches(any(Request.class))).thenReturn(false);
        // Act
        service.isAllowed(any(Request.class));
    }

    @Test
    public void aclNotNull() throws Exception {
        Collection<AclEntry> acl = service.acl();
        assertNotNull(acl);
    }

    @Test
    public void aclNotEmpty() {
        Collection<AclEntry> acl = service.acl();
        assertThat(acl.isEmpty(), is(false));
    }

    @Test
    public void get() throws Exception {
        // Arrange
        AclLoader loader = mock(AclLoader.class);
        when(loader.sources()).thenReturn(Arrays.asList(entry()));
        AclService service = new AclServiceImpl(loader);
        // Act
        AclEntry aclEntry = service.get(1);
        // Assert
        assertNotNull(aclEntry);
    }

    private AclEntry entry() {
        return new AclEntryBuilder(new SubnetUtilsAnalyzer())
                .setId(1)
                .source("192.6.2.3/32")
                .destination("45.2.2.3/8")
                .protocol(Protocol.valueOf("any"))
                .action(AclEntry.ActionType.DENY)
                .build();
    }

}