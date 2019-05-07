package com.hrfid.acs.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.IOException;


public class FragmentUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, boolean isNext) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isNext) {
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {
            transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void removedFragmentToActivity() {

    }




    final static String[] arrHexStrErrorString = {
            "Printer Offline", // 30|31|32|33
            "Battery Near End", // 42|4E
            "Buffer Full", // 43|4F|61
            "Battery Error", // 64
            "Paper End", // 63
            "Sensor Error", // 66
            "Head Error", // 67
            "Cover Open", // 68
            "Other Error" // 6B|00
    };


    /**
     * Validate user login tag id
     *
     * @param tagId
     * @return
     */
    public static boolean validateTagId(String tagId) {
        boolean isValid;

        int tagIdLength = tagId.length();
        int correctLength = 16;

        if (tagIdLength != correctLength) {
            isValid = false;
        } else {
            String tagIdPattern = "E004";
            String tagIdChar = tagId.substring(0, 4);

            isValid = tagIdChar.equals(tagIdPattern);
        }

        return isValid;
    }

    /**
     * Validate the random tag data the reader is generating
     * When the reader is initialized
     *
     * @param tagId
     * @return
     */
    public static boolean validateRandomTagId(String tagId) {
        String randomTagId = "E004";
        String newTagId = tagId.substring(0, randomTagId.length());

        if (randomTagId.equalsIgnoreCase(newTagId)) {
            if (!tagId.equalsIgnoreCase("0000000000000000")) {
                return true;
            }
        }
        return false;
    }


    /**
     * Return device wifi mac address
     *
     * @param context
     * @return - String device wifi mac address UPPERCASE
     */
    public static String getDeviceWifiMacAddress(Context context) {
        String deviceWifiMacAdd;

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();

        deviceWifiMacAdd = wInfo.getMacAddress();

        return deviceWifiMacAdd.toUpperCase();
    }

    /**
     * converts hex to ascii
     *
     * @param hexStr hex to be converted
     * @return ASCII equivalent
     */
    public static String hexToAscii(String hexStr) {
        String output = "";

        StringBuilder stringBuilder = new StringBuilder("");
        if (!hexStr.isEmpty()) {
            for (int i = 0; i < hexStr.length(); i += 2) {
                String str = hexStr.substring(i, i + 2);
                stringBuilder.append((char) Integer.parseInt(str, 16));
            }

            output = stringBuilder.toString();
        }

        return output;
    }

    /**
     * Return the equivalent error code from
     * the hex value response by the printer
     * @param hexStr {String} hex
     * @return {String} return the error c
     */
    public static String returnPrinterStatus(String hexStr) {

        String rtnString = "OK";
        String[] arrHexStrError = {"30|31|32|33", "42|4E", "43|4F|61", "64", "63", "66", "67", "68", "6B|00"};

        for (int i = 0; i < arrHexStrError.length; i++) {
            if (arrHexStrError[i].contains("|")) {
                String[] arrayCode = arrHexStrError[i].split("\\|");
                for (int x = 0; x < arrayCode.length; x++) {
                    if (hexStr.equalsIgnoreCase(arrayCode[x])) {
                        rtnString = arrHexStrErrorString[i];
                        break;
                    }
                }
            } else {
                if (hexStr.equalsIgnoreCase(arrHexStrError[i])) {
                    rtnString = arrHexStrErrorString[i];
                    break;
                }
            }
        }

        return rtnString;
    }


    /**
     * Verify the internet connection
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Return a error message
     * for the dialog display for for printer error
     *
     * @param errorStatus {String} define error code
     * @return - Error message
     */
    public static String returnPrintErrorMessage(String errorStatus) {

        String[] arrHexStrErrorMsg = {
                "Printer Offline - please press the \"Print\" button on printer", // "Printer Offline" // 30|31|32|33
                "Printer Battery Flat - Please recharge battery.",  // "Battery Near End" // 42|4E
                "Printer Buffer Full - Please turn printer off and on again.", // "Buffer Full" // 43|4F|61
                "Printer Battery Flat - Please recharge battery.",  // "Battery Near End" // 64
                "Printer Paper End - Please change label roll.", // "Paper End" // 63
                "Printer Label Misaligned - Please press the \"Feed\" button on printer.", // "Sensor Error" // 66
                "Printer Head Error - Please contact Support.", // "Head Error" // 67
                "Printer Cover Open - Please close cover and press the \"Print\" button on printer", // "Cover Open" // 68
                "Printer Error - Please check the printer and try again." // "Other Error", // 6B
        };

        String rtnString = arrHexStrErrorMsg[8];

        for (int i = 0; i < arrHexStrErrorString.length; i++) {
            if (errorStatus.equalsIgnoreCase(arrHexStrErrorString[i])) {
                rtnString = arrHexStrErrorMsg[i];
                break;
            }
        }

        return rtnString;
    } // END returnPrintErrorMessage

    /**
     * Created by brentmercader on 06/03/2017.
     */

    public static class SoundUtils {

        private static SoundUtils soundUtils;

        static {
            soundUtils = new SoundUtils();
        }

        MediaPlayer mediaPlayer;

        public SoundUtils() {
        }

        public static SoundUtils getInstance() {
                return soundUtils;
        }

        public void prepareMediaPlayer(Context context, String soundFilePath) {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Uri uri = Uri.parse(soundFilePath);
            try {
                mediaPlayer.setDataSource(context, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(100, 100);
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void playSound() {
            stopMediaPlayer();
            mediaPlayer.start();
        }

        public void stopMediaPlayer() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }


    }


}