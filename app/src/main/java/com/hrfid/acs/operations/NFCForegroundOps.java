package com.hrfid.acs.operations;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcV;

/**
 * Created by joart.cano on 11/12/15.
 */
public class NFCForegroundOps {

    private NfcAdapter mNfcAdapter;

    private Activity mActivity;
    private IntentFilter intentFiltersArray[];
    private PendingIntent intent;
    private String techListsArray[][];

    public NFCForegroundOps(Activity activity) {
        super();
        this.mActivity = activity;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(activity.getApplicationContext());

        // the activity will not be launched if it is already running at the top of the history stack
        intent = PendingIntent.getActivity(activity, 0, new Intent(activity,
                activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType("*/*");
        }
        catch(IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Unable to specify */* Mime Type", e);
        }
        intentFiltersArray = new IntentFilter[] { ndef };

        techListsArray = new String[][] { new String[] { NfcV.class.getName() } };
    }

    /* convert a byte array to a hex string */
    public String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for(int i=data.length-1; i>=0; i--) {
            buf.append(byteToHex(data[i]).toUpperCase());
        }
        return (buf.toString());
    }

    /* convert a byte to a hex string */
    public String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >>> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));
        return buf.toString();
    }

    /* convert an int to a hex char */
    public char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }

    public void enableForeground() {
        mNfcAdapter.enableForegroundDispatch(mActivity, intent, intentFiltersArray, techListsArray);
    }

    public void disableForeground() {
        mNfcAdapter.disableForegroundDispatch(mActivity);
    }

    public NfcAdapter getNfc() {
        return mNfcAdapter;
    }


}
