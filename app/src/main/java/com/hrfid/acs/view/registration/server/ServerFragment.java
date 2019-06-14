package com.hrfid.acs.view.registration.server;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.components.FontAwesomeIcons;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.view.dialog.AlertDialogFragment;
import com.hrfid.acs.view.dialog.AlertDialogInterface;


public class ServerFragment extends Fragment implements ServerTasks.View, AlertDialogInterface {
    private final String TAG = getClass().getSimpleName();

    private OnFragmentListener mListener;

    private ServerTasks.Presenter mPresenter;
    private DialogFragment mDialog;

    public ServerFragment() {
        // Required empty public constructor
    }

    public static ServerFragment newInstance() {
        return new ServerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_server, container, false);

        TextView mSectionTitleTv = (TextView) root.findViewById(R.id.tv_section_title);
        mSectionTitleTv.setText(R.string.section_title_select_server);

        final Spinner mServerSpn = (Spinner) root.findViewById(R.id.dropdown_server_spn);

        FontAwesomeIcons mCheckConnectionFa = (FontAwesomeIcons) root.findViewById(R.id.test_connection_fa);
       // mCheckConnectionFa.setOnClickListener(___ -> mPresenter.checkServer(mServerSpn.getSelectedItemId(), false));
        mCheckConnectionFa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.checkServer(mServerSpn.getSelectedItemId(), false);
            }
        });

        Button mNextBtn = (Button) root.findViewById(R.id.next_btn);
        //mNextBtn.setOnClickListener(___ -> mPresenter.checkServer(mServerSpn.getSelectedItemId(), true));
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.checkServer(mServerSpn.getSelectedItemId(), true);
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            mListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentListener {
        void serverNext(int activity);
    }

    @Override
    public void setPresenter(ServerTasks.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void next() {
        // NEXT activity
        if (null != mListener) {
//        mListener.serverNext(Constants.MAIN_ACTIVITY);
            mListener.serverNext(Constants.USER_AUTH_ACTIVITY);
        } else {
            LoggerLocal.error(TAG, "invoke interface ServerFragment");
        }
    }

    @Override
    public void showDialog(String activity) {
        if (activity.equalsIgnoreCase(Constants.DLG_INTERNET_CONNECTION)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.internet_connection_title, R.string.ic_not_connection_message, R.string.dlg_ok_text, 0);
        } else if (activity.equalsIgnoreCase(Constants.DLG_SERVER_UNAVAILABLE)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.server_title, R.string.cnt_conn_server_msg, R.string.dlg_ok_text, 0);
        } else {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.req_service_title, R.string.req_service_error_on, R.string.dlg_ok_text, 0);
        }

        mDialog.setTargetFragment(this, 0);
        mDialog.setCancelable(false);
        mDialog.show(getFragmentManager(), activity);
    }

    @Override
    public void doPositiveClick() {
        mDialog.dismiss();
    }

    @Override
    public void doNegativeClick() {
        mDialog.dismiss();
    }
}
