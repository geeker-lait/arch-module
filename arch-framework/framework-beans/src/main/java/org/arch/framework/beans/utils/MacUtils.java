package org.arch.framework.beans.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MacUtils {
    public MacUtils() {
    }

    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("hwaddr");
                if (index != -1) {
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("硬件地址");
                if (index != -1) {
                    mac = line.substring(index + 4).trim();
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac == null ? getUnixMACAddress() : mac;
    }

    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index;
                if (line.split("-").length == 6) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }

                index = line.toLowerCase().indexOf("物理地址");
                if (index != -1) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static String getMac() {
        String os = getOSName();
        String mac;
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else if (os.startsWith("linux")) {
            mac = getLinuxMACAddress();
        } else {
            mac = getUnixMACAddress();
        }

        return mac == null ? "" : mac;
    }

    public static void main(String[] argc) {
        String os = getOSName();
        System.out.println("os: " + os);
        String mac;
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
            System.out.println("mac: " + mac);
        } else if (os.startsWith("linux")) {
            mac = getLinuxMACAddress();
            System.out.println("mac: " + mac);
        } else {
            mac = getUnixMACAddress();
            System.out.println("mac: " + mac);
        }

    }
}
