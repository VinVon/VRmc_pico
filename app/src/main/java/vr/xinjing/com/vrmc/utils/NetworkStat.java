package vr.xinjing.com.vrmc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by raytine on 2017/2/14.
 */

public class NetworkStat {
    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;
    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;
    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;
    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;
    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    public static int mNetWorkType = NETWORKTYPE_INVALID;
    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context
     *            上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G}, *
     *         {@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}*
     *         <p>
     *         {@link #NETWORKTYPE_WIFI}
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type ==  ConnectivityManager.TYPE_MOBILE) {
                int subtype = networkInfo.getSubtype();
                if(subtype == TelephonyManager.NETWORK_TYPE_GPRS)
                    mNetWorkType = NETWORKTYPE_WAP;
                else if(subtype ==  TelephonyManager.NETWORK_TYPE_EDGE
                        || subtype == TelephonyManager.NETWORK_TYPE_CDMA)
                    mNetWorkType = NETWORKTYPE_2G;
                else
                    mNetWorkType = NETWORKTYPE_3G;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }
}
