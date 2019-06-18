package com.hrfid.acs.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hrfid.acs.view.fragment.AddKitFragment;
import com.hrfid.acs.view.fragment.AddSubjectFragment;
import com.hrfid.acs.view.fragment.SubjectDetailsFragment;

/**
 * Created by MS on 2019-05-30.
 */
public class InventorySetupAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String frmPage;

    public InventorySetupAdapter(FragmentManager fm, int NoofTabs){
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
                    AddKitFragment addKitFragment = new AddKitFragment();
                    return addKitFragment;

                case 1:
                    SubjectDetailsFragment subjectDetailsFragment = new SubjectDetailsFragment();
                    return subjectDetailsFragment;


                default:
                    return null;
            }


        //return null;

    }

}
