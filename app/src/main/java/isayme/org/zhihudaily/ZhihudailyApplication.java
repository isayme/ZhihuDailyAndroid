package isayme.org.zhihudaily;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by isayme on 2015/5/10.
 */
public class ZhihudailyApplication extends Application {
    private static Context mCtx = null;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mCtx = getApplicationContext();
    }

    public static Context getAppContext() {
        return mCtx;
    }
}
