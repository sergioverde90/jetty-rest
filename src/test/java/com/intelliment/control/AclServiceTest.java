package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Collections;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
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
        boolean allowed = service.isAllowed(any(Request.class));
        // Assert
        assertTrue(allowed);
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
        AclEntry aclEntry = service.get(1);
        assertNotNull(aclEntry);
    }

}