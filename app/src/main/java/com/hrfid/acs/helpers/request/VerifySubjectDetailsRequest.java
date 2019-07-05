package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.content.Context;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class VerifySubjectDetailsRequest extends BaseApiRequest {

    public VerifySubjectRequestModel verifySubjectRequestModel;

    public VerifySubjectDetailsRequest(Context cmgActivity, boolean isProgressShown, VerifySubjectRequestModel verifySubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.VERIFY_SUBJECT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "VERIFY_SUBJECT_DETAILS_API";
        this.verifySubjectRequestModel = verifySubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}