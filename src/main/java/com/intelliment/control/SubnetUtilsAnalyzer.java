package com.intelliment.control;

import com.intelliment.entity.Constants;
import com.intelliment.entity.IpAddress;
import org.apache.commons.net.util.SubnetUtils;

import static com.intelliment.entity.Constants.addDefaultMask;

public class SubnetUtilsAnalyzer implements AddressAnalyzer {

    @Override
    public IpAddress valueOf(String cidr) {
        SubnetUtils utils = getSubnetUtilsInstance(cidr);
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
        SubnetUtils utils = getSubnetUtilsInstance(cidrToCompare);
        if(isConcreteIp(utils)) return concreteIpsAreEquals(in, cidrToCompare);
        return utils.getInfo().isInRange(in);
    }

    private static SubnetUtils getSubnetUtilsInstance(String cidr) {
        return new SubnetUtils(cidr);
    }

    private static boolean concreteIpsAreEquals(String in, String toCompare) {
        return addDefaultMask(in).equals(toCompare);
    }

    private static boolean isConcreteIp(SubnetUtils utils) {
        return utils.getInfo().getAddressCount() == 0;
    }

    private static boolean isAnyAllowed(String cidrToCompare) {
        return cidrToCompare.equals(Constants.OPEN_WORLD_ADDRESS);
    }
}
