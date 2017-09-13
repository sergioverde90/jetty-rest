package com.sergio.resources;

import com.sergio.resources.control.AclService;
import com.sergio.resources.control.AclServiceImpl;
import com.sergio.resources.control.InMemoryAclLoader;
import com.sergio.resources.control.SimpleRequestMapper;
import com.sergio.resources.entity.IpAddress;
import com.sergio.resources.entity.Request;
import org.apache.commons.net.util.SubnetUtils;

public class Main {

    public interface AddressAnalyzer {
        IpAddress valueOf(String cidr);
        boolean isInRange(String in, IpAddress toCompare);
    }

    public static class SubnetUtilsAnalyzer implements AddressAnalyzer {

        private static final String SLASH = "/";

        public IpAddress valueOf(String cidr) {
            SubnetUtils utils = new SubnetUtils(cidr);
            String mask =  utils.getInfo().getNetmask();
            String broadcast = utils.getInfo().getBroadcastAddress();
            IpAddress ipAddress = new IpAddress();
            ipAddress.cidr = cidr;
            ipAddress.mask = mask;
            ipAddress.broadcast = broadcast;
            ipAddress.netIdentifier = utils.getInfo().getNetworkAddress();
            ipAddress.maxHostDir = utils.getInfo().getHighAddress();
            ipAddress.minHostDir = utils.getInfo().getLowAddress();
            ipAddress.totalHostsInNet = utils.getInfo().getAddressCount();
            return ipAddress;
        }

        @Override
        public boolean isInRange(String in, IpAddress toCompare) {
            SubnetUtils utils = new SubnetUtils(toCompare.cidr);
            return utils.getInfo().isInRange(in);
        }
    }

    public static void main(String[] args) {
        // entry point
        Request request = new Request("192.168.0.5", "192.168.0.1", "udp/80");
        AclService service = new AclServiceImpl(new InMemoryAclLoader(new SimpleRequestMapper()));
        System.out.println(service.isAllowed(request));
    }

}
