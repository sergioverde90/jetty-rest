package com.intelliment.control;

import com.intelliment.entity.Constants;
import com.intelliment.entity.IpAddress;
import org.apache.commons.net.util.SubnetUtils;

public class SubnetUtilsAnalyzer implements AddressAnalyzer {

    @Override
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
        String cidrToCompare = toCompare.cidr;
        if(isAnyAllowed(cidrToCompare)) return true;
        SubnetUtils utils = new SubnetUtils(cidrToCompare);
        if(isSpecificIp(utils)) return checkSpecificIpsAreEquals(in, cidrToCompare);
        return utils.getInfo().isInRange(in);
    }

    private boolean isAnyAllowed(String cidrToCompare) {
        return cidrToCompare.equals(Constants.OPEN_WORLD_ADDRESS);
    }

    private static boolean checkSpecificIpsAreEquals(String in, String toCompare) {
        return in.concat(Constants.DEFAULT_NETMASK).equals(toCompare);
    }

    private static boolean isSpecificIp(SubnetUtils utils) {
        return utils.getInfo().getAddressCount() == 0;
    }
}
