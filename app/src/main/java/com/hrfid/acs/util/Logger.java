package com.hrfid.acs.util;

import android.util.Log;

/**
 * Created by Akshay Kalpe on 5/27/16.
 */
public class Logger {

  public static void log(String cmgString) {
    System.out.println("******************");

        if (cmgString != null) {
            System.out.println(cmgString);
        } else {
            System.out.println("Data cmg null");
        }

    System.out.println("******************");
  }

  public static void logError(String cmgString) {
    System.out.println("******************");

        if (cmgString != null) {
            Log.e("LOGGER", cmgString);
        } else {
            Log.e("LOGGER", "Data cmg null");
        }
    System.out.println("******************");
  }
}
