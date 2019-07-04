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

public class DismissKitDetailsRequest extends BaseApiRequest {

    public DismissKitRequestModel dismissKitRequestModel;

    public DismissKitDetailsRequest(Context cmgActivity, boolean isProgressShown, DismissKitRequestModel dismissKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.DISMISS_KIT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "DISMISS_KIT_DETAILS_API";
        this.dismissKitRequestModel = dismissKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}