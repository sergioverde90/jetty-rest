package com.intelliment.control;

import com.intelliment.entity.AclEntry;

public interface RequestMapper<T> {
    AclEntry map(T request);
}
