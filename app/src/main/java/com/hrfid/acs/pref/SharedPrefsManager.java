/*
 * This will create the Sharedpreference to manage the persistent data for saving details
 * received from the server. It is in xml format file which can be viewable in DDMS of
 * ADT (Android Developer Tool) IDE or Android Studio.
 *
 * @author      Armee Cabayao
 * @version     0.1
 * @since       2014-07-25
 */
package com.hrfid.acs.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hrfid.acs.util.LoggerLocal;

import static android.support.constraint.Constraints.TAG;

public class SharedPrefsManager {

    private static final String HEALTH_RFID_PREFS = "au.com.pathologyapp.pathologyapp.prefs";
    private static final String REGISTERED = "registered";
    private static final String API_URL = "api_url";
    private static final String PORT = "port";
    private static final String DEVICE_ID = "device_id";
    private static final String DEVICE_NAME = "device_name";
    private static final String REGISTER_ID = "register_id";
    private static final String LOCATION_ID = "location_id";
    private static final String LOCATION_TYPE = "location_type";
    private static final String LOCATION_NAME = "location_name";
    private static final String LICENSE_KEY = "license_key";
    private static final String TLS_SUPPORT = "tls_support";
    private static final String SERVER_VERSION = "version";
    private static final String BLUETOOTH_ROW_COUNT = "bluetooth_row_count";
    private static final String FRIDGE_CURRENT_TEMPERATURE = "fridge_current_temperature";
    private static final String DEMO_LAB = "demo_lab";
    private static final String SINGLE_AUTHENTICATION = "single_authentication";
    private static final String USER_TAGID = "user_tagid";
    private static final String SINGLE_SHOT = "single_shot";
    private static final String USER_AUTHENTICATON = "user_authentication";
    private static final String USER_IDLE_TIME = "user_idle_time";
    private static final String USER_NAME = "user_name";
    private static final String PATHOLOGY_CONFIG = "pathology_config";
    private static final String BEEP_CONFIG = "beep_config";
    private static final String LABEL_TYPE_CONFIG = "label_config";
    private static final String TIME_OUT = "time_out";
    private static final String OVERRIDE = "override";
    private static final String IS_LABEL_DECL_ON = "is_label_declaration_on";
    private static final String VERBAL_ID = "verbal_id_cnfg";
    private static final String TAKE_BLOOD = "take_blood_cnfg";
    private static final String STICK_SAMPLE = "stick_sample_cnfg";
    private static final String AUTHENTICATION = "authentication";
    private static final String USER_ID = "user_id";
    private static final String LOC_PARENT = "location_parent";

    private static final String CREATED_ON_DATE = "created_on_date";
    private static final String ORG_ID = "org_id";
    private static final String BLOOD_COMPONENT_ID = "blood_component_id";
    private static final String EXPIRY_DATE_AND_TIME = "expiry_date_and_time";
    private static final String DONATION_ID_BARCODE = "donation_id_barcode";
    private static final String BLOOD_GROUP_BARCODE = "blood_group_barcode";
    private static final String BLOOD_GROUP_NAME = "blood_group_name";
    private static final String PRODUCT_CODE_BARCODE = "product_barcode";
    private static final String SPECIAL_CODE_BARCODE = "special_code_barcode";
    private static final String SPECIAL_TESTING_NAME = "special_testing_name";
    private static final String COMPONENT_CODE_BARCODE = "component_code_barcode";
    private static final String COMPONENT_ID = "component_id";
    private static final String COMPONENT_NAME = "component_name";


    private static final String BARCODE_SCAN_TYPE = "bc_scan_type";



    private SharedPreferences mPrefs;
    private Editor editor;

    public SharedPrefsManager() {
    }

