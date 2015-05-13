package org.isayme.zhihudaily.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.isayme.zhihudaily.ZhihudailyApplication;

/**
 * Created by isayme on 2015/5/11.
 */
public class NetworkUtil {
    private static class NetworkUtilHolder {
        private static final NetworkUtil mInstance = new NetworkUtil(ZhihudailyApplication.getAppContext());
    }

    public final static int TYPE_NONE = 0;
    public final static int TYPE_MOBILE = 1;
    public final static int TYPE_WIFI = 2;

    private Context mCtx = null;

    private NetworkUtil(Context ctx) {
        mCtx = ctx;
    }

    public int getNetworkStatus() {
        ConnectivityManager manager = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (null != info) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
        }
        return TYPE_NONE;
    }

    public static NetworkUtil getInstance() {
        return NetworkUtilHolder.mInstance;
    }
}
