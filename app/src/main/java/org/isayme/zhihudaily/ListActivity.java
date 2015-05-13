package org.isayme.zhihudaily;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.isayme.zhihudaily.adapter.NewsAdapter;
import org.isayme.zhihudaily.utils.NetworkUtil;
import org.isayme.zhihudaily.utils.Utils;
import org.isayme.zhihudaily.utils.VolleySingleton;


public class ListActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private long mExitTime;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private NewsAdapter mAdapter;
    private ArrayList<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mListView = (ListView) findViewById(R.id.listView);

        String[] fromColumns = {"title", "image"};
        int[] toViews = {R.id.title, R.id.image};

        mAdapter = new NewsAdapter(this, mDataList,
                R.layout.listitem, fromColumns, toViews);
        mListView.setAdapter(mAdapter);

        fetchNews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long cur = System.currentTimeMillis();

        if (cur - mExitTime > 2000) {
            mExitTime = cur;
            Utils.getInstance().toast(R.string.press_again_to_exit);
        } else {
            Utils.getInstance().cancelToast();
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        fetchNews();
    }

    private void fetchNews() {
        if (NetworkUtil.TYPE_NONE == NetworkUtil.getInstance().getNetworkStatus()) {
            Utils.getInstance().toast(R.string.network_not_valid);
            return;
        }

        mSwipeLayout.setRefreshing(true);

        String url = "http://news-at.zhihu.com/api/4/news/latest";
        JsonObjectRequest jre = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray stories = response.getJSONArray("stories");

                    mDataList.clear();

                    for (int i = 0; i < stories.length(); i++) {
                        JSONObject item = stories.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("title", item.getString("title"));
                        map.put("imageUrl", item.getJSONArray("images").getString(0));
                        mDataList.add(map);
                    }

                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("TAG", e.toString());
                }
                mSwipeLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.getInstance().toast(R.string.refresh_fail);
                mSwipeLayout.setRefreshing(false);
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(jre);
    }
}
