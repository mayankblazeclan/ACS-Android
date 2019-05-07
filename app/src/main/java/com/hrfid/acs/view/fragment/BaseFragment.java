package com.hrfid.acs.view.fragment;

import android.nfc.NfcAdapter;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.hrfid.acs.operations.NFCForegroundOps;
import com.hrfid.acs.pref.SharedPrefsManager;

public class BaseFragment extends DialogFragment {

    /* debugging tag used by the Android logger */
    /**
     * Final integer variables
     */
    protected static int GRAVITY = 0;
    /**
     * Final String variables
     */
    protected final String TAG = getClass().getSimpleName();
    /**
     * Class Variables
     */
    protected NfcAdapter mNfcAdapter;
    protected NFCForegroundOps mNfcForegroundOps = null;


    /**
     * Setup the NFC on the activity
     */
    protected void setupNfc(){
        // initialize NFC and check if device support NFC
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        try {

            if (!mNfcAdapter.isEnabled() || mNfcAdapter == null) {
                Log.v(TAG, "This device doesn't support NFC");
            } else {
                //"Ready to read . . ."
                Log.v(TAG, "NFC is ready to read");
                mNfcForegroundOps = new NFCForegroundOps(getActivity());
            }
        }
        catch (Exception e){
            Log.v(TAG, "This device doesn't support NFC");
        }

    }


    /**
     * Clear the user tag id used for Single Authentication
     */
    protected void resetUserAuthentication () {
        SharedPrefsManager pref = new SharedPrefsManager();
        pref.setUserAuthentication(getContext(), false);
    }

    /**
     * Reset User Idle Time
     */
    protected void resetUserIdleTime () {
        long currTime = System.currentTimeMillis();

        SharedPrefsManager pref = new SharedPrefsManager();
        pref.setUserIdleTime(getContext(), currTime);
    }

    protected long getUserIdleTime () {
        SharedPrefsManager pref = new SharedPrefsManager();

        return pref.getUserIdleTime(getContext());
    }




}
