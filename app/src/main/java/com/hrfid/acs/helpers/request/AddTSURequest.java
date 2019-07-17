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

public class AddTSURequest extends BaseApiRequest {

    public AddTSURequestModel addTSURequestModel;

    public AddTSURequest(Activity cmgActivity, boolean isProgressShown, AddTSURequestModel addTSURequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.ADD_TSU_SETUP;
        super.mActivity = cmgActivity;
        super.apiName = "ADD_TSU_API";
        this.addTSURequestModel = addTSURequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}