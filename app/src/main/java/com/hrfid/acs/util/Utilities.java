package com.hrfid.acs.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * General Utility Methods
 * Created by racitsolutionspvtltd on 19/01/17.
 */

public class Utilities {

    private static int size = 660;
    private static int size_width = 660;
    private static int size_height = 220;
    private static int big_size_width = 1800;
    private static int big_size_height = 164;

    public static boolean isNetworkConnected(Context cmgContext) {
        ConnectivityManager cm = (ConnectivityManager) cmgContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    public static void showSnackBar(final View mView, final String mMessage) {
//    Snackbar snackbar = Snackbar
//        .make(mView, mMessage, Snackbar.LENGTH_LONG);
//    snackbar.show();

        final Snackbar bar = Snackbar.make(mView, mMessage, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle user action
                        Snackbar snackbar1 = Snackbar.make(mView, mMessage, Snackbar.LENGTH_SHORT);
                        snackbar1.dismiss();
                    }
                });

        bar.show();
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Logger.logError(e.getMessage());
        }
    }

    public static void showToast(Context context, String text) {

    try {
      Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
    }

    public static String CurrentDateNTime() {
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(c.getTime());
    }

    public static String CurrentDateNTimeForSSO() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault());
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(c.getTime());
    }




    public static long CurrentDateNTimeForFeeds() {
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
//    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
//    df.setTimeZone(TimeZone.getTimeZone("UTC"));
//    return df.format(c.getTimeInMillis());
        return c.getTimeInMillis();
    }

    public static String getTimeDifference(String serverTime) {
//    Reference Link - http://stackoverflow.com/questions/25174921/time-ago-for-android-java
        String s = String
                .valueOf(DateUtils
                        .getRelativeTimeSpanString(getDateInMillis(serverTime),
                                CurrentDateNTimeForFeeds(), DateUtils.MINUTE_IN_MILLIS
                        ));
        if (s.contentEquals("0 minutes ago")) {
            s = "just now";
        } else {

            if (s.contains("days ago")) {
                s = s.replace("days ago", "Hari Lalu");
            }
            if (s.contains("day ago")) {
                s = s.replace("day ago", "Hari Lalu");
            }
            if (s.contains("hours ago")) {
                s = s.replace("hours ago", "Jam Lalu");
            }
            if (s.contains("hour ago")) {
                s = s.replace("hour ago", "Jam Lalu");
            }
            if (s.contains("minute ago")) {
                s = s.replace("minute ago", "Minit Lalu");
            }
            if (s.contains("minutes ago")) {
                s = s.replace("minutes ago", "Minit Lalu");
            }
        }
        return s;
//    try {
//      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//      format.setTimeZone(TimeZone.getTimeZone("UTC"));
//      Date past = format.parse(serverTime);
//
//      Calendar c = Calendar.getInstance();
////      c.setTimeZone(TimeZone.getTimeZone("UTC"));
//      SimpleDateFormat formatCurrent = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"
//      );
////      formatCurrent.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//      Date now = formatCurrent.parse(formatCurrent.format(c.getTime()));
//
////      int gmtOffset = TimeZone.getDefault().getRawOffset();
////      System.out.println(
////          TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
////      System.out.println(
////          TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//
//      Logger.logError("now.getTime()..." + now.getTime());
//
//      Logger.logError("past.getTime()..." + past.getTime());
//
//      long strHours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
//
//      Logger.logError("strHours..." + strHours);
//
//      long strDays = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//      Logger.logError("strDays..." + strDays);
////      long strMinutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
//
//      System.out
//          .println(TimeUnit.SECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//      System.out
//          .println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
//
//      if (strHours > 24) {
//        return strDays + " days ago";
//      } else {
////        if (strHours < 1) {
////          return strMinutes + " minutes ago";
////        } else {
//        return strHours + " hours ago";
////        }
//      }
//
//    } catch (Exception e) {
//      Logger.logError(e.getMessage());
//    }
//    return null;
    }

    private static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
        desiredFormat.setLenient(false);
