package org.arch.framework.beans.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/**
 * @author lait.zhang@gmail.com
 * @description: ip 工具
 * @weixin PN15855012581
 * @date 12/31/2020 10:21 AM
 */
public class IpUtils {


    /**
     * 获取本地 IP
     * @return ip
     */
    @Nullable
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getMACAddress());
    }
    /**
     * 获取MAC地址, unix 默认从 "ifconfig eth0" 中获取.
     */
    @NonNull
    public static String getMACAddress() {
        // unix
        String mac = getUnixMACAddress();
        // windows
        if (isNull(mac)) {
            mac = getWindowsMACAddress();
        }
        // windows7
        if (isNull(mac)) {
            mac = getWindows7MACAddress();
        }

        if (!isNull(mac)) {
            mac = mac.replace("-", "");
        }
        else {
            mac = "ABCDEFGHIJ";
        }
        return mac.toLowerCase();
    }

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    private static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
     *
     * @return mac地址
     */
    private static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                // 找到了
                if (index >= 0) {
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("unix/linux方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return mac;
    }

    /**
     * 获取widows网卡的mac地址.
     *
     * @return mac地址
     */
    private static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            // windows下的命令，显示信息中包含有mac地址信息
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[physical
                index = line.toLowerCase().indexOf("physical address");
                // 找到了
                if (index >= 0) {
                    // 寻找":"的位置
                    index = line.indexOf(":");
                    if (index >= 0) {
                        // 取出mac地址并去除2边空格
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("widnows方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return mac;
    }

    /**
     * windows 7 专用 获取MAC地址
     *
     * @return  mac
     */
    private static String getWindows7MACAddress() {
        StringBuilder sb = new StringBuilder();
        try {
            // 获取本地IP对象
            InetAddress ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            for (byte b : mac) {
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(b & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
        }
        catch (Exception ex) {
            System.out.println("windows 7方式未获取到网卡地址");
        }
        return sb.toString();
    }

    private static boolean isNull(Object strData) {
        return strData == null || "".equals(String.valueOf(strData).trim());
    }

}
