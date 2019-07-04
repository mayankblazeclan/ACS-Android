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

public class ModifyKitRequest extends BaseApiRequest {

    public ModifyKitRequestModel modifyKitRequestModel;

    public ModifyKitRequest(Activity cmgActivity, boolean isProgressShown, ModifyKitRequestModel modifyKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MODIFY_KIT;
        super.mActivity = cmgActivity;
        super.apiName = "MODIFY_KIT_API";
        this.modifyKitRequestModel = modifyKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}