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

public class SearchSubjectRequest extends BaseApiRequest {

    public SearchSubjectRequestModel searchSubjectRequestModel;

    public SearchSubjectRequest(Activity cmgActivity, boolean isProgressShown, SearchSubjectRequestModel searchSubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.SEARCH_SUBJECT_ONBOARDING;
        super.mActivity = cmgActivity;
        super.apiName = "SEARCH_SUBJECT_ONBOARDING_API";
        this.searchSubjectRequestModel = searchSubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}