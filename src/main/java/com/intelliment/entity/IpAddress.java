package com.intelliment.control;

import java.util.Objects;

/**
 * This class is immutable, therefore all its fields can be public.
 *
 * @author Sergio Verde
 */
public class IpAddress {

    public final String type;
    public final String mask;
    public final String cidr;
    public final String broadcast;
    public final String netIdentifier;
    public final String maxHostDir;
    public final String minHostDir;
    public final int totalHostsInNet;

    public IpAddress(IpAddressBuilder ipAddressBuilder) {
        this.type = ipAddressBuilder.type;
        this.mask = ipAddressBuilder.mask;
        this.broadcast = ipAddressBuilder.broadcast;
        this.netIdentifier = ipAddressBuilder.netIdentifier;
        this.maxHostDir = ipAddressBuilder.maxHostDir;
        this.minHostDir = ipAddressBuilder.minHostDir;
        this.totalHostsInNet = ipAddressBuilder.totalHostsInNet;
        this.cidr = ipAddressBuilder.cidr;
    }

    public static class IpAddressBuilder {

        public String type;
        public String mask;
        public String cidr;
        public String broadcast;
        public String netIdentifier;
        public String maxHostDir;
        public String minHostDir;
        public int totalHostsInNet;

        public IpAddress build() {
            return new IpAddress(this);
        }
    }

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
