package com.hrfid.acs.view.user_authentication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;


//import au.com.healthrfid.pathologyapp.R;

//import au.com.healthrfid.pathologyapp.utils.Constants;

/**
 * Created by brentmercader on 05/05/2017.
 */

public class BC2DScanFragment extends Fragment {

    private TextView mTvScanItem;
    private TextView mTvHeader;
    private ImageView mImgBarcode;

    private OnBC2DInterface onBC2DInterface;
    private String mScanItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScanItem = getArguments().getString("scan_item");

        View rootView = inflater.inflate(R.layout.fragment_bcscan, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvScanItem = (TextView) view.findViewById(R.id.scan_item_textview);
        mTvHeader = (TextView) view.findViewById(R.id.header_text_textview);
        mImgBarcode = (ImageView) view.findViewById(R.id.barcode_img);

        mImgBarcode.setVisibility(View.VISIBLE);
        mTvHeader.setTextColor(Color.BLACK);

        if (mScanItem.equalsIgnoreCase(Constants.SCAN_MR_NUM)) {
//            mTvScanItem.setText(R.string.string_scan_patient);
//            mTvHeader.setText(R.string.string_verify_patient);

            mImgBarcode.setImageResource(R.drawable.scan_patient_wristband_high);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_CONFIRM_UR_CODE)) {
//            mTvScanItem.setText(R.string.string_scan_patient_complete);
//            mTvScanItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            mTvScanItem.setPadding(70, 20, 50, 20);
//            mTvHeader.setText(R.string.string_confirm_patient);

            mImgBarcode.setImageResource(R.drawable.scan_patient_wristband_high);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_ADM_NUM)) {
//            mTvScanItem.setText(R.string.string_scan_adm);
//            mTvHeader.setText(R.string.string_verify_patient);
//            mTvScanItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            mTvScanItem.setPadding(90, 15, 90, 20);

            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        }

        //        RK
       /* else if (mScanItem.equalsIgnoreCase(Constants.SCAN_DONATION_ID)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Donation ID Barcode");
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_BLOOD_GROUP_CODE)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Blood Group Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_BLOOD_GROUP_CODE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_BLOOD_GROUP_NAME)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Blood Group Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_BLOOD_GROUP_CODE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        }  else if (mScanItem.equalsIgnoreCase(Constants.SCAN_COMPONENT_CODE)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Component Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_COMPONENT_CODE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        }  else if (mScanItem.equalsIgnoreCase(Constants.SCAN_COMPONENT_NAME)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Component Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_COMPONENT_CODE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_EXPIRY_DATE)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Expiry Date and Time Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_EXPIRY_DATE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        }  else if (mScanItem.equalsIgnoreCase(Constants.SCAN_SPECIAL_TESTING_CODE)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Special Testing Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_SPECIAL_TESTING_CODE);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        } else if (mScanItem.equalsIgnoreCase(Constants.SCAN_SPECIAL_TESTING_NAME)) {
            ((RegisterBloodActivity) getActivity()).setTvPageTitle("Scan Special Testing Barcode");
            ((RegisterBloodActivity) getActivity()).setScanItem(Constants.SCAN_SPECIAL_TESTING_NAME);
            mImgBarcode.setImageResource(R.drawable.scan_donation_id_small);
        } else {
            mTvScanItem.setText(R.string.string_scan_urcode);
            mTvHeader.setText(R.string.string_verify_urcode);
        }
*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BC2DScanFragment.OnBC2DInterface) {
            onBC2DInterface = (BC2DScanFragment.OnBC2DInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onBC2DInterface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBC2DInterface = null;
    }

    // Interface for returning barcode result
    public interface OnBC2DInterface {
        void returnBarcodeResult(String mScanItem, String barcodeResult);
        void returnScanItem(String mScanItem);
    }

}
