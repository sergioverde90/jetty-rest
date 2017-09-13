package com.sergio.resources.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/intelliment")
public class Resource {

    @GET
    @Path("/acl")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        System.out.println("hello from Jersey!");
        return Response.ok("hello from Jersey!", MediaType.TEXT_PLAIN).build();
    }
}
