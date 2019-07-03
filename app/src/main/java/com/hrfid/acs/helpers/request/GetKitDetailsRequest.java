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

public class GetKitDetailsRequest extends BaseApiRequest {

    public CommonRequestModel commonRequestModel;

    public GetKitDetailsRequest(Activity cmgActivity, boolean isProgressShown, CommonRequestModel commonRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.GET_KIT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "GET_KIT_DETAILS_API";
        this.commonRequestModel = commonRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}