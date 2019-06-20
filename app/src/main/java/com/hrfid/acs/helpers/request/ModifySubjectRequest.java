package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.ModifySubjectRequestModel;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class ModifySubjectRequest extends BaseApiRequest {

    public ModifySubjectRequestModel modifySubjectRequestModel;

    public ModifySubjectRequest(Activity cmgActivity, boolean isProgressShown, ModifySubjectRequestModel modifySubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MODIFY_SUBJECT;
        super.mActivity = cmgActivity;
        super.apiName = "MODIFY_SUBJECT_API";
        this.modifySubjectRequestModel = modifySubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}