package com.heihuli.test;

import com.heihuli.util.IPUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class IPUtilTest {

    @Test
    public void test() {
        System.out.println(IPUtil.getLocalIpv4());
        System.out.println(IPUtil.getIpv4("utun5"));
        System.out.println(IPUtil.getLinuxLocalIpv4());

    }
}
