package org.isayme.zhihudaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.isayme.zhihudaily.ZhihudailyApplication;

/**
 * Created by isayme on 2015/5/10.
 */
public class VolleySingleton {
    private static class VolleySingletonHolder {
        private static final VolleySingleton mInstance = new VolleySingleton();
        private static final RequestQueue mRequestQueue = Volley.newRequestQueue(ZhihudailyApplication.getAppContext());;
        private static final ImageLoader mImageLoader = new ImageLoader(mRequestQueue,
                                       new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private VolleySingleton() {
    }

    public static VolleySingleton getInstance() {
        return VolleySingletonHolder.mInstance;
    }

    public RequestQueue getRequestQueue() {
        return VolleySingletonHolder.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return VolleySingletonHolder.mImageLoader;
    }

}