    private SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(HEALTH_RFID_PREFS, Context.MODE_PRIVATE);
    }

    /*
     * This will set the tagid of the user.
     * @param context the application context where the call was made
     * @param userTagId Sets the string value of userTagId.
     * @return Nothing.
     */
    public void setUserTagId(Context context, String userTagId) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(USER_TAGID, userTagId);
        editor.apply();
    }

    /*
     * This will return the tagid of user for the single authentication process.
     * @param context The application context where the call was made
     * @return String This returns the string value of the user tag id.
     */
    public String getUserTagId(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(USER_TAGID, "");


    }

    public void setLocParent(Context context, Integer location) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(LOC_PARENT, location);
        editor.apply();
    }

    /*
     * This will return the tagid of user for the single authentication process.
     * @param context The application context where the call was made
     * @return String This returns the string value of the user tag id.
     */
    public Integer getLocParent(Context context) {
        mPrefs = getSharedPrefs(context);
        return mPrefs.getInt(LOC_PARENT, 0);


    }


    /*
     * This will set the single authentication process.
     * @param context the application context where the call was made
     * @param isSingleAuthentication Sets the boolean value authentication status.
     * @return Nothing.
     */
    public void setSingleAuthentication(Context context, boolean isSingleAuthentication) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(SINGLE_AUTHENTICATION, isSingleAuthentication);
        editor.apply();
    }

    /*
     * Check if the device was successfully set to single authentication.
     * @param context The application context where the call was made
     * @return boolean This returns the current authentication status .
     */
    public boolean isSingleAuthencation(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(SINGLE_AUTHENTICATION, false);
    }

    /*
     * This will set the status if the device is successfully registered.
     * @param context the application context where the call was made
     * @param regStatus Sets the current registration status.
     * @return Nothing.
     */
    public void setRegistered(Context context, boolean regStatus) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(REGISTERED, regStatus);
        editor.apply();
    }

    /*
     * Check if the device was successfully registered at the server.
     * @param context The application context where the call was made
     * @return boolean This returns the current registration status .
     */
    public boolean isRegistered(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(REGISTERED, false);
    }

    public void setDemoLab(Context context, boolean isDemoLab) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(DEMO_LAB, isDemoLab);
        editor.apply();
    }

    public boolean isDemoLab(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(DEMO_LAB, false);
    }

    /*
     * This will set base url for the api and save it in a xml format using shared preference.
     * @param context the application context where the call was made
     * @param apiUrl The base url for the http connection use.
     * @return Nothing.
     */
    public void setApiUrl(Context context, String apiUrl) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(API_URL, apiUrl);
        editor.apply();
    }

    /*
     * This will return the base url for the http connection use.
     * @param context The application context where the call was made
     * @return String This returns the string value of the api url.
     */
    public String getApiUrl(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(API_URL, "");
    }

    /*
     * This will set the current port to use and save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param portValue The port value to be use in http connection.
     * @return Nothing.
     */
    public void setPort(Context context, int portValue) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(PORT, portValue);
        editor.apply();
    }

    /*
     * This will return the port for the http connection use.
     * @param context The application context where the call was made
     * @return int This returns the integer value of the port.
     */
    public int getPort(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(PORT, 80);
    }

    /*
     * This will set the current device id which the server assigned during the device
     * registration. And save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param deviceId The device id value.
     * @return Nothing.
     */
    public void setDeviceId(Context context, String deviceId) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(DEVICE_ID, deviceId);
        editor.apply();
    }

    /*
     * This will return the device unique id assign by the server.
     * @param context The application context where the call was made
     * @return int This returns the integer value of the device id.
     */
    public String getDeviceId(Context context) {
        mPrefs = getSharedPrefs(context);

        return  mPrefs.getString(DEVICE_ID,"null");

    }

    public void setDeviceName(Context context, String value) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(DEVICE_NAME, value);
        editor.apply();
    }

    public String getDeviceName(Context context){
        mPrefs = getSharedPrefs(context);
        return mPrefs.getString(DEVICE_NAME, "null");
    }

    /*
     * This will set the current location id which the server assigned during the device
     * registration. And save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param locId The value of the location id.
     * @return Nothing.
     */
    public void setLocId(Context context, int locId) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(LOCATION_ID, locId);
        editor.apply();
    }

    /*
     * This will return the location id assigned by the server during device registration.
     * @param context The application context where the call was made
     * @return int This returns the integer value of location id.
     */
    public int getLocId(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(LOCATION_ID, 0);
    }

    /*
     * This will set the current location type which the user selected during
     * the device registration. And save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param locType The value of the location type.
     * @return Nothing.
     */
    public void setLocType(Context context, int locType) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(LOCATION_TYPE, locType);
        editor.apply();
    }

    /*
     * This will return the location type selected by the user during the
     * during device registration.
     * @param context The application context where the call was made
     * @return int This returns the integer value of location type.
     */
    public int getLocType(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(LOCATION_TYPE, 0);
    }

    /*
     * This will set the current location name which the user selected during
     * the device registration. And save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param locName The value of the location name.
     * @return Nothing.
     */
    public void setLocName(Context context, String locName) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(LOCATION_NAME, locName);
        editor.apply();
    }

    /*
     * This will return the location name selected by the user during the
     * during device registration.
     * @param context The application context where the call was made
     * @return String This returns the string value of location name.
     */
    public String getLocName(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(LOCATION_NAME, "");
    }

    /*
     * This will return the registered id after the registration of the device
     * @param context the application context where the call was made
     * @return Integer This returns the int value of the registered device
     */
    public int getRegisterId(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(REGISTER_ID, 0);
    }

    /*
     * This will set the btn_register id after
     * the device registration. And save the data in an xml format using sharedpreference.
     * @param context the application context where the call was made
     * @param registerId The value of the btn_register id.
     * @return Nothing.
     */
    public void setRegisterId(Context context, int registerId) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(REGISTER_ID, registerId);
        editor.apply();
    }


    public String getLicenseKey(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(LICENSE_KEY, "");
    }

    /*
     * This will set the authorization key to be able to use the application.
     * @param context the application context where the call was madeD
     * @param key The value of the license key
     * @return Nothing.
     */
    public void setLicenseKey(Context context, String key) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(LICENSE_KEY, key);
        editor.apply();
    }

    /*
     * This will return if transport layer security is used.
     * @param context The application context where the call was made
     * @return boolean This returns the boolean value if TLS is being used.
     */
    public boolean getIsTLS(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(TLS_SUPPORT, false);
    }

    /*
     * This will set if transport layer security will be used during http request.
     * @param context the application context where the call was made
     * @param isTLS The value if TLS is being used.
     * @return Nothing.
     */
    public void setIsTLS(Context context, boolean isTLS) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(TLS_SUPPORT, isTLS);
        editor.apply();
    }

    /*
     * This will set the current server version.
     * @param context the application context where the call was made
     * @param version The value of the server version.
     * @return Nothing.
     */
    public void setServerVersion(Context context, String version) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(SERVER_VERSION, version);
        editor.apply();
    }

    /*
     * This will return the current server version.
     * @param context The application context where the call was made
     * @return String This returns the string value of the current server version.
     */
    public String getServerVersion(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(SERVER_VERSION, "");
    }

    public void setFridgeCurrentTemperature(Context context, long currTemp) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putLong(FRIDGE_CURRENT_TEMPERATURE, currTemp);
        editor.apply();
    }

    public long getFridgeCurrentTemperature(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getLong(FRIDGE_CURRENT_TEMPERATURE, 0);
    }

    public void setBlueToothRowCount(Context context, long blueToothRowCount) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putLong(BLUETOOTH_ROW_COUNT, blueToothRowCount);
        editor.apply();
    }

    public long getBlueToothRowCount(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getLong(BLUETOOTH_ROW_COUNT, 0);
    }


    /*
     * This will set the single shot process.
     * @param context the application context where the call was made
     * @param isSingleShot Sets the boolean value authentication status.
     * @return Nothing.
     */
    public void setSingleShot(Context context, boolean isSingleShot) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(SINGLE_SHOT, isSingleShot);
        editor.apply();
    }

    /*
     * Check if the device was successfully set to single shot.
     * @param context The application context where the call was made
     * @return boolean This returns the current authentication status .
     */
    public boolean isSingleShot(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(SINGLE_SHOT, false);
    }

    /**
     * This will set for user auhtentication process
     */
    public void setUserAuthentication(Context context, boolean isAuthentication) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(USER_AUTHENTICATON, isAuthentication);
        editor.apply();
    }

    /**
     * Check if the user was authenticated.
     *
     * @return boolean if the user was authenticated
     */
    public boolean isAuthentication(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(USER_AUTHENTICATON, false);
    }

    public void setUserIdleTime(Context context, long milleseconds) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putLong(USER_IDLE_TIME, milleseconds);
        editor.apply();
    }

    public long getUserIdleTime(Context context) {
        mPrefs = getSharedPrefs(context);
        long currentTime = System.currentTimeMillis();

        return mPrefs.getLong(USER_IDLE_TIME, currentTime);
    }

    public void setUserName(Context context, String userName) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public String getUserName(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(USER_NAME, "");
    }

    /**
     * Set Pathology Server Config
     *
     * @param context    context
     * @param pathConfig pathConfig
     */
    public void setPathConfig(Context context, String pathConfig) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putString(PATHOLOGY_CONFIG, pathConfig);
        editor.apply();
    }

    /**
     * Get the Pathogy Server Config
     *
     * @return pathology config
     */
    public String getPathConfig(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getString(PATHOLOGY_CONFIG, "");
    }

    /*
     * This will set the single shot process.
     * @param context the application context where the call was made
     * @param isSingleShot Sets the boolean value authentication status.
     * @return Nothing.
     */
    public void setBeepOn(Context context, boolean isSingleShot) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(BEEP_CONFIG, isSingleShot);
        editor.apply();
    }

    /*
     * Check if the device was successfully set to single shot.
     * @param context The application context where the call was made
     * @return boolean This returns the current authentication status .
     */
    public boolean isBeepOn(Context context) {
        mPrefs = getSharedPrefs(context);


        return mPrefs.getBoolean(BEEP_CONFIG, false);
    }

    // Set the label type for a specific location
    public void setLabelType(Context context, int labelType) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(LABEL_TYPE_CONFIG, labelType);
        editor.apply();
    }

    // returns the label type of a specific location
    public int getLabelType(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(LABEL_TYPE_CONFIG, 0);
    }

    // Sets the timeout for end session
    public void setTimeOut(Context context, int timeOut) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putInt(TIME_OUT, timeOut);
        editor.apply();
    }

    // Gets the timeout for end session
    public int getTimeOut(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getInt(TIME_OUT, 10);

    }

    // Set override value in shared pref
    public void setOverride(Context context, boolean override) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(OVERRIDE, override);
        editor.apply();
    }

    // Get override value in shared pref
    public boolean getOverride(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(OVERRIDE, false);
    }

    // Set is label value in shared pref
    public void setIsLabelDeclarationON(Context context, boolean override) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(IS_LABEL_DECL_ON, override);
        editor.apply();
    }

    // Get override value in shared pref
    public boolean isLabelDeclarationOn(Context context) {
        mPrefs = getSharedPrefs(context);
        return mPrefs.getBoolean(IS_LABEL_DECL_ON, false);
    }

    // Set verbal id config value in shared pref
    public void setVerbalIdEnabled(Context context, boolean verbalIdEnabled) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(VERBAL_ID, verbalIdEnabled);
        editor.apply();
    }

    // Get verbal id config value in shared pref
    public boolean getVerbalIdEnabled(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(VERBAL_ID, false);
    }


    // Set stick label config value in shared pref
    public void setTakeBloodEnabled(Context context, boolean takeBloodEnabled) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(TAKE_BLOOD, takeBloodEnabled);
        editor.apply();
    }

    // Get stick label config value in shared pref
    public boolean getTakeBloodEnabled(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(TAKE_BLOOD, false);
    }

    // Set stick label config value in shared pref
    public void setStickLabelEnabled(Context context, boolean stickLabelEnabled) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(STICK_SAMPLE, stickLabelEnabled);
        editor.apply();
    }

    // Get stick label config value in shared pref
    public boolean getStickLabelEnabled(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(STICK_SAMPLE, false);
    }

    // Set authentication config value in shared pref
    public void setAuthenticationEnabled(Context context, boolean authenticationEnabled) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        editor.putBoolean(AUTHENTICATION, authenticationEnabled);
        editor.apply();
    }

    // Get authentication config value in shared pref
    public boolean getAuthenticationEnabled(Context context) {
        mPrefs = getSharedPrefs(context);

        return mPrefs.getBoolean(AUTHENTICATION, false);
    }


    //Set Barcode Scan Type 1) COdabar 2) Isbt-128
    public void setBarcodeScanType(Context context, String value) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (value == null) value = "";
        editor.putString(BARCODE_SCAN_TYPE, value);
        editor.apply();
    }
    // Get Barcode Scan Type

    public String getBarcodeScanType(Context context) {
        mPrefs = getSharedPrefs(context);
        return mPrefs.getString(BARCODE_SCAN_TYPE, "null");
    }


    // Set user Id login
    public void setUserId(Context context, Integer userId) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (userId == null) userId = 0;
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    // Get user Id login
    public Integer getUserId(Context context) {
        mPrefs = getSharedPrefs(context);
        Integer userId = mPrefs.getInt(USER_ID, 0);
        if (userId == 0) userId = null;
        return userId;
    }

    // Crated On Date
    public void setCreatedOnDate(Context context, String value) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (value == null) {
            value = "";
        }

        editor.putString(CREATED_ON_DATE, value);
        editor.apply();

    }

    //ORG ID

    public void setOrgID(Context context, String value) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (value == null) {
            value = "";
        }

        editor.putString(ORG_ID, value);
        editor.apply();

    }

