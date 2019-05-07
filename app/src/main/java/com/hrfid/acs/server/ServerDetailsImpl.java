package com.hrfid.acs.server;

import android.util.Log;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.interfaces.ServerDetails;


/**
 * Created by a.cabayao on 6/2/15.
 */
public class ServerDetailsImpl implements ServerDetails {
    @Override
    public boolean isLowerVersion(String currentVersion) {
        Log.v("", "current version = " + currentVersion);
        int  result = compareVersions(Constants.SERVER_BASE_VERSION, currentVersion);

        return result > 1;

    }

    /**
     * This method will compare two version strings
     * @author joart.cano
     * @param version1
     * @param version2
     * @return Integer
     */
    public int compareVersions(String version1, String version2) {
        String[] levels1 = version1.split("\\.");
        String[] levels2 = version2.split("\\.");

        int length = Math.max(levels1.length, levels2.length);
        for(int i = 0; i< length; i++){
            Integer v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
            Integer v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
            int compare = v1.compareTo(v2);
            if(compare!=0){
                return compare;
            }
        }
        return 0;
    }

}
