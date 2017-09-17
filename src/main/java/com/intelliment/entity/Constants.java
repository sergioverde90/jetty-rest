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
    public static final String OPEN_WORLD_ADDRESS = "0.0.0.0";
    public static final int MAX_PORT_ALLOWED = 65535; // 2^16
}
