package com.intelliment.boundary;

import com.intelliment.control.AclEntryJsonParser;
import com.intelliment.control.AclFileLoader;
import com.intelliment.control.AclService;
import com.intelliment.control.AclServiceImpl;
import com.intelliment.control.exception.NotAllowedException;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.Request;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/intelliment")
public class Resource {

    AclEntryJsonParser parser;
    AclService service;

    public Resource(){}

    public Resource(AclService service, AclEntryJsonParser parser){
        this.service = service;
        this.parser = parser;
    }

    @PostConstruct
    public void onInit() {
        this.service = new AclServiceImpl(new AclFileLoader());
        this.parser = new AclEntryJsonParser();
    }

    /**
     * Returns the whole acl
     *
     * @return
     */
    @GET
    @Path("/acl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acl() {
        Collection<AclEntry> acl = service.acl();
        String jsonFormat = toJSON(acl);
        return Response.ok(jsonFormat, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Returns the single rule given the id
     *
     * @param id rule
     *
     * @return rule found
     */
    @GET
    @Path("/acl/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aclEntry(@PathParam("id") int id) {
        AclEntry entry = service.get(id);
        String jsonFormat = toJSON(entry);
        return Response.ok(jsonFormat, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Return the first rule that their fields match with the packet
     * fields in order to know what action to apply.
     *
     * <p>For example, for a packet with fields: [source=”192.168.0.5”, destination=”192.168.0.1”
     * and protocol=”UDP/80”] the first rule that match it will be the number 3.</p>
     *
     * Note that the IP address 192.168.0.5 is contained in the subnet
     * 192.168.0.0/24, the IP 192.168.0.2 is contained in the subnet
     * 192.168.0.0/28 and udp/80 is a subset of the udp/any.
     *
     * @param request to test matches
     *
     * @return matcher found
     */
    @POST
    @Path("/acl")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response match(Request request) {
        try {
            AclEntry entry = service.isAllowed(request);
            String jsonFormat = toJSON(entry);
            return Response.ok(jsonFormat, MediaType.APPLICATION_JSON).build();
        }catch (NotAllowedException nae){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private String toJSON(AclEntry entry) {
        return parser.parse(entry);
    }

    private String toJSON(Collection<AclEntry> entries) {
        return parser.parse(entries);
    }
}
