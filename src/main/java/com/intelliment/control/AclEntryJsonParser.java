package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Protocol;

import javax.json.*;
import java.io.StringReader;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.intelliment.entity.Constants.*;

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
                .source(revert(jsonObject.getString("source")))
                .destination(revert(jsonObject.getString("destination")))
                .protocol(Protocol.valueOf(jsonObject.getString("protocol")))
                .action(AclEntry.ActionType.valueOf(jsonObject.getString("action").toUpperCase()))
                .build();
    }

    @Override
    public Collection<AclEntry> parseEntries(String types) {
        JsonReader reader = Json.createReader(new StringReader(types));
        JsonArray jsonArray = reader.readArray();
        return jsonArray.stream()
                .map(Object::toString)
                .map(this::parse)
                .collect(Collectors.toList());
    }

    private static JsonObject getJson(AclEntry json) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        return builder
                .add("id", json.id)
                .add("source", format(json.source.cidr))
                .add("destination", format(json.destination.cidr))
                .add("protocol", json.protocol.toString())
                .add("action", json.action.toString().toLowerCase())
                .build();
    }

    private static String format(String cidr) {
        cidr = addressToAny(cidr);
        cidr = removeDefaultMask(cidr);
        return cidr;
    }

    private static String revert(String cidr) {
        cidr = anyToAddress(cidr);
        cidr = addDefaultMask(cidr);
        return cidr;
    }



}
