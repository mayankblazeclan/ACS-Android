package com.hrfid.acs.view.adapter;

/**
 * Created by MS on 09/05/19.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.model.StaffItem;

import java.util.ArrayList;

public class StaffItemAdapter extends ArrayAdapter {

    ArrayList<StaffItem> staffItems = new ArrayList<StaffItem>();

    public StaffItemAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        staffItems = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_staff_grid_items, null);
        TextView textView = (TextView) v.findViewById(R.id.item_name);
        ImageView imageView = (ImageView) v.findViewById(R.id.item_image);
        textView.setText(staffItems.get(position).getTagName());
        imageView.setImageResource(staffItems.get(position).getTagImage());
        return v;

    }

}
