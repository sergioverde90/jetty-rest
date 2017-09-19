package com.intelliment.control;

import com.intelliment.control.exception.NotAllowedException;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import java.util.Collection;

public interface AclService {
    AclEntry isAllowed(Request request) throws NotAllowedException;
    Collection<AclEntry> acl();
    AclEntry get(int id);
}
