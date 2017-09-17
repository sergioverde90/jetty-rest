package com.intelliment.boundary;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ResourceTest {

    @Test
    public void sayHello() throws Exception {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("name", "sergio");
        builder.add(ob.build());
        System.out.println("builder = " + builder.build());
    }

}