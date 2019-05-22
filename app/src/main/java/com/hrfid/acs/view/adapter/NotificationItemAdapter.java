package com.hrfid.acs.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.serverResponses.GetNofication.Notification;
import com.hrfid.acs.util.DataModel;
import com.hrfid.acs.view.activity.NotificationActivity;

import java.util.ArrayList;

/**
 * Created by MS on 15/05/19.
 */
public class NotificationItemAdapter  extends RecyclerView.Adapter<NotificationItemAdapter.MyViewHolder> {

    private ArrayList<Notification> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.sent_role);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public NotificationItemAdapter(ArrayList<Notification> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_notification_list_heading, parent, false);

       // view.setOnClickListener(NotificationActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getTitle());
        textViewVersion.setText(dataSet.get(listPosition).getCreatedOn());
        //imageView.setImageResource(dataSet.get(listPosition).getId());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
