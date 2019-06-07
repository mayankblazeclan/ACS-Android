/*
package com.hrfid.acs.view.user_authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.zxing.Result;
import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//import au.com.healthrfid.pathologyapp.R;
//import au.com.healthrfid.pathologyapp.utils.Constants;

*/
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BCScanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *//*

public class BCScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private final String TAG = getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;

    private ZXingScannerView mScannerView;

    private TextView mTvScanItem;
    private TextView mTvHeader;

    private String mScanItem = "";

    public BCScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScanItem = getArguments().getString("scan_item");
        mScannerView = new ZXingScannerView(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_bcscan, container, false);
        ViewGroup contentFrame = (ViewGroup) rootView.findViewById(R.id.content_frame);


        contentFrame.addView(mScannerView);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvScanItem = (TextView) view.findViewById(R.id.scan_item_textview);
        mTvHeader = (TextView)view.findViewById(R.id.header_text_textview);

        if (mScanItem.equalsIgnoreCase(Constants.SCAN_MR_NUM)) {
            mTvScanItem.setText("SCAN REQUEST NUMBER");
            mTvHeader.setText("REQUEST SLIP VERIFICATION");
        }
        else if (mScanItem.equalsIgnoreCase(Constants.SCAN_BED_NUMBER)) {
            mTvScanItem.setText("SCAN THE BED NUMBER");
            mTvHeader.setText("RECORD BED NUMBER");
        }
        else if (mScanItem.equalsIgnoreCase(Constants.SCAN_CONFIRM_UR_CODE)) {
            mTvScanItem.setText("SCAN PATIENT UR CODE TO COMPLETE COLLECTION");
            mTvScanItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mTvScanItem.setPadding(2, 10, 2, 10);
            mTvHeader.setText("PATIENT CONFIRMATION");
        }
        else {
            mTvScanItem.setText("SCAN PATIENT UR CODE");
            mTvHeader.setText("PATIENT VERIFICATION");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void handleResult(Result result) {
        mListener.returnScanValue(mScanItem, result.getText());
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public interface OnFragmentInteractionListener {
        void returnScanValue(String scanItem, String scanValue);
    }


}
*/
