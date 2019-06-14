package com.hrfid.acs.view.registration.server;

import android.support.annotation.NonNull;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.data.SharedPreferenceManager;
import com.hrfid.acs.service.api.healthcheck.GetHealthCheck;
import com.hrfid.acs.service.model.HealthCheckResponse;

public class ServerPresenter implements ServerTasks.Presenter, GetHealthCheck.HealthcheckInterface {

    @NonNull
    private final ServerTasks.View mView;

    private SharedPreferenceManager mPrefs;

    private final GetHealthCheck getHealthCheckAPI;

    private boolean mIsNext;

    public ServerPresenter(@NonNull ServerTasks.View view,
                           SharedPreferenceManager sharedPreferenceManager) {
        mView = view;
        mView.setPresenter(this);
        mPrefs = sharedPreferenceManager;
        getHealthCheckAPI = new GetHealthCheck(this);
    }

    @Override
    public void subscribe() {
        // Open View
    }

    @Override
    public void unsubscribe() {
        // Close View
    }

    @Override
    public void checkServer(long server, boolean isNext) {
        switch ((int) server) {
            case Constants.DEV_CONTROLPOINT_ID: {
                checkServerAPI(Constants.DEV_CONTROLPOINT, isNext);
                break;
            }
            case Constants.SIT_CONTROLPOINT_ID: {
                checkServerAPI(Constants.SIT_CONTROLPOINT + "." + Constants.CONTROLPOINT, isNext);
                break;
            }
            case Constants.TEST_CONTROLPOINT_ID: {
                checkServerAPI(Constants.TEST_CONTROLPOINT + "." + Constants.CONTROLPOINT, isNext);
                break;
            }
            case Constants.UAT_CONTROLPOINT_ID: {
                checkServerAPI(Constants.UAT_CONTROLPOINT + "." + Constants.CONTROLPOINT, isNext);
                break;
            }
            case Constants.CONTROLPOINT_ID: {
                checkServerAPI(Constants.CONTROLPOINT, isNext);
                break;
            }
        }
    }

    private void checkServerAPI(String url, boolean isNext) {
        this.mIsNext = isNext;
        getHealthCheckAPI.callAPI(url);
    }

    @Override
    public void healthcheckError(int errorCode) {
        mView.showDialog(Constants.DLG_SERVER_UNAVAILABLE);
    }


    @Override
    public void healthcheckResponse(HealthCheckResponse healthCheckResponse) {
        if (healthCheckResponse.getDb() == 1) {
            mView.next();
        }
    }
}
