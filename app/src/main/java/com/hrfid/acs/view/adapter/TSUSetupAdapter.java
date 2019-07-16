package com.hrfid.acs.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hrfid.acs.view.fragment.AddKitFragment;
import com.hrfid.acs.view.fragment.AddTSUFragment;
import com.hrfid.acs.view.fragment.KitDetailsFragment;
import com.hrfid.acs.view.fragment.TSUDetailsFragment;

/**
 * Created by MS on 2019-05-30.
 */
public class TSUSetupAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String frmPage;

    public TSUSetupAdapter(FragmentManager fm, int NoofTabs){
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
                    AddTSUFragment addTSUFragment = new AddTSUFragment();
                    return addTSUFragment;

                case 1:
                    TSUDetailsFragment tsuDetailsFragment = new TSUDetailsFragment();
                    return tsuDetailsFragment;


                default:
                    return null;
            }


        //return null;

    }

}
