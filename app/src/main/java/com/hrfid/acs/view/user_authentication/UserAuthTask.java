package com.hrfid.acs.view.user_authentication;

import com.hrfid.acs.view.BasePresenter;
import com.hrfid.acs.view.BaseView;

public interface UserAuthTask {
    interface View extends BaseView<Presenter> {
        void nextActivity(int activity);
    }

    interface Presenter extends BasePresenter {
        void verifyUser();
    }
}
