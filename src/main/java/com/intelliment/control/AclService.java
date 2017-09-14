package com.intelliment.control;

import com.intelliment.entity.Request;

public interface AclService {
    boolean isAllowed(Request request);
}
