package net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * 网络帮助类
 * Created by zhouL on 2016/2/7.
 */
public class NetworkUtils {
    public static final int NETWORK_TYPE_NONE = -1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    public static final int NETWORK_TYPE_2G = 2;
    public static final int NETWORK_TYPE_3G = 3;
    public static final int NETWORK_TYPE_4G = 4;

    public static final String NETWORK_TYPESTRING_UNKNOWN = "?";
    public static final String NETWORK_TYPESTRING_NONE = "-";
    public static final String NETWORK_TYPESTRING_WIFI = "WiFi";
    public static final String NETWORK_TYPESTRING_2G = "2G";
    public static final String NETWORK_TYPESTRING_3G = "3G";
    public static final String NETWORK_TYPESTRING_4G = "4G";

    public static interface NetworkListener {
        void onNetworkStatusChanged(boolean isNetworkAvailable, NetInfo netInfo);
    }

    public static class NetInfo {
        public int type;
        public int subType;
        public String extra;
        /**
         * 运营商
         */
        public String operatorName;
    }

    private static NetInfo sCurNetworkInfo;

    private static final List<NetworkListener> sNetworkListeners = Collections.synchronizedList(new ArrayList<NetworkListener>());

    public static void addNetworkListener(NetworkListener listener) {
        sNetworkListeners.add(listener);
        listener.onNetworkStatusChanged(isNetworkAvailable(), getNetworkInfo());
    }

    public static void removeNetworkListener(NetworkListener listener) {
        sNetworkListeners.remove(listener);
    }

    /**
     * 类型转文字
     *
     * @param type
     * @return
     */
    public static String typeIntToString(int type) {
        switch (type) {
            case NETWORK_TYPE_WIFI:
                return NETWORK_TYPESTRING_WIFI;
            case NETWORK_TYPE_2G:
                return NETWORK_TYPESTRING_2G;
            case NETWORK_TYPE_3G:
                return NETWORK_TYPESTRING_3G;
            case NETWORK_TYPE_4G:
                return NETWORK_TYPESTRING_4G;
            case NETWORK_TYPE_UNKNOWN:
                return NETWORK_TYPESTRING_UNKNOWN;
            default:
                return NETWORK_TYPESTRING_UNKNOWN;
        }
    }

    public static String getNetworkTypeString() {
        int type = getNetworkType();
        return typeIntToString(type);
    }

    public static NetInfo getNetworkInfo() {
        synchronized (NetworkUtils.class) {
            if (sCurNetworkInfo == null) {
                try {
                    updateNetworkInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sCurNetworkInfo;
    }

    private static void updateNetworkInfo() {
        NetInfo netInfo = new NetInfo();
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String simOperatorName = telephonyManager.getSimOperatorName();
        netInfo.operatorName = simOperatorName;
        ConnectivityManager connManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            netInfo.type = NETWORK_TYPE_NONE;
            netInfo.subType = NETWORK_TYPE_NONE;
        } else {
            int networkType = NETWORK_TYPE_NONE;
            int type = info.getType();
            int subType = getSubType(info);
            if (type == ConnectivityManager.TYPE_WIFI) {
                networkType = NETWORK_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        networkType = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        // case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case 12:
                        // case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case 14:
                        // case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case 15:
                    case 17:
                    case 18:
                        networkType = NETWORK_TYPE_3G;
                        break;
                    // case TelephonyManager.NETWORK_TYPE_LTE:
                    case 13:
                        networkType = NETWORK_TYPE_4G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        networkType = NETWORK_TYPE_UNKNOWN;
                        break;
                }
            }
            netInfo.type = networkType;
            netInfo.subType = subType;
            netInfo.extra = info.getExtraInfo();
        }
        sCurNetworkInfo = netInfo;
    }

    /** 获取网络的subtype */
    private static int getSubType(NetworkInfo info) {
        int subType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
        try {
            subType = info.getSubtype();// 有些机器这边不能用int转型，这里捕获一下，如果不能解析就用未知代替
        }catch (Exception e){
            e.printStackTrace();
        }
        return subType;
    }

    private static void notifyNetworkListeners() {
        for (Iterator<NetworkListener> iterator = sNetworkListeners.iterator(); iterator.hasNext();) {
            NetworkListener listener = iterator.next();
            if (listener != null) {
                listener.onNetworkStatusChanged(sCurNetworkInfo.type != NETWORK_TYPE_NONE, sCurNetworkInfo);
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * 判断网络类型，2G\3G\4G\WIFI\NONE
     * @return
     */
    public static int getNetworkType() {
        return getNetworkInfo().type;
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable() {
        return getNetworkType() != NETWORK_TYPE_NONE;
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 是否是wifi
     *
     * @return
     */
    public static boolean isWifi() {
        return NetworkUtils.NETWORK_TYPE_WIFI == getNetworkType();
    }

    public static boolean is4G() {
        return NetworkUtils.NETWORK_TYPE_4G == getNetworkType();
    }

    public static int getDownLoadBuffer() {
        if (NetworkUtils.getNetworkType() == NetworkUtils.NETWORK_TYPE_WIFI) {
            return 1024 * 32;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            switch (telephonyManager.getNetworkType()) {
//	        	case TelephonyManager.NETWORK_TYPE_IDEN:   // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_1xRTT:  // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:  // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:  // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:  // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return 1024*8;  //2G网络
                case TelephonyManager.NETWORK_TYPE_EVDO_0:  // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_UMTS:  // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:   // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_HSPA:  // ~ 700-1700 kbps
                    return 1024 * 16;
                case TelephonyManager.NETWORK_TYPE_EHRPD:  // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:  // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:   // ~ 1-23 Mbps
                    return 1024 * 16;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:  // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP:   // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_LTE:  // ~ 10+ Mbps（4G网络）
                    return 1024 * 32;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return 1024*8;

                default:
                    return 1024*8;
            }
        }
    }


    /**
     * 是否使用移动网络
     *
     * @return
     */
    public static boolean isUsingMobileNetwork() {
        int networkType = getNetworkType();
        return networkType == NETWORK_TYPE_2G || networkType == NETWORK_TYPE_3G || networkType == NETWORK_TYPE_4G;
    }

    public static boolean isCMWAP() {
        String currentAPN = "";
        ConnectivityManager conManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        currentAPN = info.getExtraInfo();

        if (currentAPN == null || currentAPN == "") {
            return false;
        } else {
            if (currentAPN.toLowerCase().equals("cmwap")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    /**
     * 判断是否中国移动sim卡
     * author:caowy
     * date:2016-11-14
     *
     * @return
     */
    public static boolean isChinaMobile() {
        NetInfo networkInfo = getNetworkInfo();
        return "中国移动".equals(networkInfo.operatorName) || "CMCC".equals(networkInfo.operatorName.toUpperCase());
    }


    public static class ConnectBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                synchronized (NetworkUtils.class) {
                    try {
                        updateNetworkInfo();
                        notifyNetworkListeners();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
