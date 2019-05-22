
package com.hrfid.acs.helpers.serverResponses.GetNofication;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNotificationResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;
    @SerializedName("totalUnread")
    @Expose
    private int totalUnread;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public int getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(int totalUnread) {
        this.totalUnread = totalUnread;
    }

}
