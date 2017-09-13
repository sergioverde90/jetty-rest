package com.sergio.resources.control;

import com.sergio.resources.entity.Request;

public interface AclService {
    boolean isAllowed(Request request);
}
