package com.hrfid.acs.view.user_authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hrfid.acs.MainActivity;
import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.operations.BarcodeOps;
import com.hrfid.acs.operations.BarcodeResusltOps;
import com.hrfid.acs.operations.HFReaderOps;
import com.hrfid.acs.operations.NFCForegroundOps;
import com.hrfid.acs.service.api.user.ProcessLog;
import com.hrfid.acs.service.api.user.TagIdItem;
import com.hrfid.acs.service.model.VerifyTagId;
import com.hrfid.acs.util.FragmentUtils;
import com.hrfid.acs.util.SoundUtils;
import com.hrfid.acs.util.Utils;
import com.zebra.adc.decoder.Barcode2DWithSoft;


//import com.hrfid.acs.App;
//import retrofit2.RetrofitError;


public class UserAuthActivity extends AppCompatActivity implements UserAuthFragment.OnFragmentInteractionListener, Barcode2DWithSoft.ScanCallback ,BCUserLoginFragment.OnBCUserLoginInterface, BarcodeResusltOps ,PostUserValidationCheck.UserAuthInterface{


    private Fragment mFragment;
    private boolean mIsScanReceive = false;
    private boolean isBeepSoundOn;
    private SoundUtils mSoundUtils;
    protected NfcAdapter mNfcAdapter;
    protected NFCForegroundOps mNfcForegroundOps = null;
    private boolean isAuthenticate = false;
    private RelativeLayout mRelativeLayoutProgressBar;
    // Process Log
    private ProcessLog mProcessLog;
    private boolean mBarcodeEnabled = false;

//    @NonNull
//    private final ServerTasks.View mView;

    // Runnable function to check the user idle time
    private Runnable mIdleCheckAuthentication;

    // Handler for idle time validation
    private final Handler mUserIdleHandler = new Handler();
    private PostUserValidationCheck postUserValidationCheck;
    private boolean isViewUserLogin = false;

    private boolean mIsBarcodeReady = false;
    // Location ID
    private int mLocationId;

    // HF Reader Operation
    HFReaderOps hfReaderOps;

    // Barcode Operation
    private BarcodeOps mBarcodeOps;

    private static final String TAG = UserAuthActivity.class.getSimpleName();

//    // HF Reader Operation
//    HFReaderOps hfReaderOps;
//    // Handler for receiving tagDetails
//    @SuppressLint("HandlerLeak")
//    private Handler mHandlerReader = new Handler() {
//        @Override
//        public void handleMessage(Message message) {
//            super.handleMessage(message);
//
//            if (message.arg1 == 0) {
//                // Get tag details object
//                String tagId = message.getData().getString("hf_tag_id");
//                String tagData = message.getData().getString("hf_tag_data");
//
//                if (tagId != null && !tagId.isEmpty()) {
//                    hfReaderOps.stop();
//                    hfReaderOps.setRunning(true);
//
//                    onBCUserLoginInterface.returnTagDetails(tagId, tagData);
//                }
//            }
//        }
//    };

//    public UserAuthActivity(boolean isBeepSoundOn, SoundUtils mSoundUtils) {
//        this.isBeepSoundOn = isBeepSoundOn;
//        this.mSoundUtils = mSoundUtils;
//    }

//    public PostUserValidationCheck(@NonNull ServerTasks.View view,
//                           SharedPreferenceManager sharedPreferenceManager) {
//        mView = view;
//        mView.setPresenter(this);
////        mPrefs = sharedPreferenceManager;
////        getHealthCheckAPI = new GetHealthCheck(this);
//    }
    // Process Log
//    private ProcessLog mProcessLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        UserAuthFragment userAuthFragment =
                (UserAuthFragment) getSupportFragmentManager().findFragmentById(R.id.user_auth_frame);

        if (userAuthFragment == null) {
            userAuthFragment = UserAuthFragment.newInstance();
            FragmentUtils.addFragmentToActivity(getSupportFragmentManager(), userAuthFragment, R.id.user_auth_frame, true);
        }

        //Start Setup NFC
        setupNfc();



        mRelativeLayoutProgressBar = (RelativeLayout)findViewById(R.id.progressbar_relativelayout);
        // Get barcode instance
        mBarcodeOps = new BarcodeOps(UserAuthActivity.this);
        mBarcodeOps.setmListener(this);
        mBarcodeEnabled = false;

//
//        mHFReaderOps = new HFReaderOps(mHandlerHFReader);
//        mHFReaderOps.initHFReader();
//        mHFReaderOps.read();
//        mHFReaderOps.setRunning(false);

