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

public class AddSubjectRequest extends BaseApiRequest {

    public AddSubjectRequestModel addSubjectRequestModel;

    public AddSubjectRequest(Activity cmgActivity, boolean isProgressShown, AddSubjectRequestModel addSubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.ADD_SUBJECT_ONBOARDING;
        super.mActivity = cmgActivity;
        super.apiName = "ADD_SUBJECT_ONBOARDING_API";
        this.addSubjectRequestModel = addSubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}