package com.hrfid.acs.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.hrfid.acs.components.BaseActivity;

import java.math.BigInteger;


/**
 * Created by a.cabayao on 6/10/15.
 */
public abstract class TagIdInputOps extends BaseActivity {

    String tagDetails = "";
    String barcodeKey = "";
    String tagIdFormat = "E0";  // temporary this should get on the shared preference
    //OnKeyListener to display tag id and tag to the list view of tags and update tag count
    public View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int unicodeChar = event.getUnicodeChar();
            char keyCodeValue;

            if(event.getAction() == KeyEvent.ACTION_DOWN) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    validateDataConfiguration(tagDetails);
                    tagDetails = "";
                }
                else if(keyCode == KeyEvent.KEYCODE_DEL) {
                    onClearTagListView();
                } else {
                    if(unicodeChar > 0) {
                        keyCodeValue = (char)unicodeChar;
                        tagDetails += ("" + keyCodeValue);
                    }
                }
            }
            return false;
        }
    };
    // this variables is for benchmarking the reading of tagids from rfid reader
    boolean isTimerStarted = false;
    long nowTime;
    long startTime;
    double elapseTimeWithValidate;
    double elapseTimeWithoutValidate;
    String testTagData = "";

    public abstract void onClearTagListView();

    public abstract void onValidTagId(String newTagId, String newTagData);

    private void validateDataConfiguration(String newTagId) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String dataConfiguration = preference.getString("menu_data_configuration", "1");
        String[] tagIdValue = newTagId.split(",");
        String tagData = "0000000000000000";

        if(tagIdValue.length <= 0) return;
        String tagId = tagIdValue[0];

        if(tagIdValue.length >= 2) {
            tagData = newTagId.substring(tagId.length() + 1);
        }

        if(dataConfiguration.equals("1")) {
            validateTagId(tagId, tagData);
        } else if(dataConfiguration.equals("3")) {
            validateTagId(binToHex(tagId), tagData);
        } else if (dataConfiguration.equals("4")) {
            if (tagData == "0000000000000000") tagData = "";
            onValidTagId(tagId, tagData);
        }
    }

    private void validateTagId(String hexValue, String tagData) {

        String pattern = "[A-F0-9]+";
        String tagId = hexValue.toUpperCase();

        if(tagId.length() != 16) return;
        if(!tagId.substring(0, tagIdFormat.length()).equals(tagIdFormat)) return;

        if(tagId.matches(pattern)) {
            onValidTagId(tagId, tagData);
        }
        else {
            //showToastMessage("Not a valid Tag ID!");
            Log.v("", "pattern did not match!!!");
        }

    }

    private String binToHex(String binaryValue) {
        StringBuffer result = new StringBuffer();

        String pattern = "[0-1]+";
        if(binaryValue.length() != 64 || !binaryValue.matches(pattern)) return "ERROR";

        int index = 0;
        while (index < binaryValue.length()) {
            String value = binaryValue.substring(index, Math.min(index + 4,binaryValue.length()));
            int decimal = Integer.parseInt(value,2);
            String hexStr = Integer.toString(decimal, 16);
            result.append(hexStr);
            index += 4;
        }

        return result.toString();

    }

    private String hexToBin(String hexValue) {
        return new BigInteger(hexValue, 16).toString(2);
    }

}
