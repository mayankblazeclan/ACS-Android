package com.hrfid.acs.util;

import android.util.Log;

public class LoggerLocal {
    public static void error(String tag, String msg)
    {
        Log.e(tag,msg);
    }
    public static void log(String tag, String msg)
    {
        Log.d(tag,msg);
    }
}
