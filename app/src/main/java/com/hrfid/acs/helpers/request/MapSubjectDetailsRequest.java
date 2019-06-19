package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 08/08/17.
 */

import android.app.Activity;
import android.content.Context;

import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.util.AppConstants;


/**
 * Created by MS on 20/11/18.
 */

public class MapSubjectDetailsRequest extends BaseApiRequest {

    public MapSubjectRequestModel mapSubjectRequestModel;

    public MapSubjectDetailsRequest(Context cmgActivity, boolean isProgressShown, MapSubjectRequestModel mapSubjectRequestModel) {
        super.showProgressDialog = isProgressShown;
        super.apiToCall = NetworkingHelper.MAP_SUBJECT_DETAILS;
        super.mActivity = cmgActivity;
        super.apiName = "MAP_SUBJECT_DETAILS_API";
        this.mapSubjectRequestModel = mapSubjectRequestModel;
        AppConstants.API_TO_HIT = AppConstants.API_KENTICO;
    }
}