package com.intelliment.control;

import com.intelliment.entity.IpAddress;
import org.apache.commons.net.util.SubnetUtils;

public class SubnetUtilsAnalyzer implements AddressAnalyzer {

    public IpAddress valueOf(String cidr) {
        SubnetUtils utils = new SubnetUtils(cidr);
        String mask =  utils.getInfo().getNetmask();
        String broadcast = utils.getInfo().getBroadcastAddress();
        IpAddress.IpAddressBuilder builder = new IpAddress.IpAddressBuilder();
        builder.cidr = cidr;
        builder.mask = mask;
        builder.broadcast = broadcast;
        builder.netIdentifier = utils.getInfo().getNetworkAddress();
        builder.maxHostDir = utils.getInfo().getHighAddress();
        builder.minHostDir = utils.getInfo().getLowAddress();
        builder.totalHostsInNet = utils.getInfo().getAddressCount();
        return builder.build();
    }

    @Override
    public boolean isInRange(String in, IpAddress toCompare) {
        SubnetUtils utils = new SubnetUtils(toCompare.cidr);
        return utils.getInfo().isInRange(in);
    }
}
