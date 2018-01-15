package com.gopher.medicalwaste;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/10/13.
 */

public class NetHelper {
    /**
     * 是否联网网络 使用前提是需要加上权限: <uses-permission
     * android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static String TAG = NetHelper.class.getSimpleName();

    public static boolean IsHaveInternet(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            Log.e("info;", info + "..");
            if (info != null) {
                Log.e("info;", info.isConnected() + "..");
            }
            // return (info != null && info.isConnected());
            return (info != null);
        } catch (Exception e) {
            return false;
        }
    }

    public static int netType(Context context) {
        int type = 0;
        if (IsHaveInternet(context)) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == 1) {// 1是wifi
                    type = 1;
                } else if (info.getType() == 0) {// 2是gprs
                    type = 2;
                }
            }
        } else {
            Log.e(TAG, "网络未连接");
        }
        return type;
    }

    public static String getLocalIp(Context cotext) {
        int netType = netType(cotext);
        if (netType == 1) {
            return getWifiIp(cotext);
        } else {
            return getGprsIp();
        }
    }


    public static String getWifiIp(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    public static String getGprsIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (true) {
                            if (isIPv4)
                                return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
//                        return hostAddress.toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, "WifiPreferenceIpAddress = " + ex.toString());
        }
        return null;
    }

    private static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }
}
