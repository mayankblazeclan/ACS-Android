package com.hrfid.acs.view.user_authentication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hrfid.acs.R;

public class UserAuthFragment extends Fragment implements UserAuthTask.View {
    private OnFragmentInteractionListener mListener;
    private UserAuthTask.Presenter mPresenter;

    public UserAuthFragment() {
        // Required empty public constructor
    }

    public static UserAuthFragment newInstance() {
        UserAuthFragment fragment = new UserAuthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_auth, container, false);
        mPresenter.verifyUser();
        Button scanbtn = (Button)root.findViewById(R.id.button);
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.userLogin();
            }
        });


        return root;
    }

    @Override
    public void setPresenter(UserAuthTask.Presenter presenter) {
        this.mPresenter = presenter;
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

    public interface OnFragmentInteractionListener {
        void userLogin();
    }

    @Override
    public void nextActivity(int activity) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mListener.userLogin();
            }
        }, 5000);
    }
}
