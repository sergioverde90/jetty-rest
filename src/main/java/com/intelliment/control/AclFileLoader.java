package com.intelliment.control;

import com.intelliment.entity.AclEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AclFileLoader implements AclLoader {

    private final RequestMapper<String> mapper;

    public AclFileLoader(RequestMapper<String> mapper){
        this.mapper = mapper;
    }

    @Override
    public List<AclEntry> readSources() {
        List<AclEntry> entries = new ArrayList<>();
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("intelliment-devtest-acl.txt");
        try(BufferedReader bis = new BufferedReader(new InputStreamReader(resource))){
            String line;
            while((line = bis.readLine()) != null) {
                entries.add(mapper.map(line));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return entries;
    }

}
