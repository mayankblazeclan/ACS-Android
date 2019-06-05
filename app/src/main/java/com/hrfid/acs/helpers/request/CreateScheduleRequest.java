package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class CreateScheduleRequest extends BaseApiRequest {

    public CreateScheduleModel createScheduleModel;

    public CreateScheduleRequest(Activity cmgActivity, boolean isProgressShown, CreateScheduleModel createScheduleModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.CREATE_SCHEDULE;
        super.mActivity = cmgActivity;
        super.apiName = "CREATE_SCHEDULE_API";
        this.createScheduleModel = createScheduleModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}