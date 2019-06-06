package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class ModifyScheduleRequest extends BaseApiRequest {

    public ModifyScheduleRequestModel createScheduleModel;

    public ModifyScheduleRequest(Activity cmgActivity, boolean isProgressShown, ModifyScheduleRequestModel createScheduleModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MODIFY_SCHEDULE;
        super.mActivity = cmgActivity;
        super.apiName = "MODIFY_SCHEDULE_API";
        this.createScheduleModel = createScheduleModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}