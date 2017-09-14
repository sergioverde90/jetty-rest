package com.intelliment.control;

import com.intelliment.Main;
import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;

public class StringRequestMapper implements RequestMapper<String> {

    private static final String SLASH = "/";
    private static final String DEFAULT_NETMASK = "/32";
    private static final String OPEN_WORLD_ADDRESS = "0.0.0.0";
    private static final String OPEN_WORLD_LABEL = "any";

    @Override
    public AclEntry map(String request) {
        String source = extractFrom(request);
        String destination =  extractTo(request);
        String protocol = extractProtocol(request);
        String action = extractAction(request);
        AclEntryBuilder builder = new AclEntryBuilder(new Main.SubnetUtilsAnalyzer());
        builder.source(source);
        builder.destination(destination);
        builder.protocol(protocol);
        builder.action(action);
        return builder.build();
    }

    private static String extractFrom(String input) {
        int fromIndex = input.indexOf("from") + "from".length();
        int toIndex = input.indexOf("to");
        String address = input.substring(fromIndex, toIndex).trim();
        address = checkAny(address);
        return toCIDR(address);
    }

    private static String extractTo(String input) {
        int toIndex = input.indexOf("to") + "to".length();
        int withIndex = input.indexOf("with");
        String address = input.substring(toIndex, withIndex).trim();
        address = checkAny(address);
        return toCIDR(address);
    }

    private static String checkAny(String address) {
        if(OPEN_WORLD_LABEL.equalsIgnoreCase(address)) address = OPEN_WORLD_ADDRESS;
        return address;
    }

    private static String extractAction(String input) {
        int arrowIndex = input.indexOf("=>")+"=>".length();
        return input.substring(arrowIndex, input.length()).trim();
    }

    private static String extractProtocol(String input) {
        int withIndex = input.indexOf("with")+"with".length();
        int arrowIndex = input.indexOf("=>");
        return input.substring(withIndex, arrowIndex).trim();
    }

    private static String toCIDR(String input) {
        return (!input.contains(SLASH)) ? input.concat(DEFAULT_NETMASK) : input;
    }

}
