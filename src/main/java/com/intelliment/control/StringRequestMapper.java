package com.intelliment.control;

import com.intelliment.entity.AclEntry;
import com.intelliment.entity.AclEntryBuilder;
import com.intelliment.entity.Constants;
import com.intelliment.entity.Protocol;

import static com.intelliment.entity.Constants.*;

public class StringRequestMapper implements RequestMapper<String> {

    @Override
    public AclEntry map(String request) {
        int id = extractId(request);
        String source = extractFrom(request);
        String destination =  extractTo(request);
        Protocol protocol = extractProtocol(request);
        AclEntry.ActionType action = extractAction(request);
        AclEntryBuilder builder = new AclEntryBuilder(new SubnetUtilsAnalyzer());
        builder.setId(id);
        builder.source(source);
        builder.destination(destination);
        builder.protocol(protocol);
        builder.action(action);
        return builder.build();
    }

    private int extractId(String input) {
        int fromIndex = input.indexOf("from");
        return Integer.parseInt(input.substring(0, fromIndex).trim());
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
        return Constants.anyToAddress(address);
    }

    private static AclEntry.ActionType extractAction(String input) {
        int arrowIndex = input.indexOf("=>")+"=>".length();
        String action = input.substring(arrowIndex, input.length()).trim();
        return AclEntry.ActionType.valueOf(action.toUpperCase());
    }

    private static Protocol extractProtocol(String input) {
        int withIndex = input.indexOf("with")+"with".length();
        int arrowIndex = input.indexOf("=>");
        return Protocol.valueOf(input.substring(withIndex, arrowIndex).trim());
    }

    private static String toCIDR(String input) {
        return (!input.contains(SLASH)) ? input.concat(DEFAULT_NETMASK) : input;
    }

}
