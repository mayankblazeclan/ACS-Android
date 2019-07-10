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

public class SearchKitRequest extends BaseApiRequest {

    public SearchKitRequestModel searchKitRequestModel;

    public SearchKitRequest(Activity cmgActivity, boolean isProgressShown, SearchKitRequestModel searchKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.SEARCH_KIT;
        super.mActivity = cmgActivity;
        super.apiName = "SEARCH_KIT_API";
        this.searchKitRequestModel = searchKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}