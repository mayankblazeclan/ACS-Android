package com.hrfid.acs.view.user_authentication;

import android.support.annotation.NonNull;

public class UserAuthPresenter implements UserAuthTask.Presenter {

    @NonNull
    private final UserAuthTask.View mView;

    public UserAuthPresenter(@NonNull UserAuthTask.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void verifyUser() {
        mView.nextActivity(1);
    }
}
