package com.intelliment.boundary;

import com.intelliment.control.AclFileLoader;
import com.intelliment.control.AclService;
import com.intelliment.control.AclServiceImpl;
import com.intelliment.control.exception.NotAllowedException;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
        return Response.ok(acl, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/acl/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aclEntry(@PathParam("id") int id) {
        AclEntry entry = service.get(id);
        return Response.ok(entry, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/acl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response match(Request request) {
        try {
            AclEntry entry = service.isAllowed(request);
            return Response.ok(entry, MediaType.APPLICATION_JSON).build();
        }catch (NotAllowedException nae){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
