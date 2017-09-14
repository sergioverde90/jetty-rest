package com.intelliment.entity;

import java.util.Objects;

public class IpAddress {

    public String type; // A, B or C
    public String mask; // 255.0.0.0 / 255.255.0.0 / 255.255.255.0
    public String cidr;
    public String broadcast;
    public String netIdentifier;
    public String maxHostDir;
    public String minHostDir;
    public int totalHostsInNet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddress ipAddress = (IpAddress) o;
        return Objects.equals(cidr, ipAddress.cidr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cidr);
    }

    @Override
    public String toString() {
        return "{\"IpAddress\":{"
                + "\"type\":\"" + type + "\""
                + ", \"mask\":\"" + mask + "\""
                + ", \"cidr\":\"" + cidr + "\""
                + ", \"broadcast\":\"" + broadcast + "\""
                + ", \"netIdentifier\":\"" + netIdentifier + "\""
                + ", \"maxHostDir\":\"" + maxHostDir + "\""
                + ", \"minHostDir\":\"" + minHostDir + "\""
                + ", \"totalHostsInNet\":\"" + totalHostsInNet + "\""
                + "}}";
    }
}
