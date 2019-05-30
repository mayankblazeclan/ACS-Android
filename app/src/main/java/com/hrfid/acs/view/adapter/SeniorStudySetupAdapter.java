package com.hrfid.acs.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hrfid.acs.view.fragment.CreateStudyScheduleFrgement;

/**
 * Created by MS on 2019-05-30.
 */
public class SeniorStudySetupAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String frmPage;

    public SeniorStudySetupAdapter(FragmentManager fm, int NoofTabs, String fromSMS){
        super(fm);
        this.mNumOfTabs = NoofTabs;
        this.frmPage = fromSMS;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        if(frmPage.equalsIgnoreCase("FromSMS")){

            switch (position) {
                case 0:
                    CreateStudyScheduleFrgement createStudyScheduleFrgement = new CreateStudyScheduleFrgement();
                    return createStudyScheduleFrgement;

                case 1:
                    CreateStudyScheduleFrgement createStudyScheduleFrgement1 = new CreateStudyScheduleFrgement();
                    return createStudyScheduleFrgement1;


                case 2:
                    CreateStudyScheduleFrgement createStudyScheduleFrgement2 = new CreateStudyScheduleFrgement();
                    return createStudyScheduleFrgement2;

                default:
                    return null;
            }

        }
        return null;

    }

}
