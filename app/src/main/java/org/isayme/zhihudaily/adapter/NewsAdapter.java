package org.isayme.zhihudaily.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.isayme.zhihudaily.R;
import org.isayme.zhihudaily.utils.Utils;
import org.isayme.zhihudaily.utils.VolleySingleton;

/**
 * Created by isayme on 2015/5/12.
 */
public class NewsAdapter extends SimpleAdapter {
    private LayoutInflater mInflater;

    public NewsAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh = null;

        super.getView(position, convertView, parent);
        if (convertView == null) {
            view = mInflater.inflate(R.layout.listitem, null);

            vh = new ViewHolder();
            vh.niv = (NetworkImageView) view.findViewById(R.id.image);
            vh.tv = (TextView) view.findViewById(R.id.title);
            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
        ImageLoader il = VolleySingleton.getInstance().getImageLoader();
        vh.niv.setImageUrl((String) data.get("imageUrl"), il);
        vh.tv.setText((String) data.get("title"));

        return view;
    }

    class ViewHolder {
        NetworkImageView niv;
        TextView tv;
    }
}
