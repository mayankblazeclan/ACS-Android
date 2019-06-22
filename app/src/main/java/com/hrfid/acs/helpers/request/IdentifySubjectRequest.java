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

public class IdentifySubjectRequest extends BaseApiRequest {

    public IdentifySubjectRequestModel searchSubjectRequestModel;

    public IdentifySubjectRequest(Activity cmgActivity, boolean isProgressShown, IdentifySubjectRequestModel searchSubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.IDENTIFY_SUBJECT_ONBOARDING;
        super.mActivity = cmgActivity;
        super.apiName = "IDENTIFY_SUBJECT_ONBOARDING_API";
        this.searchSubjectRequestModel = searchSubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}