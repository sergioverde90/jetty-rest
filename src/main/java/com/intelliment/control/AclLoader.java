package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.util.List;

public interface AclLoader {
    List<AclEntry> readSources();
}
