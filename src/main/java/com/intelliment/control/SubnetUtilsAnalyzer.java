package com.intelliment.control;

import com.intelliment.entity.IpAddress;
import org.apache.commons.net.util.SubnetUtils;

public class SubnetUtilsAnalyzer implements AddressAnalyzer {

    public IpAddress valueOf(String cidr) {
        SubnetUtils utils = new SubnetUtils(cidr);
        String mask =  utils.getInfo().getNetmask();
        String broadcast = utils.getInfo().getBroadcastAddress();
        return new IpAddress.IpAddressBuilder()
                .setCidr(cidr)
                .setMask(mask)
                .setBroadcast(broadcast)
                .setNetIdentifier(utils.getInfo().getNetworkAddress())
                .setMaxHostDir(utils.getInfo().getHighAddress())
                .setMinHostDir(utils.getInfo().getLowAddress())
                .setTotalHostsInNet(utils.getInfo().getAddressCount())
                .build();
    }

    @Override
    public boolean isInRange(String in, IpAddress toCompare) {
        SubnetUtils utils = new SubnetUtils(toCompare.cidr);
        return utils.getInfo().isInRange(in);
    }
}