        new UserAuthPresenter(userAuthFragment);
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

//    private HFReaderOps mHFReaderOps;

//    // Handler for HF Reader
//    @SuppressLint("HandlerLeak")
//    private Handler mHandlerHFReader = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.arg1 == 0) {
//                // Get tag details object
//                String tagId = msg.getData().getString("hf_tag_id");
//                String tagData = msg.getData().getString("hf_tag_data");
//
//                if (tagId != null && !tagId.isEmpty() && Utils.validateTagId(tagId)) {
//                    // Verified user tag id
//                    mHFReaderOps.setRunning(false);
////                    flipperView(tagId, tagData);
//                }
//            }
//        }
//    };


    @Override
    public void userLogin() {

        mBarcodeEnabled = true;


            mFragment = new BCUserLoginFragment();

            setFragmentDisplay(mFragment);

        //mHFReaderOps.setRunning(true);

        //setMenuActive(menuFragment);
       //setContainerLabel(R.string.bc_header_user_login);



       // savita

//        Intent mNextActivity = new Intent(UserAuthActivity.this, MainActivity.class);
//        mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
//        finish();
//        startActivity(mNextActivity);
    }

    private void setFragmentDisplay(Fragment mReFragment) {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mReFragment)
                .commit();
    }

    protected void onNewIntent(Intent intent) {
//      /**/  if (isViewUserLogin && !isAuthenticate) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            String newTagId = mNfcForegroundOps.bytesToHex(tag.getId());
            String newTagData = "";

            // Validate Login
            collectorLogin(newTagId, newTagData);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            mNfcForegroundOps.disableForeground();
        }
        catch (Exception e){
            Log.v(TAG, "This device doesn't support NFC");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mNfcForegroundOps.enableForeground();

            if(!mNfcForegroundOps.getNfc().isEnabled()) {
//                showToastMessage("Please activate NFC and press Back to return to the application!");
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                if(isAuthenticate) resetUserChecker();
            }
        }
        catch (Exception e){
            Log.v(TAG, "This device doesn't support NFC");
        }

        // Initialize barcode
        if (mBarcodeOps != null && !mIsBarcodeReady) mBarcodeOps.initBarcode();
    }

    // reset end session checker
    private void resetUserChecker() {
        stopUserChecker();
        startUserChecker();
    }
