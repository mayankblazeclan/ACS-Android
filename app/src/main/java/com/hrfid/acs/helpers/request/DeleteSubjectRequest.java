package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.serverResponses.models.DeleteScheduleRequestModel;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class DeleteSubjectRequest extends BaseApiRequest {

    public DeleteScheduleRequestModel deleteScheduleRequestModel;

    public DeleteSubjectRequest(Activity cmgActivity, boolean isProgressShown, DeleteScheduleRequestModel deleteScheduleRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.DELETE_SUBJECT;
        super.mActivity = cmgActivity;
        super.apiName = "DELETE_SUBJECT_API";
        this.deleteScheduleRequestModel = deleteScheduleRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}