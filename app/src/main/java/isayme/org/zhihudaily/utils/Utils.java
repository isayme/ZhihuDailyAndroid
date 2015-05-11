package isayme.org.zhihudaily.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import isayme.org.zhihudaily.R;
import isayme.org.zhihudaily.ZhihudailyApplication;

/**
 * Created by isayme on 2015/5/11.
 */
public class Utils {
    private static class UtilsHolder {
        private static final Utils mInstance = new Utils(ZhihudailyApplication.getAppContext());
    }

    private Context mCtx = null;

    private Utils(Context ctx) {
        mCtx = ctx;
    }

    public static Utils getInstance() {
        return UtilsHolder.mInstance;
    }

    public int getSavedVerCode() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mCtx);
        int verCode = sharedPref.getInt(mCtx.getString(R.string.versionCode), 0);
        return verCode;
    }

    public int getVerCode() {
        int verCode = -1;
        try {
            verCode = mCtx.getPackageManager().getPackageInfo("isayme.org.zhihudaily", PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return verCode;
    }
}
