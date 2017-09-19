package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Protocol;

import javax.json.*;
import java.io.StringReader;
import java.util.Collection;
import java.util.stream.Collectors;

public class AclEntryJsonParser implements JsonParser<AclEntry> {

    @Override
    public String parse(AclEntry entry) {
        JsonObject jsonObject = getJson(entry);
        return jsonObject.toString();
    }

    @Override
    public String parse(Collection<AclEntry> entries) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (AclEntry entry : entries) {
            JsonObject json = getJson(entry);
            arrBuilder.add(json);
        }
        return arrBuilder.build().toString();
    }

    @Override
    public AclEntry parse(String type) {
        JsonReader reader = Json.createReader(new StringReader(type));
        JsonObject jsonObject = reader.readObject();
        return new AclEntryBuilder(new SubnetUtilsAnalyzer())
                .setId(jsonObject.getInt("id"))
                .source(jsonObject.getString("source"))
                .destination(jsonObject.getString("destination"))
                .protocol(Protocol.valueOf(jsonObject.getString("protocol")))
                .action(AclEntry.ActionType.valueOf(jsonObject.getString("action")))
                .build();
    }

    @Override
    public Collection<AclEntry> parseEntries(String types) {
        JsonReader reader = Json.createReader(new StringReader(types));
        JsonArray jsonArray = reader.readArray();
        return jsonArray.stream()
                .map(j -> parse(j.toString()))
                .collect(Collectors.toList());
    }

    private static JsonObject getJson(AclEntry json) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        return builder
                .add("id", json.id)
                .add("source", json.source.cidr)
                .add("destination", json.destination.cidr)
                .add("protocol", json.protocol.toString())
                .add("action", json.action.toString())
                .build();
    }

}
