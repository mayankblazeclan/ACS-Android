package com.hrfid.acs.view.registration.server;

import com.hrfid.acs.view.BasePresenter;
import com.hrfid.acs.view.BaseView;

public interface ServerTasks {

    interface View extends BaseView<Presenter> {
        void showDialog(String activity);
        void next();
    }

    interface Presenter extends BasePresenter {
        void checkServer(long server, boolean isNext);
    }
}
