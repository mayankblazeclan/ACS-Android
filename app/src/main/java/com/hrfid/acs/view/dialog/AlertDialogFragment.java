package com.hrfid.acs.view.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hrfid.acs.R;

public class AlertDialogFragment extends DialogFragment {

    private final static String ACTIVITY_TYPE = "activity_type";
    private final static String POSITIVE_BTN = "positive_btn";
    private final static String NEGATIVE_BTN = "negative_btn";
    private final static String MESSAGE_DLG = "message_dlg";
    private final static String TITLE_DLG = "title_dlg";

    private AlertDialog mAlertDialog;

    // Alert Dialog Interface
    private AlertDialogInterface callback;

    public static AlertDialogFragment newInstance(String activityType, int title, int message, int postiveBtn, int negativeBtn) {
        AlertDialogFragment frag = new AlertDialogFragment();

        Bundle args = new Bundle();

        args.putString(ACTIVITY_TYPE, activityType);
        args.putInt(TITLE_DLG, title);
        args.putInt(MESSAGE_DLG, message);
        args.putInt(POSITIVE_BTN, postiveBtn);
        args.putInt(NEGATIVE_BTN, negativeBtn);

        frag.setArguments(args);
        return frag;
    }
    public static AlertDialogFragment newInstance(int title, int message, boolean hideNoButton) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("activityType", "");
        args.putInt("title", title);
        args.putInt("message", message);
        args.putBoolean("hideNoButton", hideNoButton);
        args.putInt("positiveBtn", android.R.string.ok);
        args.putInt("negativeBtn", android.R.string.cancel);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Fragment Target Fragment
        callback = (AlertDialogInterface) getTargetFragment();
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();
        builder = errorAlertDialog(builder, bundle);

        mAlertDialog = builder.create();
        return mAlertDialog;
    }


    private AlertDialog.Builder errorAlertDialog(AlertDialog.Builder builder, Bundle bundle) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_error_ok, null);

        if (bundle.getInt(POSITIVE_BTN) != 0) {
            final Button mBtnPositive = dialogView.findViewById(R.id.positive_btn);
            mBtnPositive.setText(bundle.getInt(POSITIVE_BTN));
            mBtnPositive.setVisibility(View.VISIBLE);
            mBtnPositive.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback = (AlertDialogInterface) getTargetFragment();
                    callback.doPositiveClick();
                }
            });
        }

        if (bundle.getInt(NEGATIVE_BTN) != 0) {
            final Button mBtnNegative = dialogView.findViewById(R.id.negative_btn);
            mBtnNegative.setText(bundle.getInt(NEGATIVE_BTN));
            mBtnNegative.setVisibility(View.VISIBLE);
            mBtnNegative.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback = (AlertDialogInterface) getTargetFragment();
                    callback.doNegativeClick();
                }
            });
        }

        final TextView mTvTitle = dialogView.findViewById(R.id.title_tv);
        mTvTitle.setText(bundle.getInt(TITLE_DLG));

        final TextView mTvMsg = dialogView.findViewById(R.id.messsage_tv);
        mTvMsg.setText(bundle.getInt(MESSAGE_DLG));

        builder.setView(dialogView);
        return builder;
    }
}
