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

public class MapKitDetailsRequest extends BaseApiRequest {

    public MapKitRequestModel mapKitRequestModel;

    public MapKitDetailsRequest(Context cmgActivity, boolean isProgressShown, MapKitRequestModel mapKitRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MAP_KIT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "MAP_KIT_DETAILS_API";
        this.mapKitRequestModel = mapKitRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}