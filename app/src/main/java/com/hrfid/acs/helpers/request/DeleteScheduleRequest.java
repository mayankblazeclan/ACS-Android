package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.serverResponses.models.DeleteScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class DeleteScheduleRequest extends BaseApiRequest {

    public DeleteScheduleRequestModel deleteScheduleRequestModel;

    public DeleteScheduleRequest(Activity cmgActivity, boolean isProgressShown, DeleteScheduleRequestModel deleteScheduleRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.DELETE_SCHEDULE;
        super.mActivity = cmgActivity;
        super.apiName = "DELETE_SCHEDULE_API";
        this.deleteScheduleRequestModel = deleteScheduleRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}