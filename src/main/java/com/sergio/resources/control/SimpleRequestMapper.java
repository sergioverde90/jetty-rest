package com.sergio.resources.control;

import com.sergio.resources.Main;
import com.sergio.resources.entity.AclEntry;
import com.sergio.resources.entity.PolicyBuilder;

public class SimpleRequestMapper implements RequestMapper<String> {

    @Override
    public AclEntry map(String request) {
        String source = extractFrom(request);
        String destination =  extractTo(request);
        String protocol = extractProtocol(request);
        String action = extractAction(request);
        PolicyBuilder builder = new PolicyBuilder(new Main.SubnetUtilsAnalyzer());
        builder.source(source);
        builder.destination(destination);
        builder.protocol(protocol);
        builder.action(action);
        return builder.build();
    }

    private static String extractTo(String input) {
        int toIndex = input.indexOf("to") + "to".length();
        int withIndex = input.indexOf("with");
        return toCIDR(input.substring(toIndex, withIndex).trim());
    }

    private static String extractAction(String input) {
        int arrowIndex = input.indexOf("=>");
        return input.substring(arrowIndex, input.length()).trim();
    }

    private static String extractProtocol(String input) {
        int withIndex = input.indexOf("with")+"with".length();
        int arrowIndex = input.indexOf("=>");
        return input.substring(withIndex, arrowIndex).trim();
    }

    private static String toCIDR(String input) {
        return (!input.contains("/")) ? input.concat("/32") : input;
    }

    private static String extractFrom(String input) {
        int fromIndex = input.indexOf("from") + "from".length();
        int toIndex = input.indexOf("to");
        return toCIDR(input.substring(fromIndex, toIndex).trim());
    }

}
