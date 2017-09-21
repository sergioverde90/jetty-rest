package com.intelliment.entity;

public class Constants {

    private Constants(){
        // avoid reflection instantiation
        throw new UnsupportedOperationException("this class cannot be instantiated");
    }

    public static final String OPEN_WORLD_LABEL = "any";
    public static final int ANY_PORT = -1;
    public static final String SLASH = "/";
    public static final String DEFAULT_NETMASK = "/32";
    public static final String OPEN_WORLD_ADDRESS = "0.0.0.0/32";
    public static final int MAX_PORT_ALLOWED = 65535; // 2^16

    public static String anyToAddress(String address) {
        if(OPEN_WORLD_LABEL.equalsIgnoreCase(address)) address = OPEN_WORLD_ADDRESS;
        return address;
    }

    public static String addressToAny(String cidr) {
        if(!cidr.equals(Constants.OPEN_WORLD_ADDRESS)) return cidr;
        return Constants.OPEN_WORLD_LABEL;
    }

    public static String addDefaultMask(String cidr) {
        int index = cidr.indexOf(Constants.SLASH);
        if(index == -1) return cidr.concat(Constants.DEFAULT_NETMASK);
        return cidr;
    }

    public static String removeDefaultMask(String cidr) {
        int index = cidr.indexOf(Constants.DEFAULT_NETMASK);
        if(index == -1) return cidr;
        return cidr.substring(0, index);
    }
}
