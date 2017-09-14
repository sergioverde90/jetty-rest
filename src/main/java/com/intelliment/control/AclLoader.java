package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.util.List;

/**
 * Acl loader from external source
 *
 * @param <T> external source type
 */
public interface AclLoader<T> {

    List<T> rawSources();
    List<AclEntry> map(List<T> sources);
    List<AclEntry> readAndMap();

}
