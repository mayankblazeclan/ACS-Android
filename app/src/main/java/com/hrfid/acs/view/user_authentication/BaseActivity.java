/*
 * The base class for the product process. Add the common properties and method
 * for all the process.  
 * 
 * @author      Armee Cabayao
 * @version     0.1
 * @since       2015-02-12
 */
package com.hrfid.acs.view.user_authentication;

import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hrfid.acs.operations.NFCForegroundOps;

//import au.com.healthrfid.pathologyapp.pref.SharedPrefsManager;

public class BaseActivity extends AppCompatActivity {

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
	 * This method will set the toast message
	 * @param msg
     */
	public void showToastMessage(String msg){
		//set the toast message to the center of the screen
		Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(GRAVITY, 0, 0);
		toast.show();
	}

	/**
	 * Setup the NFC on the activity
	 */
	protected void setupNfc(){
		// initialize NFC and check if device support NFC
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		try {

			if (!mNfcAdapter.isEnabled() || mNfcAdapter == null) {
				Log.v(TAG, "This device doesn't support NFC");
			} else {
				//"Ready to read . . ."
				Log.v(TAG, "NFC is ready to read");
				mNfcForegroundOps = new NFCForegroundOps(this);
			}
		}
		catch (Exception e){
			Log.v(TAG, "This device doesn't support NFC");
		}

	}

//	/**
//	 * Move intent to Device Resgiter
//	 * @param activity
//     */
//	protected void gotoDeviceRegister(Activity activity){
//		Intent gotoDevicegister = new Intent(activity, DeviceConfigurationActivity.class);
//		gotoDevicegister.putExtra("activityName", "main");
//		startActivity(gotoDevicegister);
//		finish();
//	}
//
//	/**
//	 * Clear the user tag id used for Single Authentication
//	 */
//	protected void resetUserAuthentication () {
//		SharedPrefsManager pref = new SharedPrefsManager();
//		pref.setUserAuthentication(getApplicationContext(), false);
//	}
//
//	/**
//	 * Reset User Idle Time
//	 */
//	protected void resetUserIdleTime () {
//		long currTime = System.currentTimeMillis();
//
//		SharedPrefsManager pref = new SharedPrefsManager();
//		pref.setUserIdleTime(getApplicationContext(), currTime);
//	}
//
//	protected long getUserIdleTime () {
//		SharedPrefsManager pref = new SharedPrefsManager();
//
//		return pref.getUserIdleTime(getApplicationContext());
//	}

}
