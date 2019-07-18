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

public class GetTSUParamRequest extends BaseApiRequest {

    public String studyId;

    public GetTSUParamRequest(Activity cmgActivity, boolean isProgressShown, String studyId) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.GET_TSU_PARAMS;
        super.mActivity = cmgActivity;
        super.apiName = "GET_TSU_PARAM_API";
        this.studyId = studyId;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}