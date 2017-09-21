package com.intelliment.boundary;

import com.intelliment.entity.Protocol;
import com.intelliment.entity.Request;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class AclEntryReaderProvider implements MessageBodyReader<Request> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return genericType == Request.class;
    }

    @Override
    public Request readFrom(Class<Request> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        JsonReader reader = Json.createReader(entityStream);
        JsonObject object = reader.readObject();
        String source = object.getString("source");
        String destination = object.getString("destination");
        String protocol = object.getString("protocol");
        return new Request(source, destination, Protocol.valueOf(protocol));
    }
}
