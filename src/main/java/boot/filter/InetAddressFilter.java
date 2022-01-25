package boot.filter;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressFilter {
    public static String testQuery(String host) throws IOException {
        if (null == host) {
            return null;
        }

        if (host.endsWith("jetbrains.com")) {
            System.out.println("Reject dns query: " + host);
            throw new java.net.UnknownHostException();
        }

        return host;
    }

    public static Object testReachable(InetAddress n) {
        if (null == n) {
            return null;
        }

        if (n.getHostName().equals("jetbrains.com")) {
            System.out.println("Reject dns reachable test: " + n.getHostName());
            return false;
        }

        return null;
    }
}