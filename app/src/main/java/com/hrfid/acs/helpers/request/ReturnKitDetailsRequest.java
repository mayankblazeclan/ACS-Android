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

public class ReturnKitDetailsRequest extends BaseApiRequest {

    public ReturnKitRequestModel returnKitRequestModel;

    public ReturnKitDetailsRequest(Context cmgActivity, boolean isProgressShown, ReturnKitRequestModel returnKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.RETURN_KIT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "RETURN_KIT_DETAILS_API";
        this.returnKitRequestModel = returnKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}