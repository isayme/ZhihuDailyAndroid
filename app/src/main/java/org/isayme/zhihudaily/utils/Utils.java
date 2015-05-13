package org.isayme.zhihudaily.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.isayme.zhihudaily.R;
import org.isayme.zhihudaily.ZhihudailyApplication;

/**
 * Created by isayme on 2015/5/11.
 */
public class Utils {
    private static class UtilsHolder {
        private static final Utils mInstance = new Utils(ZhihudailyApplication.getAppContext());
    }

    private Context mCtx = null;
    private Toast toast = null;

    private Utils(Context ctx) {
        mCtx = ctx;
    }

    public static Utils getInstance() {
        return UtilsHolder.mInstance;
    }

    public void saveVerCode(int verCode) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mCtx.getString(R.string.version_code), verCode);
        editor.commit();
    }

    public int getSavedVerCode() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mCtx);
        int verCode = sharedPref.getInt(mCtx.getString(R.string.version_code), 0);
        return verCode;
    }

    public int getVerCode() {
        int verCode = -1;

        try {
            verCode = mCtx.getPackageManager().getPackageInfo("org.isayme.zhihudaily", PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // leave alone
        }

        return verCode;
    }

    public void cancelToast() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
    }
    public void toast(int resId) {
        toast(mCtx.getString(resId));
    }
    public void toast(String text) {
        cancelToast();

        toast = Toast.makeText(mCtx, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
