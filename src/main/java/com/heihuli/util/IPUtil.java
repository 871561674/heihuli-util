package com.heihuli.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 *
 * @author heihuli
 */
public class IPUtil {

    /**
     * 获取本机的ip地址，过滤了docker的网卡
     *
     * @return
     */
    public static String getLocalIpv4() {
        if (isWindowsOS()) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return getLinuxLocalIpv4();
    }

    /**
     * 是否window系统
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }


    /**
     * 获取本机的ip地址，过滤了docker的网卡
     *
     * @return
     */
    public static final String getLinuxLocalIpv4() {
        InetAddress ip;
        try {
            // 获取本机所有网卡接口
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                // 过滤docker的网卡
                if (netInterface.getName().startsWith("docker"))
                    continue;
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                // 获取该网卡下所有ip
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && !"127.0.0.1".equals(ip.getHostAddress())) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    /**
     * 获取指定网卡ip
     *
     * @param cardName 网卡
     * @return
     */
    public static String getIpv4(String cardName) {
        Enumeration<NetworkInterface> netInterfaces;
        String ip = "";
        try {
            // 获取本机所有网卡接口
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                // 获取该网卡下所有ip
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                if (ni.getName().indexOf(cardName) != -1) {
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement().getHostAddress();
                        if (ip.indexOf(".") != -1) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return ip;
    }
}
