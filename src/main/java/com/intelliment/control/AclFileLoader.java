package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AclFileLoader implements AclLoader<String> {

    private final RequestMapper<String> mapper;

    public AclFileLoader(RequestMapper<String> mapper){
        this.mapper = mapper;
    }

    @Override
    public List<String> rawSources() {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("intelliment-devtest-acl.txt");
        List<String> policies = new ArrayList<>();
        try(BufferedReader bis = new BufferedReader(new InputStreamReader(resource))){
            String policy;
            while((policy = bis.readLine()) != null) {
                policies.add(policy);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return policies;
    }

    @Override
    public List<AclEntry> map(List<String> sources) {
        List<AclEntry> acl = new ArrayList<>();
        for (String source : sources) {
            acl.add(mapper.map(source));
        }
        return acl;
    }

    @Override
    public List<AclEntry> readAndMap() {
        List<String> souces = rawSources();
        return map(souces);
    }

}
