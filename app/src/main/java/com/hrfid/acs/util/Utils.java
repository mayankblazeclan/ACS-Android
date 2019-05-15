package com.hrfid.acs.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class Utils {


    private static final long TIME_TO_DISMISS = 3000; // 3 Seconds

    public static boolean isNotNullOrEmpty(String value) {
        if (null == value || value.isEmpty() || value.equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

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

    public static void createDialogTwoButtons(Context context, String title, boolean isCancelAble, String msg, String positiveLabel, String negativeLabel, DialogInterface.OnClickListener positiveOnClick, DialogInterface.OnClickListener negativeOnClick) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);
        alertDialog.setCancelable(isCancelAble);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
       // alertDialog.setIcon(R.drawable.ic_error);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(positiveLabel, positiveOnClick);
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(negativeLabel, negativeOnClick

        );

        // Showing Alert Message
        alertDialog.show();

    }

    public static void createDialogOneButtons(Context context, String title, boolean isCancelAble, String msg, String label, DialogInterface.OnClickListener onClick) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_error);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(label, onClick);


        // Showing Alert Message
        alertDialog.show();

    }

    public static AlertDialog showDialog(Context context,
                                         String title,
                                         String msg,
                                         String positiveLabel,
                                         DialogInterface.OnClickListener positiveOnClick,
                                         String negativeLabel,
                                         DialogInterface.OnClickListener negativeOnClick,
                                         boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setPositiveButton(negativeLabel, negativeOnClick);

        AlertDialog alertDialog = builder.create();
        if (isCancelAble) {
            alertDialog.setCanceledOnTouchOutside(true);
        } else {
            alertDialog.setCanceledOnTouchOutside(false);
        }
        alertDialog.show();
        return alertDialog;

    }

    public static String convertExpiryDate(StringBuffer stringBuffer) {
        stringBuffer.deleteCharAt(0);
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        //   stringBuffer.insert(2,'/');
        //   stringBuffer.insert(5,'/');

        String date = "" + stringBuffer.subSequence(0, 2);
        String month = "" + stringBuffer.subSequence(2, 4);
        String year = "" + stringBuffer.substring(4);

        LoggerLocal.error(TAG, "Date ========" + date + "/" + month + "/" + year);
/*
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd MMMM yyyy");
//        formatter = new SimpleDateFormat("yyyy MMMM dd");
        Date date = null;
        String strDate = null;
        try {
            date = formatter.parse(stringBuffer.toString());
            strDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }*/
        return year + "/" + month + "/" + date;
    }

    public static String returnComponentCode(StringBuffer stringBuffer) {

        stringBuffer.delete(0, 2);
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        return stringBuffer.toString();
    }

    public static String returnCodabarCodeData(StringBuffer value) {
        String result = "";
        if (isNotNullOrEmpty(value.toString())) {
            if (value.length() == Constants.CODABAR_LENGTH_DONATION_ID && (value.charAt(0) == 'A') && value.charAt(value.length() - 1) == 'A') {
                return Constants.CODABAR_DONATION_ID;
            } else if (value.length() == Constants.CODABAR_LENGTH_BLOOD_GROUP && (value.charAt(0) == 'D') && (value.charAt(value.length() - 1) == 'B')) {
                return Constants.CODABAR_BLOOD_GROUP;
            } else if (value.length() == Constants.CODABAR_LENGTH_COMPONENT_CODE && (value.charAt(0) == 'A') && (value.charAt(value.length() - 1) == 'B')) {
                return Constants.CODABAR_COMPONENT_CODE;
            } else if (value.length() == Constants.CODABAR_LENGTH_EXPIRY_DATE_AND_TIME && (value.charAt(0) == 'A') && (value.charAt(value.length() - 1) == 'A')) {
                return Constants.CODABAR_EXPIRY_DATE_AND_TIME;
            } else {
                return null;
            }
        }

        return result;
    }

    public static String returnISBT128CodeData(StringBuffer value) {
        String result = "";
        if (isNotNullOrEmpty(value.toString())) {
            if ((value.length() == Constants.ISBT_LENGTH_DONATION_ID) && (value.charAt(0) == '=' && value.charAt(1) == 'A')) {
                return Constants.ISBT_DONATION_ID;
            } else if (value.length() == Constants.ISBT_LENGTH_COMPONENT_CODE && (value.charAt(0) == '=' && value.charAt(1) == '<')) {
                return Constants.ISBT_COMPONENT_CODE;
            } else if (value.length() == Constants.ISBT_LENGTH_BLOOD_GROUP) {
                return Constants.ISBT_BLOOD_GROUP;
            } else if (value.length() == Constants.ISBT_LENGTH_EXPIRY_DATE_AND_TIME && (value.charAt(0) == '&' && value.charAt(1) == '>')) {
                return Constants.ISBT_EXPIRY_DATE_AND_TIME;
            } else if (value.length() == Constants.ISBT_LENGTH_SPECIAL_TESTING && (value.charAt(0) == '=' && value.charAt(1) == '\\')) {
                return Constants.ISBT_SPECIAL_TESTING;
            } else {
                return null;
            }

        }
        return result;

    }


    public static String getDonationId(boolean isIsbt, StringBuffer value) {
        if (isIsbt) {
            value.deleteCharAt(0);
            // value.delete(value.length() - 2, value.length());

            LoggerLocal.error(TAG, "In Donation ID =" + value.toString());
        } else {
            //no need to delete any char
            return value.toString();
        }

        return value.toString();
    }

    public static String getComponentCode(boolean isIsbt, StringBuffer value) {
        if (isIsbt) {
            value.delete(0, 2);
        } else {
            value.deleteCharAt(0);
            value.deleteCharAt(value.length() - 1);
        }
        return value.toString();
    }

    public static String getBloodGroup(boolean isIsbt, StringBuffer value) {

        if (isIsbt) {
            value.delete(0, 2);
        } else {
            value.deleteCharAt(0);
            value.deleteCharAt(value.length() - 1);
        }
        return value.toString();
    }

    public static String getExpiryDateAndTime(boolean isIsbt, StringBuffer value) throws ParseException {

        if (isIsbt) {
            String countryCode = "" + value.charAt(2);
            value.delete(0, 3);
            LoggerLocal.error(TAG, "Remaining date = " + value.toString());
            String input = "" + value.toString();
            DateFormat formatJulian = new SimpleDateFormat("yyDDDhhmm");
            Date jDate = formatJulian.parse(input);

            DateFormat fCurrent = new SimpleDateFormat("yyyy/MM/dd HH:MM");
            String output = fCurrent.format(jDate);
            LoggerLocal.error(TAG, "Output Format = " + output);
            return output;
        } else {
            String mDate = "" + Utils.convertExpiryDate(value) + " 23:59";

            return mDate;
        }

    }

    public static String getSpecialTestingCode(boolean isIsbt, StringBuffer value) {
        value.delete(0, 2);
        return value.toString();
    }


    public static void showAlertDialog(Activity activity, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_with_one_button, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        TextView tvDesc = (TextView) dialogView.findViewById(R.id.tv_dialog_desc);
        tvDesc.setText(msg);
        Button btDialogOk = (Button) dialogView.findViewById(R.id.bt_dialog_ok);
        btDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

/*    public static void showAlertDialogSuccessOrFaill(Activity activity, String title, String titleTextColor, String msg, int drawable, boolean isCancelable) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sucess_or_fail, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
//drawable = R.drawable.ic_check_circle_green;

        ImageView icon = (ImageView) dialogView.findViewById(R.id.iv_icon);
        icon.setImageResource(drawable);

        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tv_dialog_title);
        tvTitle.setTextColor(Color.parseColor(titleTextColor));
        tvTitle.setText(title);

        TextView tvDesc = (TextView) dialogView.findViewById(R.id.tv_dialog_desc);
        tvDesc.setText(msg);
       *//* Button btDialogOk = (Button) dialogView.findViewById(R.id.bt_dialog_ok);
        btDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*//*

        if (isCancelable) {
            alertDialog.setCanceledOnTouchOutside(true);
        } else {
            alertDialog.setCanceledOnTouchOutside(false);
        }
        alertDialog.show();

        //Handled close dialog after time interval

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    clearSPForVarifyDonationId(activity, false);
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                }
            }
        };


        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, TIME_TO_DISMISS);
    }*/


    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String mYear = "" + calendar.get(Calendar.YEAR);
        String mMonth = "" + (calendar.get(Calendar.MONTH) + 1);
        String mDate = "" + calendar.get(Calendar.DATE);
        String mHours = "" + calendar.get(Calendar.HOUR);
        String mMin = "" + calendar.get(Calendar.MINUTE);
        String mSec = "" + calendar.get(Calendar.SECOND);
        String mMiliSec = "" + calendar.get(Calendar.MILLISECOND);

        String currentDateTime = "" + mYear + "/" + mMonth + "/" + mDate + " " + mHours + ":" + mMin + ":" + mSec;
        LoggerLocal.error(TAG, "Current Date and Time =" + currentDateTime);
        LoggerLocal.error(TAG, "new Date =" + new Date());


        return currentDateTime;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }


    public static String getBluetoothMacAdd(Context context) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Device does not support bluetooth!",
                    Toast.LENGTH_SHORT).show();
            return null;
        }

        return mBluetoothAdapter.getAddress();
    }

    public static Map<String, Object> getDeviceManufactureData() {
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.DEVICE_MODEL, Build.MODEL);
        data.put(Constants.DEVICE_MANUFACTURE, Build.MANUFACTURER);
        data.put(Constants.DEVICE_SERIAL, Build.SERIAL);
        data.put(Constants.DEVICE_VERSION, Build.VERSION.RELEASE);

        return data;
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

    public static String getAppVersion(Context context)
    {
        String result ="";

        try
        {
            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
            LoggerLocal.error(TAG, "getAppVersion()="+result);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            LoggerLocal.error(TAG, e.getMessage());
        }
        LoggerLocal.error(TAG, "getAppVersion() ="+result);
        return result;
    }

}
