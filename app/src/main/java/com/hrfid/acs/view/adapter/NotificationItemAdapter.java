package com.hrfid.acs.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.helpers.serverResponses.GetNofication.Notification;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.DataModel;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.view.activity.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MS on 15/05/19.
 */
public class NotificationItemAdapter  extends RecyclerView.Adapter<NotificationItemAdapter.MyViewHolder> {

    private List<Notification> notifications;
    private Context notificationActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notificationDesc;
        TextView sentBy;
        TextView notification_date;
        ImageView imageViewIcon;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.notificationDesc = (TextView) itemView.findViewById(R.id.notificationDesc);
            this.sentBy = (TextView) itemView.findViewById(R.id.sentBy);
            this.notification_date = (TextView) itemView.findViewById(R.id.notification_date);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public NotificationItemAdapter(List<Notification> notifications, NotificationActivity notificationActivity) {
        this.notifications = notifications;
        this.notificationActivity = notificationActivity;
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

        TextView notificationDesc = holder.notificationDesc;
        TextView sentBy = holder.sentBy;
        TextView notification_date = holder.notification_date;
        ImageView imageView = holder.imageViewIcon;
        CardView cardView = holder.cardView;

        notificationDesc.setText(notifications.get(listPosition).getDescription());
        notification_date.setText(" / " +notifications.get(listPosition).getCreatedOn());
        if(notifications.get(listPosition).getRole().equalsIgnoreCase(Constants.LAB_STAFF)){

            sentBy.setText(Constants.LAB_STAFF);
            imageView.setImageResource(R.drawable.ic_lab_staff);

        }else if(notifications.get(listPosition).getRole().equalsIgnoreCase(Constants.TYPE_NURSE_STAFF)){

            sentBy.setText(Constants.TYPE_NURSE_STAFF);
            imageView.setImageResource(R.drawable.ic_nurse_staff);

        }else if(notifications.get(listPosition).getRole().equalsIgnoreCase(Constants.SENIOR_STAFF)){

            sentBy.setText(Constants.SENIOR_STAFF);
            imageView.setImageResource(R.drawable.ic_senior_staff);

        }


        if(new PrefManager(notificationActivity).getUserRoleType().equalsIgnoreCase(Constants.LAB_STAFF)){

            if(notifications.get(listPosition).getIsReadLabStaff().equalsIgnoreCase("1")){

                cardView.setCardBackgroundColor(Color.WHITE);

            }else {

                cardView.setCardBackgroundColor(Color.parseColor("#ffe6e6"));
            }

        }

        if(new PrefManager(notificationActivity).getUserRoleType().equalsIgnoreCase(Constants.TYPE_NURSE_STAFF)){

            if(notifications.get(listPosition).getIsReadNurse().equalsIgnoreCase("1")){

                cardView.setCardBackgroundColor(Color.WHITE);

            }else {

                cardView.setCardBackgroundColor(Color.parseColor("#ffe6e6"));
            }

        }


        if(new PrefManager(notificationActivity).getUserRoleType().equalsIgnoreCase(Constants.SENIOR_STAFF)){

            if(notifications.get(listPosition).getIsReadSeniorStaff().equalsIgnoreCase("1")){

                cardView.setCardBackgroundColor(Color.WHITE);

            }else {

                cardView.setCardBackgroundColor(Color.parseColor("#ffe6e6"));
            }

        }

        //imageView.setImageResource(dataSet.get(listPosition).getId());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
