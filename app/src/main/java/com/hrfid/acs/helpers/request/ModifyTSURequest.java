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

public class ModifyTSURequest extends BaseApiRequest {

    public ModifyTSUDetailsRequestModel modifyTSUDetailsRequestModel;

    public ModifyTSURequest(Activity cmgActivity, boolean isProgressShown, ModifyTSUDetailsRequestModel modifyTSUDetailsRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MODIFY_TSU_SETUP;
        super.mActivity = cmgActivity;
        super.apiName = "MODIFY_TSU_API";
        this.modifyTSUDetailsRequestModel = modifyTSUDetailsRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}