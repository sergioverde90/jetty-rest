package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.util.List;

/**
 * Acl loader from external source
 */
public interface AclLoader {
    List<AclEntry> sources();
}