//Blood Component Id

    public void setBloodComponentId(Context context, String value) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (value == null) {
            value = "";
        }
        editor.putString(BLOOD_COMPONENT_ID, value);
        editor.apply();

    }

    //Donation ID
    public void setDonationIdBarcode(Context context, String donationIdBarcode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (donationIdBarcode == null) {
            donationIdBarcode = "";
        }

        editor.putString(DONATION_ID_BARCODE, donationIdBarcode);
        editor.apply();

    }

    public void setBloodGroupBarcode(Context context, String bloodGroupBarcode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (bloodGroupBarcode == null) {
            bloodGroupBarcode = "";
        }

        editor.putString(BLOOD_GROUP_BARCODE, bloodGroupBarcode);
        editor.apply();

    }

    public void setBloodGroupName(Context context, String bloodGroupName) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (bloodGroupName == null) {
            bloodGroupName = "";
        }

        editor.putString(BLOOD_GROUP_NAME, bloodGroupName);
        editor.apply();

    }


    public void setProductCodeBarcode(Context context, String producCodeBarcode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (producCodeBarcode == null) {
            producCodeBarcode = "";
        }

        editor.putString(PRODUCT_CODE_BARCODE, producCodeBarcode);
        editor.apply();

    }


    public void setSpecialCodeBarcode(Context context, String specialCodeBarcode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (specialCodeBarcode == null) {
            specialCodeBarcode = "";
        }

        editor.putString(SPECIAL_CODE_BARCODE, specialCodeBarcode);
        editor.apply();

    }


    public void setSpecialTestingName(Context context, String specialTestingBarcode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (specialTestingBarcode == null) {
            specialTestingBarcode = "";
        }

        editor.putString(SPECIAL_TESTING_NAME, specialTestingBarcode);
        editor.apply();

    }


    public void setExpiryDateAndTime(Context context, String expiryDateAndTime) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (expiryDateAndTime == null) {
            expiryDateAndTime = "";
        }

        editor.putString(EXPIRY_DATE_AND_TIME, expiryDateAndTime);
        editor.apply();

    }

    public void setComponentCodeBarcode(Context context, String componentCode) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (componentCode == null) {
            componentCode = "";
        }

        editor.putString(COMPONENT_CODE_BARCODE, componentCode);
        editor.apply();

    }

    public void setComponentCodeName(Context context, String componentName) {
        mPrefs = getSharedPrefs(context);
        editor = mPrefs.edit();
        if (componentName == null) {
            componentName = "";
        }

        editor.putString(COMPONENT_NAME, componentName);
        editor.apply();

    }


    public String getDonationIdBarcode(Context context) {
        mPrefs = getSharedPrefs(context);
        String donationIdBarcode = mPrefs.getString(DONATION_ID_BARCODE, "null");
        if (donationIdBarcode == "null") donationIdBarcode = null;
        return donationIdBarcode;
    }

    public String getBloodGroupBarcode(Context context) {
        mPrefs = getSharedPrefs(context);
        String bloodGroupBarcode = mPrefs.getString(BLOOD_GROUP_BARCODE, "null");
        if (bloodGroupBarcode == "null") bloodGroupBarcode = null;
        return bloodGroupBarcode;
    }


    public String getBloodGroupName(Context context) {
        mPrefs = getSharedPrefs(context);
        String bloodGroupName = mPrefs.getString(BLOOD_GROUP_NAME, "null");
        if (bloodGroupName == "null") bloodGroupName = null;
        return bloodGroupName;
    }


    public String getComponentCodeBarcode(Context context) {
        mPrefs = getSharedPrefs(context);
        String componentCodeBarcode = mPrefs.getString(COMPONENT_CODE_BARCODE, "null");
        if (componentCodeBarcode == "null") componentCodeBarcode = null;
        return componentCodeBarcode;
    }


  public String getComponentName(Context context) {
        mPrefs = getSharedPrefs(context);
        String componentName = mPrefs.getString(COMPONENT_NAME, "null");
        if (componentName == "null") componentName = null;
        return componentName;
    }


    public String getProductCodeBarcode(Context context) {
        mPrefs = getSharedPrefs(context);
        String productCodeBarcode = mPrefs.getString(PRODUCT_CODE_BARCODE, "null");
        if (productCodeBarcode == "null") productCodeBarcode = null;
        return productCodeBarcode;
    }

    public String getExpiryDateAndTime(Context context) {
        mPrefs = getSharedPrefs(context);
        String expiryDateAndTime = mPrefs.getString(EXPIRY_DATE_AND_TIME, "null");
        if (expiryDateAndTime == "null") expiryDateAndTime = null;
        return expiryDateAndTime;
    }


    public String getSpecialCodeBarcode(Context context) {
        mPrefs = getSharedPrefs(context);
        String specialCodeBarcode = mPrefs.getString(SPECIAL_CODE_BARCODE, "null");
        if (specialCodeBarcode == "null") specialCodeBarcode = null;
        return specialCodeBarcode;
    }

    public String getSpecialTestingName(Context context) {
        mPrefs = getSharedPrefs(context);
        String specialTestingName = mPrefs.getString(SPECIAL_TESTING_NAME, "null");
        if (specialTestingName == "null") specialTestingName = null;
        return specialTestingName;
    }

    public String getOrgId(Context context) {
        mPrefs = getSharedPrefs(context);
        String value = mPrefs.getString(ORG_ID, "null");
        if (value == "null") value = null;
        return value;
    }

    public String getCreatedOn(Context context) {
        mPrefs = getSharedPrefs(context);
        String value = mPrefs.getString(CREATED_ON_DATE, "null");
        if (value == "null") value = null;
        return value;
    }

    public String getBloodComponentId(Context context) {
        mPrefs = getSharedPrefs(context);
        String value = mPrefs.getString(BLOOD_COMPONENT_ID, "null");
        if (value == "null") value = null;
        return value;
    }



//Delete SP data
public static final void clearSPForVarifyDonationId(Context context, boolean skipDonationId) {
    SharedPrefsManager pref = new SharedPrefsManager();
//    TODO delete SP here
    if(skipDonationId){
        LoggerLocal.error(TAG, "Delete SP here ");
      //  pref.setDonationIdBarcode(context, null);
        pref.setBloodGroupBarcode(context, null);
        pref.setComponentCodeBarcode(context, null);
        pref.setBloodComponentId(context, null);
        pref.setComponentCodeName(context, null);
        pref.setExpiryDateAndTime(context, null);
        pref.setSpecialCodeBarcode(context, null);
    }
    else {
        LoggerLocal.error(TAG, "Delete SP here ");
        pref.setDonationIdBarcode(context, null);
        pref.setBloodGroupBarcode(context, null);
        pref.setComponentCodeBarcode(context, null);
        pref.setBloodComponentId(context, null);
        pref.setComponentCodeName(context, null);
        pref.setExpiryDateAndTime(context, null);
        pref.setSpecialCodeBarcode(context, null);
    }
    }
}

