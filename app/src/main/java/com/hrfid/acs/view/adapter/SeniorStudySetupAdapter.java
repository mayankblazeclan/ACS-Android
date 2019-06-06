package com.hrfid.acs.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hrfid.acs.view.fragment.CreateStudyScheduleFrgement;
import com.hrfid.acs.view.fragment.ViewScreenStudyFragment;

/**
 * Created by MS on 2019-05-30.
 */
public class SeniorStudySetupAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
   // private String frmPage;

    public SeniorStudySetupAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
        //this.frmPage = fromSMS;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position){

            switch (position) {
                case 0:
                    CreateStudyScheduleFrgement createStudyScheduleFrgement = new CreateStudyScheduleFrgement();
                    return createStudyScheduleFrgement;


                case 1:
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFromScreening", true);
                    ViewScreenStudyFragment viewScreenStudyFragment = new ViewScreenStudyFragment();
                    viewScreenStudyFragment.setArguments(bundle);
                    return viewScreenStudyFragment;

                case 2:
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("isFromScreening", false);
                    ViewScreenStudyFragment viewScreenStudyFragment1 = new ViewScreenStudyFragment();
                    viewScreenStudyFragment1.setArguments(bundle1);
                    return viewScreenStudyFragment1;

                default:
                    return null;
            }

    }

}