//    @Override
//    public void onValidTagId(String newTagId, String newTagData) {
//        // For Read Tags Not in used
//        if (isViewUserLogin) {
//           // mRelativeLayoutProgressBar.setVisibility(View.VISIBLE);
//            fetchUserDetails(newTagId, newTagData);
//        }
//    }


    /**
     * Check user based tagid or tagdata
     * return userinfo with unique number
     * then next view validate ADM
     *
     * @param tagId
     * @param tagData
     */
    private void fetchUserDetails(final String tagId, final String tagData) {

        if (mBarcodeEnabled) {
        TagIdItem tagIdItem = new TagIdItem(tagId, tagData);
        mRelativeLayoutProgressBar.setVisibility(View.VISIBLE);
//
//        // Log Data Type - Login
//        // Log - Login process
//        mProcessLog.setDataType(Constants.USER_TAG_ID);
//        mProcessLog.setLocationId(mLocationId);
//        mProcessLog.setModule(Constants.BED_COLLECTION);
//        mProcessLog.setEventType(Constants.LOGIN);
        TagData tagData1 = new TagData(tagId);
//        tagData1.setTagId(tagId);

        String url = Constants.SIT_CONTROLPOINT + "." + Constants.CONTROLPOINT;
        postUserValidationCheck = new PostUserValidationCheck(this);
        postUserValidationCheck.callAPI(url,tagId);
        }
        else{
            mBarcodeOps.stopScan();

        }



    } // END fetchUserDetails

    @Override
    public void onScanComplete(int i, int i1, byte[] bytes) {

    }

    @Override
    public void returnTagDetails(String tagId, String tagData) {
        // Returns tag details that was read in the HF reader
//        mRelativeLayoutProgressBar.setVisibility(View.VISIBLE);
        collectorLogin(tagId, tagData);
    }

    /**
     * Collector login process
     * Validate the collector id based on RFID tag
     * @param tagId collector's tag id
     */
    private void collectorLogin(String tagId, String tagData) {
//        if (isViewUserLogin && !isAuthenticate) {
            if (Utils.validateTagId(tagId) && Utils.validateRandomTagId(tagId)) {
//                if (isBeepSoundOn) {
////                    mSoundUtils.prepareMediaPlayer(this, Constants.BEEP_SOUND);
////                    mSoundUtils.playSound();
////                }
////                isAuthenticate = true;
                fetchUserDetails(tagId, tagData);
//            } else {
//                //need to implment
////                if (isViewUserLogin) setLoginUserFragment(Constants.BC_HOME);
//            }
        }
    }

    @Override
    public void barcodeReady(Boolean isReady) {

    }

    // stop end session checker
    private void stopUserChecker() {
        mUserIdleHandler.removeCallbacks(mIdleCheckAuthentication);
    }

    // start end session checker
    private void startUserChecker() {
        mUserIdleHandler.post(mIdleCheckAuthentication);
    }

    @Override
    public void userAuthError(int errorCode) {
        mRelativeLayoutProgressBar.setVisibility(View.GONE);
//        mView.showDialog(Constants.DLG_SERVER_UNAVAILABLE);
Log.d("User Auth Error", String.valueOf(errorCode));
        AlertDialog.Builder ad= new AlertDialog.Builder(this);
//        ad.setTitle("Alert Window");
        ad.setMessage("Invalid Login");
        ad.setPositiveButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public  void onClick(DialogInterface dilog , int which)
            {
                Intent mNextActivity = null;
//                mNextActivity = new Intent(UserAuthActivity.this, UserAuthActivity.class);
                mNextActivity = new Intent(UserAuthActivity.this, MainActivity.class);
                mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
                startActivity(mNextActivity);
                finish();
                dilog.dismiss();

            }
        });
        //ad.setIcon(R.drawable.ic_launcher);
//        ad.setMessage("This is an alert dialog box");
//        MyListener m= new MyListener();
//        ad.setPositiveButton("OK", m);
        ad.show();

    }

    @Override
    public void userAuthResponse(VerifyTagId verifyTagId) {
        Log.d("User Auth sucess", String.valueOf(verifyTagId));
        mRelativeLayoutProgressBar.setVisibility(View.GONE);
        if (verifyTagId.getUser().size() > 0)
        {
            Intent mNextActivity = null;
            mNextActivity = new Intent(UserAuthActivity.this, MainActivity.class);
            mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
            startActivity(mNextActivity);
            finish();
        }
        else
        {
            mBarcodeEnabled = false;
            AlertDialog.Builder ad= new AlertDialog.Builder(this);
//        ad.setTitle("Alert Window");
            ad.setMessage("Invalid Login");
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public  void onClick(DialogInterface dilog , int which)
                {
                    Intent mNextActivity = null;
                    mBarcodeEnabled = true;
                mNextActivity = new Intent(UserAuthActivity.this, UserAuthActivity.class);
//                    mNextActivity = new Intent(UserAuthActivity.this, MainActivity.class);
//                    mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
                    startActivity(mNextActivity);
                    finish();
                    dilog.dismiss();

                }
            });
            //ad.setIcon(R.drawable.ic_launcher);
//        ad.setMessage("This is an alert dialog box");
//        MyListener m= new MyListener();
//        ad.setPositiveButton("OK", m);
            ad.show();
        }



    }
    @Override
    public void onBackPressed() {

                        Intent mNextActivity = null;
                        mBarcodeEnabled = false;
                        mNextActivity = new Intent(UserAuthActivity.this, UserAuthActivity.class);
                        startActivity(mNextActivity);
                        finish();
    }


//    @Override
//    public void returnBarcodeResult(String mScanItem, String barcodeResult) {
//        // Return from BC2DScanFragment
////        if (!mIsScanReceive) {
////            if (isBeepSoundOn) {
////                mSoundUtils.prepareMediaPlayer(this, Constants.BEEP_SOUND);
////                mSoundUtils.playSound();
////            }
////            mIsScanReceive = true;
////            validateReturnValue(mScanItem, barcodeResult);
////        }
//    }

}


