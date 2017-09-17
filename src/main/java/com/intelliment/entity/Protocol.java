package com.intelliment.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.intelliment.entity.Constants.ANY_PORT;
import static com.intelliment.entity.Constants.MAX_PORT_ALLOWED;
import static com.intelliment.entity.Constants.OPEN_WORLD_LABEL;
import static java.util.stream.Collectors.toSet;

public class Protocol {

    public enum ProtocolType { TCP, UDP, ANY }

    private final ProtocolType type;
    private final Set<Integer> ports;

    private Protocol(ProtocolType type, Set<Integer> ports) {
        this.type = type;
        this.ports = new HashSet<>(ports);
    }
    
    static Protocol newInstance(ProtocolType type, Set<Integer> ports) {
        if(ports.stream().anyMatch((p) -> p > MAX_PORT_ALLOWED))
            throw new IllegalArgumentException("No port can be greater than "+MAX_PORT_ALLOWED);
        return new Protocol(type, ports);
    }

    static Protocol newInstance(ProtocolType type, int... ports) {
        Set<Integer> set = IntStream.of(ports).boxed().collect(toSet());
        return Protocol.newInstance(type, set);
    }

    /**
     * <p>
     * The format must follow the following structure
     * </p>
     * <i> 'any' OR TCP | UDP)/port[,port] or 'any'</i>
     *
     * @param format
     *
     * @return protocol instance
     */
    public static Protocol valueOf(String format) {
        if(Constants.OPEN_WORLD_LABEL.equals(format)) return newInstance(ProtocolType.ANY, Collections.singleton(-1));
        // regex patter for (TCP | UDP)/port[,port] or 'any'.
        String regex = "(TCP|UDP|tcp|udp)/(any|(\\d{1,5}(,\\d{1,5})*)+)";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(format).matches()) throw new IllegalArgumentException(format+" is not a valid format");
        ProtocolType protocolType = extractProtocol(format);
        Set<Integer> ports = extractPorts(format);
        return new Protocol(protocolType, ports);
    }

    public boolean isInRange(Protocol requestProtocol) {
        System.out.println("isAnyProtocol() = " + isAnyProtocol());
        System.out.println("isAnyPort() = " + isAnyPort());
        System.out.println("matchProtocols(requestProtocol) = " + matchProtocols(requestProtocol));
        System.out.println("matchAllPorts(requestProtocol) = " + matchAllPorts(requestProtocol));
        return matchProtocols(requestProtocol) && matchAllPorts(requestProtocol);
    }

    private boolean matchAllPorts(Protocol requestProtocol) {
        return isAnyPort() || ports.containsAll(requestProtocol.ports);
    }

    private boolean isAnyProtocol() {
        return type.equals(ProtocolType.ANY);
    }

    private boolean matchProtocols(Protocol requestProtocol) {
        return isAnyProtocol() || type.equals(requestProtocol.type);
    }

    private boolean isAnyPort() {
        return ports.size() == 1 && ports.contains(-1);
    }

    private static Set<Integer> extractPorts(String format) {
        // port[,port] or 'any'
        final String SPLITTER = ",";
        String portsPattern = "(any|(\\d{1,5}(,\\d{1,5})*)+)";
        Pattern pattern = Pattern.compile(portsPattern);
        Matcher matcher = pattern.matcher(format);
        matcher.find();
        String portsFound = matcher.group();
        Set<Integer> ports = new HashSet<>();
        if(OPEN_WORLD_LABEL.equals(portsFound))
            ports.add(ANY_PORT);
        else {
            String[] sPorts = portsFound.split(SPLITTER);
            ports = Stream.of(sPorts).map(Integer::valueOf).collect(toSet());
        }
        return ports;
    }

    private static ProtocolType extractProtocol(String format) {
        // regex patter for (TCP|UDP)
        String onlyProtocol = "(TCP|UDP|tcp|udp)";
        Pattern pattern = Pattern.compile(onlyProtocol);
        Matcher matcher = pattern.matcher(format);
        matcher.find();
        String matchedValue = matcher.group().toUpperCase();
        return ProtocolType.valueOf(matchedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Protocol protocol = (Protocol) o;

        if (type != protocol.type) return false;
        return ports.equals(protocol.ports);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + ports.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{\"Protocol\":{"
                + "\"type\":\"" + type + "\""
                + ", \"ports\":" + ports
                + "}}";
    }
}
