package com.effigrity.slidermenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.effigrity.Utilities.CommonData;
import com.effigrity.ayurvedichomemedicine.free.R;
import com.effigrity.slidermenu.model.NavDrawerItem;
import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public int getCount() {
        return this.navDrawerItems.size();
    }

    public Object getItem(int position) {
        return this.navDrawerItems.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.drawer_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setTypeface(CommonData.typeFace);
        txtTitle.setText(((NavDrawerItem) this.navDrawerItems.get(position)).getTitle());
        return convertView;
    }
}