//    desiredFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        long dateInMillis;
        try {
            Date date = (desiredFormat.parse(srcDate));
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException | java.text.ParseException e) {
            Logger.logError("Exception while parsing date. " + e.getMessage());
        }
        return 0;
    }

    public static void showAlertDialogWithTitle(final Context mContext) {
      /*  String msg = mContext.getString(R.string.text_error_not_logged_in);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(mContext, SplashNavigationScreen.class);
                        intent.putExtra("NOPERMISSIONS", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
        builder.setMessage(msg).setCancelable(true)
                .setNegativeButton(mContext.getResources().getString(R.string.alert_no), dialogClickListener)
                .setPositiveButton(mContext.getResources().getString(R.string.alert_yes), dialogClickListener)
                .show();*/
    }

    public static void showAlertDialogLoginToSave(final Context mContext) {
       /* String msg = mContext.getString(R.string.please_login_to_save_articles);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(mContext, SplashNavigationScreen.class);
                        intent.putExtra(IntentConstants.NO_PERMISSIONS, true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//
        builder.setMessage(msg).setCancelable(true)
                .setNegativeButton(mContext.getResources().getString(R.string.alert_no), dialogClickListener)
                .setPositiveButton(mContext.getResources().getString(R.string.alert_yes), dialogClickListener)
                .show();*/
    }

    public static void showAlertDialogForGuestUser(final Context mContext) {
        /*String msg = mContext.getString(R.string.text_error_not_logged_in);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(mContext, SplashNavigationScreen.class);
                        // intent.putExtra("NOPERMISSIONS", true);
                        intent.putExtra("FROM_GUEST_USER", true);
                        mContext.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage(msg).setCancelable(true)
                .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Yes", dialogClickListener).show();*/
    }

    public static void showAlertDialogWithTitleForFavoriteArticle(final Activity mContext) {
    /*    String msg = mContext.getString(R.string.text_article_favorite_dialogue);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
//            logoutFunctionality(mContext);
                        dialog.cancel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage(msg).setCancelable(true)
//        .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Ok", dialogClickListener).show();*/
    }

    public static void showAlertDialogCalendarActivity(final Context mContext) {
       /* String msg = mContext.getString(R.string.text_article_calendar_dialogue);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
//            logoutFunctionality(mContext);
                        dialog.cancel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage(msg).setCancelable(false)
//        .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Ok", dialogClickListener).show();*/
    }


    public static boolean isVersionCodeSupported() {

        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }


    public static void showAdsNativeInstallDFP() {

    }


    public static String getAppVersionCode(Context mActivity) {

        try {
            PackageInfo pInfo = mActivity.getPackageManager()
                    .getPackageInfo(mActivity.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return "";

        //return "1.6";

    }

//  public static byte[] encrypt(String plainText, String GEMPAK_RANDOM_DATA) throws Exception {
//    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
//    SecretKeySpec key = new SecretKeySpec(GEMPAK_RANDOM_DATA.getBytes("UTF-8"), "AES");
//    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
//    return cipher.doFinal(plainText.getBytes("UTF-8"));
//  }
//
//  public static String decrypt(byte[] cipherText, String GEMPAK_RANDOM_DATA) throws Exception{
//    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
//    SecretKeySpec key = new SecretKeySpec(GEMPAK_RANDOM_DATA.getBytes("UTF-8"), "AES");
//    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
//    return new String(cipher.doFinal(cipherText),"UTF-8");
//  }


    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
//      System.out.println("encrypted string: "
//          + Base64.encodeBase64String(encrypted));

            return android.util.Base64.encodeToString(encrypted, android.util.Base64.NO_WRAP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher
                    .doFinal(android.util.Base64.decode(encrypted, android.util.Base64.NO_WRAP));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//  public static void setColorToSwipeToRefreshProgressBar(SwipeRefreshLayout mSwipeRefreshLayout) {
//
//    // Configure the refreshing colors
//    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//        android.R.color.holo_green_light,
//        android.R.color.holo_orange_light,
//        android.R.color.holo_red_light);
//  }

    /**
     * Method to get the Device Unique Id ( in this case android id )
     *
     * @return android id
     */
    public static String getDeviceUniqueId(Context context) {

        // Method 1 : MAC Address
//        WifiManager wiFiManager = (WifiManager) context.getApplicationContext()
//                .getSystemService(Context.WIFI_SERVICE);
//        String macAddress = wiFiManager.getConnectionInfo().getMacAddress();
//        System.out.println("MAC " + macAddress);

        // Method 2 : Android Id
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("Android Id " + android_id);

        // Method 3 : IMEI Number
        //TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        //String imeiNumber = telephonyManager.getDeviceId();
        //System.out.println("IMEI "+imeiNumber);

        return android_id;

    }

    /**
     * Method to check if these permissions are granted or not
     *
     * @return whether the required permissions are granted or not
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
                "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    public static String convertPublishDateToIntendedFormat(String mFeedSectionDate) {

        String inputPattern = "dd/MM/yyyy HH:mm:ss.SSS";

//        //2016-09-14 11:08:09
//        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";

        String outputPattern = "yyyy-MM-dd   HH:mm:ss";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        // inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(mFeedSectionDate);
            str = outputFormat.format(date);
        } catch (ParseException | java.text.ParseException e) {
            Logger.logError("" + e.getMessage());
        }
        return str;
    }

    /**
     * Method to convert comma separated string into List
     *
     * @param commaString comma separated string
     * @return list
     */
    public static List<String> convertCommaSeparatedString(String commaString) {

        try {

            if (commaString != null) {
                return Arrays.asList(commaString.split("\\s*,\\s*"));
            } else {
                return null;
            }


        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Convert Date to Desired Format
     *
     * @param date string from server
     * @return formatted date string
     */
    public static String convertToDesiredFormat(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
            //Date serverDate = null;
            //try {
            Date serverDate = sdf.parse(date);
      /*} catch (Exception ex) {
        Logger.logError("convertToDesiredFormat.Error.." + ex.getMessage());
      }*/
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd   hh:mm aa", Locale.ENGLISH);
            String newFormatDateStr = formatter.format(serverDate);
            // Logger.log(".....Date..." + newFormatDateStr);
            return newFormatDateStr;
        } catch (Exception e) {
            Logger.logError("convertToDesiredFormat..ERROR " + e.getMessage());
        }
        return null;
    }


    public static String splitDateFromDesired(String inputDate) {
        try {

            String[] arr = inputDate.split(" ", 0);
            for (String w : arr) {
              //  System.out.println("Splitted Text :"+w);
            }
            return arr[0];
        } catch (Exception e) {
            Logger.logError("splitDateFromDesired..ERROR " + e.getMessage());
        }
        return null;
    }


    public static Bitmap CreateImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            //case "QR Code": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            case "Barcode": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);break;
            /*case "Data Matrix": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.DATA_MATRIX, size, size);break;
            case "PDF 417": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.PDF_417, size_width, size_height);break;
            case "Barcode-39":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_39, size_width, size_height);break;
            case "Barcode-93":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_93, size_width, size_height);break;
            case "AZTEC": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.AZTEC, size, size);break;*/
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap CreateBigImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            //case "QR Code": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            case "Barcode": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, big_size_width, big_size_height);break;
            /*case "Data Matrix": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.DATA_MATRIX, size, size);break;
            case "PDF 417": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.PDF_417, size_width, size_height);break;
            case "Barcode-39":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_39, size_width, size_height);break;
            case "Barcode-93":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_93, size_width, size_height);break;
            case "AZTEC": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.AZTEC, size, size);break;*/
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    public static boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static void SetSpinnerSelection(Spinner spinner, List list, String text) {

        if(list !=null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(text)) {
                    spinner.setSelection(i);
                }
            }
        }
    }

}

