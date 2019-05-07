package com.hrfid.acs.view.user_authentication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrfid.acs.R;
import com.hrfid.acs.operations.HFReaderOps;

//com.healthrfid.blood.control

/**
 * A simple {@link Fragment} subclass.
 */
public class BCUserLoginFragment extends Fragment {

    public OnBCUserLoginInterface onBCUserLoginInterface;
    // HF Reader Operation
    HFReaderOps hfReaderOps;
    // Handler for receiving tagDetails
    @SuppressLint("HandlerLeak")
    private Handler mHandlerReader = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            if (message.arg1 == 0) {
                // Get tag details object
                String tagId = message.getData().getString("hf_tag_id");
                String tagData = message.getData().getString("hf_tag_data");

                if (tagId != null && !tagId.isEmpty()) {
                    hfReaderOps.stop();
                    hfReaderOps.setRunning(true);

                    onBCUserLoginInterface.returnTagDetails(tagId, tagData);
                }
            }
        }
    };

    public BCUserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instantiate reader & pass handler as parameter
        hfReaderOps = new HFReaderOps(mHandlerReader);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bcuser_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Initialize HF Reader
        hfReaderOps.initHFReader();
//        // Start reading process
        hfReaderOps.read();

    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop HF reader
        hfReaderOps.stop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BCUserLoginFragment.OnBCUserLoginInterface) {
            onBCUserLoginInterface = (BCUserLoginFragment.OnBCUserLoginInterface) context;
        } else {

            try {
                throw new RuntimeException(context.toString()
                        + " must implement onBC2DInterface");
            } catch (Exception e) {
                // This will catch any exception, because they are all descended from Exception
                System.out.println("Error " + e.getMessage());
              //  return null;
            }


        }
    }

    // User Login Interface
    public interface OnBCUserLoginInterface {
        // Returns tagDetails
        void returnTagDetails(String tagId, String tagData);
    }

}
