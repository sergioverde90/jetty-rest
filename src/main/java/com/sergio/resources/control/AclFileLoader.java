package com.sergio.resources.control;

import com.sergio.resources.entity.AclEntry;

import java.util.List;

public class AclFileLoader implements AclLoader {

    private final RequestMapper<String> mapper;

    public AclFileLoader(RequestMapper<String> mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AclEntry> readSources() {
        mapper.map("");
        throw new UnsupportedOperationException("not yet implemented");
    }

}
