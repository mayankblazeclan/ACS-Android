package com.hrfid.acs.operations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zebra.adc.decoder.Barcode2DWithSoft;

/**
 * Created by brentmercader on 05/05/2017.
 * Operation for the Barcode Reader
 */


public class BarcodeOps {

    private static final String TAG = BarcodeOps.class.getSimpleName();

    private Barcode2DWithSoft barcode2DWithSoft;
    private Context context;
    private BarcodeResusltOps mListener;


    // Barcode Operation constructor
    public BarcodeOps(Context context) {

        this.context = context;
        try {
            barcode2DWithSoft = Barcode2DWithSoft.getInstance();
            mListener = (BarcodeResusltOps) context;
        } catch (Throwable e) {
            Log.d(TAG, " This device doesn't support barcode reader");
        }
    }

    // Intialize the barcode reader
    public void initBarcode() {
        if (barcode2DWithSoft != null) {
            new InitTask().execute();
        } else {
            Log.d(TAG, " Failed to initialize barcode reader");
        }
    }

    // Start the scan process
    public void scan() {
        if (barcode2DWithSoft != null) {
            barcode2DWithSoft.scan();
        }
        else
        {
            Log.e(TAG,"Not able to scan ~~~~~~");
        }

    }

    // close the barcode reader
    public void close() {
        if (barcode2DWithSoft != null) barcode2DWithSoft.close();
    }

    // Stop the scan process
    public void stopScan() {
        if (barcode2DWithSoft != null) barcode2DWithSoft.stopScan();
    }

    // Sets the barcode reader's scan callback
    public void setScanCallback(Barcode2DWithSoft.ScanCallback scanCallback) {
        if (barcode2DWithSoft != null) barcode2DWithSoft.setScanCallback(scanCallback);
    }

    public void setmListener(BarcodeResusltOps mListener) {
        this.mListener = mListener;
    }

    // Class for executing the initialization of barcode reader
    @SuppressLint("StaticFieldLeak")
    private class InitTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;
            try {
                if (barcode2DWithSoft != null) {
                    result = barcode2DWithSoft.open(context);
                    if (result) {
                        mListener.barcodeReady(result);
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
