package com.hrfid.acs.view.splashscreen;


import android.support.annotation.NonNull;
import com.hrfid.acs.view.BasePresenter;
import com.hrfid.acs.view.BaseView;

public interface SplashScreenTasks {
    interface View extends BaseView<Presenter> {
        void showDialog(String activity, int task);

        void nextActivity(int activity);
    }

    interface Presenter extends BasePresenter {
        void loadProcess(int task);
    }
}
