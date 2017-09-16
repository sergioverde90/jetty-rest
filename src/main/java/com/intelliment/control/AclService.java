package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import java.util.Collection;

public interface AclService {
    boolean isAllowed(Request request);
    Collection<AclEntry> acl();
    AclEntry get(int id);
}
