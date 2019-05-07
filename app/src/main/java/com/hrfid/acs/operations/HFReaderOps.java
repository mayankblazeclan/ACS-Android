package com.hrfid.acs.operations;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.util.FragmentUtils;
import com.rscja.deviceapi.RFIDWithISO15693;
import com.rscja.deviceapi.entity.ISO15693Entity;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.rscja.deviceapi.exception.RFIDReadFailureException;

import java.util.ArrayList;

//import au.com.healthrfid.pathologyapp.utils.Constants;
//import au.com.healthrfid.pathologyapp.utils.Utils;

/**
 * Created by brentmercader on 10/05/2017.
 * High Frequency Reader Operation
 * HF Reader for High Frequency tags
 * <p>
 * 1. Instantiate Reader
 * 2. Initialize Reader
 * 3. Read tag details
 * 4. Create handler as parameter to receive tag details
 */

public class HFReaderOps {

    private static final String TAG = HFReaderOps.class.getSimpleName();
    private final static long READING_INTERVAL = 500;
    // HF Reader instance
    private RFIDWithISO15693 hfReader;
    // Tag Details Object
    private ISO15693Entity tagDetails;
    // Handler for sending the tagDetails
    private Handler readerHandler;
    // boolean for thread
    private boolean isRunning;

    // Handler parameter for receiving the tagDetails
    public HFReaderOps(Handler readerHandler) {

        try {
            hfReader = RFIDWithISO15693.getInstance();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        this.readerHandler = readerHandler;

        isRunning = true;

    }

    // Initialize HF Reader
    public void initHFReader() {
        new InitTask().execute();
    }

    // Start reading process
    public void read() {
        // Thread for looping reading process
        Thread readerThread = new Thread(new ReaderRunnable(readerHandler));
        readerThread.start();
        isRunning = true;
    }

    // Stop reading process / thread
    public void stop() {
        isRunning = false;
        hfReader.free();
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }


    // Inner class Runnable for running the loop reading process on background
    private class ReaderRunnable implements Runnable {

        Handler readerHandler;

        private ReaderRunnable(Handler readerHandler) {
            this.readerHandler = readerHandler;
        }

        @Override
        public void run() {
            // Run the reader of tag
            while (isRunning) {

                ReaderTask readerTask = new ReaderTask(readerHandler);
                readerTask.execute();

                try {
                    Thread.sleep(READING_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ReaderTask extends AsyncTask<String, Integer, Boolean> {

        Handler readerHandler;
        Message message;
        ArrayList<ISO15693Entity> tagDataEntities;

        boolean isReadingComplete;

        private ReaderTask(Handler readerHandler) {
            this.readerHandler = readerHandler;
            isReadingComplete = true;
            // Instantiate array list
            tagDataEntities = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                // Gets 5 hex blocks of tag data
                for (int i = 0; i < 5; i++) {
                    ISO15693Entity iso15693Entity = hfReader.read(i);

                    if (iso15693Entity == null) {
                        isReadingComplete = false;
                        break;
                    }
                    // Adds tag data entity to the array list
                    tagDataEntities.add(iso15693Entity);
                }

            } catch (RFIDReadFailureException e) {
                Log.d(TAG, e.toString());
                isReadingComplete = false;
            } catch (RuntimeException e) {
                Log.d(TAG, e.toString());
                isReadingComplete = false;
            }

            return isReadingComplete;
        }

        @Override
        protected void onPostExecute(Boolean isReadingComplete) {
            if (isReadingComplete) {
                String tagDataAscii = "";
                Bundle bundle = new Bundle();
                message = new Message();

                // Concat all tag data blocks
                for (ISO15693Entity entity : tagDataEntities) {
                    if (entity != null && !entity.getData().equals(Constants.EMPTY_BLOCK)) {
                        tagDataAscii += entity.getData();
                        Log.d("DEBUG", "tagData : " + entity.getData());
                    }
                }

                // Get Tag Id
                String tagId = tagDataEntities.get(0).getId();
                // Remove empty hex
                tagDataAscii = tagDataAscii.replace("00", "");
                // Convert hex tag data to ascii
                tagDataAscii = FragmentUtils.hexToAscii(tagDataAscii);

                if (FragmentUtils.validateRandomTagId(tagId)) {
                    // Stored in Bundle
                    bundle.putString("hf_tag_id", tagId);
                    bundle.putString("hf_tag_data", tagDataAscii);

                    // Set the data in the message
                    message.setData(bundle);
                    // send message to handler param
                    readerHandler.sendMessage(message);
                }
            }
        }
    }

    // Inner class for initializing reader in background
    private class InitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
           return hfReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (!result) Log.e("ERR", "Failed initializing reader");
        }
    }

}
