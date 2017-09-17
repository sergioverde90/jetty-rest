package com.intelliment.boundary;

import com.intelliment.control.AclFileLoader;
import com.intelliment.control.AclService;
import com.intelliment.control.AclServiceImpl;
import com.intelliment.entity.AclEntry;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/intelliment")
public class Resource {

    AclService service;

    @PostConstruct
    public void onInit() {
        this.service = new AclServiceImpl(new AclFileLoader());
    }

    @GET
    @Path("/acl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acl() {
        Collection<AclEntry> acl = service.acl();
        String jsonFormat = toJson(acl);
        return Response.ok(jsonFormat, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/acl/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aclEntry(@PathParam("id") int id) {
        AclEntry entry = service.get(id);
        String jsonFormat = toJson(entry);
        return Response.ok(jsonFormat, MediaType.APPLICATION_JSON).build();
    }

    private String toJson(AclEntry aclEntry) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("source", aclEntry.source.cidr);
        builder.add("destination", aclEntry.destination.cidr);
        builder.add("protocol", aclEntry.protocol.toString());
        builder.add("action", aclEntry.action.toString());
        return builder.build().toString();
    }

    private String toJson(Collection<AclEntry> acl) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (AclEntry entry : acl) {
            arrayBuilder.add(toJson(entry));
        }
        return arrayBuilder.build().toString();
    }
}
