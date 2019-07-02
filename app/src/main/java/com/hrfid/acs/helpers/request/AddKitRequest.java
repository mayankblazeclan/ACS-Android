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

public class AddKitRequest extends BaseApiRequest {

    public AddKitRequestModel addKitRequestModel;

    public AddKitRequest(Activity cmgActivity, boolean isProgressShown, AddKitRequestModel addKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.ADD_KIT;
        super.mActivity = cmgActivity;
        super.apiName = "ADD_KIT_API";
        this.addKitRequestModel = addKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}