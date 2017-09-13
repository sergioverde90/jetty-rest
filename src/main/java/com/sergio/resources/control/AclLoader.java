package com.sergio.resources.control;

import com.sergio.resources.entity.AclEntry;

import java.util.Collection;

public interface AclLoader {
    Collection<AclEntry> readSources();
}
