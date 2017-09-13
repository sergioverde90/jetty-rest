package com.sergio.resources.control;

import com.sergio.resources.entity.AclEntry;

public interface RequestMapper<T> {
    AclEntry map(T request);
}
