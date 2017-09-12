package com.sergio.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resources")
public class Resource {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        System.out.println("hello from Jersey!");
        return Response.ok("hello from Jersey!", MediaType.TEXT_PLAIN).build();
    }
}
