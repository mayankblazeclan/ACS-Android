package com.hrfid.acs.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private SharedPreferences mSharedPreferences;

    private SharedPreferences.Editor mEditor;

    public SharedPreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
    }

    /**
     * SET VALUE in Shared Preference
     * String
     * Int
     * Double
     * Long
     * Boolean
     * */

    /**
     * Store String Value in Shared Preference
     * @param sharedKey
     * @param sharedValue - String
     */
    public void setSharedValue (String sharedKey, String sharedValue) {
        mSharedPreferences
                .edit()
                .putString(sharedKey, sharedValue)
                .apply();
    }

    /**
     * Store Int Value in Shared Preference
     * @param sharedKey
     * @param sharedValue - Int
     */
    public void setSharedValue (String sharedKey, int sharedValue) {
        mSharedPreferences
                .edit()
                .putInt(sharedKey, sharedValue)
                .apply();
    }

    /**
     * Store Double Value in Shared Preference - Converted to String
     * @param sharedKey
     * @param sharedValue - Double
     */
    public void setSharedValue (String sharedKey, double sharedValue) {
        mSharedPreferences
                .edit()
                .putString(sharedKey, Double.toString(sharedValue))
                .apply();
    }

    /**
     * Store Long Integer Value in Shared Preference
     * @param sharedKey
     * @param sharedValue - Long Integer
     */
    public void setSharedValue (String sharedKey, long sharedValue) {
        mSharedPreferences
                .edit()
                .putLong(sharedKey, sharedValue)
                .apply();
    }

    /**
     * Store Boolean Value in Shared Preference
     * @param sharedKey
     * @param sharedValue - Boolean
     */
    public void setSharedValue (String sharedKey, boolean sharedValue) {
        mSharedPreferences
                .edit()
                .putBoolean(sharedKey, sharedValue)
                .apply();
    }
    // END SET VALUE

    /* *
     * GET VALUE in Shared Preference
     * String
     * Int
     * Double
     * Long
     * Boolean
     * */

    /**
     * Return String Value from Shared Preference
     * @param key
     * @param defaultValue
     * @return String
     */
    public String getValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Return Int Value from Shared Preference
     * @param key
     * @param defaultValue
     * @return Int
     */
    public int getValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Return Double Value from Shared Preference
     * @param key
     * @param defaultValue
     * @return Double
     */
    public double getValue(String key, double defaultValue) {
        return Double.parseDouble(mSharedPreferences.getString(key, Double.toString(defaultValue)));
    }

    /**
     * Return Long Value from Shared Preference
     * @param key
     * @param defaultValue
     * @return Long
     */
    public long getValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Return Boolean Value from Shared Preference
     * @param key
     * @param defaultValue
     * @return Boolean
     */
    public boolean getValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    // END GET VALUE
}
