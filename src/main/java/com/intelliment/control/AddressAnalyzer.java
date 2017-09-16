package com.intelliment.control;

import com.intelliment.entity.IpAddress;

public interface AddressAnalyzer {
    IpAddress valueOf(String cidr);
    boolean isInRange(String in, IpAddress toCompare);
}
