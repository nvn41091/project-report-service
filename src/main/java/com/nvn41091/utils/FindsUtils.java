package com.nvn41091.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindsUtils {

    public static String findMacAddress() throws Exception {
        String macAddress = null;
        String command = "ifconfig";

        String osName = System.getProperty("os.name");
        System.out.println("Operating System is " + osName);

        if (osName.startsWith("Windows")) {
            command = "ipconfig /all";
        } else if (osName.startsWith("Linux") || osName.startsWith("Mac") || osName.startsWith("HP-UX")
                || osName.startsWith("NeXTStep") || osName.startsWith("Solaris") || osName.startsWith("SunOS")
                || osName.startsWith("FreeBSD") || osName.startsWith("NetBSD")) {
            command = "ifconfig -a";
        } else if (osName.startsWith("OpenBSD")) {
            command = "netstat -in";
        } else if (osName.startsWith("IRIX") || osName.startsWith("AIX") || osName.startsWith("Tru64")) {
            command = "netstat -ia";
        } else if (osName.startsWith("Caldera") || osName.startsWith("UnixWare") || osName.startsWith("OpenUNIX")) {
            command = "ndstat";
        } else {// Note: Unsupported system.
            throw new Exception("The current operating system '" + osName + "' is not supported.");
        }

        Process pid = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
        Pattern p = Pattern.compile("([\\w]{1,2}(-|:)){5}[\\w]{1,2}");
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            Matcher m = p.matcher(line);
            if (m.find()) {
                macAddress = m.group();
                break;
            }
        }
        in.close();
        return macAddress;
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
