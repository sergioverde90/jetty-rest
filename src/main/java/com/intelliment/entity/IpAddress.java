package com.intelliment.entity;

import com.intelliment.entity.annotation.Immutable;

import java.util.Objects;

/**
 * This class is immutable, therefore all its fields can be public.
 *
 * @author Sergio Verde
 */
@Immutable
public class IpAddress {

    public final String mask;
    public final String cidr;
    public final String broadcast;
    public final String netIdentifier;
    public final String maxHostDir;
    public final String minHostDir;
    public final int totalHostsInNet;

    public IpAddress(IpAddressBuilder ipAddressBuilder) {
        this.mask = ipAddressBuilder.mask;
        this.broadcast = ipAddressBuilder.broadcast;
        this.netIdentifier = ipAddressBuilder.netIdentifier;
        this.maxHostDir = ipAddressBuilder.maxHostDir;
        this.minHostDir = ipAddressBuilder.minHostDir;
        this.totalHostsInNet = ipAddressBuilder.totalHostsInNet;
        this.cidr = ipAddressBuilder.cidr;
    }

    public static class IpAddressBuilder {

        private String mask;
        private String cidr;
        private String broadcast;
        private String netIdentifier;
        private String maxHostDir;
        private String minHostDir;
        private int totalHostsInNet;

        public IpAddress build() {
            return new IpAddress(this);
        }

        public IpAddressBuilder setMask(String mask) {
            this.mask = mask;
            return this;
        }

        public IpAddressBuilder setCidr(String cidr) {
            this.cidr = cidr;
            return this;
        }

        public IpAddressBuilder setBroadcast(String broadcast) {
            this.broadcast = broadcast;
            return this;
        }

        public IpAddressBuilder setNetIdentifier(String netIdentifier) {
            this.netIdentifier = netIdentifier;
            return this;
        }

        public IpAddressBuilder setMaxHostDir(String maxHostDir) {
            this.maxHostDir = maxHostDir;
            return this;
        }

        public IpAddressBuilder setMinHostDir(String minHostDir) {
            this.minHostDir = minHostDir;
            return this;
        }

        public IpAddressBuilder setTotalHostsInNet(int totalHostsInNet) {
            this.totalHostsInNet = totalHostsInNet;
            return this;
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
                + "\"mask\":\"" + mask + "\""
                + ", \"cidr\":\"" + cidr + "\""
                + ", \"broadcast\":\"" + broadcast + "\""
                + ", \"netIdentifier\":\"" + netIdentifier + "\""
                + ", \"maxHostDir\":\"" + maxHostDir + "\""
                + ", \"minHostDir\":\"" + minHostDir + "\""
                + ", \"totalHostsInNet\":\"" + totalHostsInNet + "\""
                + "}}";
    }
}
