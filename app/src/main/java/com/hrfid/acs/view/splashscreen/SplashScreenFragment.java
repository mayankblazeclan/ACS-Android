package com.hrfid.acs.view.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.dialog.AlertDialogFragment;
import com.hrfid.acs.view.dialog.AlertDialogInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreenFragment extends Fragment implements SplashScreenTasks.View, AlertDialogInterface {
    private final String TAG = getClass().getSimpleName();

    private SplashScreenTasks.Presenter mPresenter;

    Animation uptodown, downtoup, fadeIn;
    ProgressBar pb_custom_color;
    LinearLayout bg1, bg2, bg3, logoContainer;
    final Handler handler = new Handler();
    final Handler handlerLogin = new Handler();

    private DialogFragment mDialog;
    private int mTask;
    private SplashScreenInterface mListener;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    public static SplashScreenFragment newInstance() {
        return new SplashScreenFragment();
    }

    public interface SplashScreenInterface {
        void nextActivity(int nextActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        bg1 = (LinearLayout) root.findViewById(R.id.bg1);
        downtoup = AnimationUtils.loadAnimation(root.getContext(), R.anim.downtoup);
        bg1.setAnimation(downtoup);

        bg2 = (LinearLayout) root.findViewById(R.id.bg2);
        uptodown = AnimationUtils.loadAnimation(root.getContext(), R.anim.uptodown);
        bg2.setAnimation(uptodown);

        bg3 = (LinearLayout) root.findViewById(R.id.bg3);

        logoContainer = (LinearLayout) root.findViewById(R.id.logo);
        pb_custom_color = (ProgressBar) root.findViewById(R.id.pb_custom_color);
        fadeIn = AnimationUtils.loadAnimation(root.getContext(), R.anim.fadein);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bg3.setVisibility(View.VISIBLE);
            }
        }, 800);

        handlerLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                logoContainer.setVisibility(View.VISIBLE);
                pb_custom_color.setVisibility(View.VISIBLE);
                logoContainer.setAnimation(fadeIn);
                pb_custom_color.setAnimation(fadeIn);

                mPresenter.loadProcess(Constants.CHECK_CONN_TASK);
            }
        }, 1000);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SplashScreenInterface) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "Error : " + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(SplashScreenTasks.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showDialog(String activity, int task) {
        mTask = task;

        if (activity.equalsIgnoreCase(Constants.DLG_INTERNET_CONNECTION)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.internet_connection_title, R.string.ic_not_connection_message, R.string.dlg_ok_text, 0);
        } else if (activity.equalsIgnoreCase(Constants.DLG_SERVER_UNAVAILABLE)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.server_title, R.string.cnt_conn_server_msg, R.string.dlg_ok_text, 0);
        } else if (activity.equalsIgnoreCase(Constants.DLG_VERSION_UPGRADE)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.update_req_title, R.string.update_req_msg, R.string.dlg_ok_text, 0);
        } else if (activity.equalsIgnoreCase(Constants.DLG_DEV_NOT_REGISTERED)) {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.not_registration_title, R.string.not_registration_msg, R.string.dlg_yes_text, R.string.dlg_no_text);
        } else {
            mDialog = AlertDialogFragment.newInstance(activity, R.string.req_service_title, R.string.req_service_error_on, R.string.dlg_ok_text, 0);
        }

        mDialog.setTargetFragment(this, 0);
        mDialog.setCancelable(false);
        mDialog.show(getFragmentManager(), activity);
    }

    @Override
    public void doPositiveClick() {
        if (mDialog != null && mDialog.getTag() != null) {
            if (mDialog.getTag().equalsIgnoreCase(Constants.DLG_DEV_NOT_REGISTERED)) {
                mListener.nextActivity(Constants.DEVICE_REGISTRATION_ACTIVITY);
                //TODO Check Camera permission and storage permission
               /* if (checkForPermissionAndRequest(getContext(), getActivity())) {
                    mListener.nextActivity(Constants.DEVICE_REGISTRATION_ACTIVITY);
                }*/
            } else {
                mDialog.dismiss();
                mPresenter.loadProcess(mTask);
            }
        }
    }


    @Override
    public void doNegativeClick() {
        if (mDialog != null && mDialog.getTag() != null) {
            if (mDialog.getTag().equalsIgnoreCase(Constants.DLG_DEV_NOT_REGISTERED)) {
                mListener.nextActivity(Constants.CLOSE_ACTIVITY);
            } else {
                mDialog.dismiss();
                mPresenter.loadProcess(mTask);
            }
        }
    }

    @Override
    public void nextActivity(int activity) {
        mListener.nextActivity(activity);
    }

    //Permission check

    //app has all permission proceed ahead
    private boolean checkForPermissionAndRequest(Context context, Activity activity) {

        List<String> listPermissionNeeded = new ArrayList<>();

        for (String perm : Constants.appPermission) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(perm);
            }
        }

        //Ask For Non Granted Permission

        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), Constants.PERMISSION_REQUEST_CODE);
            return false;
        } else {
            LoggerLocal.error(TAG, "All Permission Granted");

        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResult = new HashMap<>();
            int deniedCount = 0;

            //Gather Permission Grant Result

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResult.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0) {
                //Proceed ahead with App
                Toast.makeText(getContext(), "All Permissions are Granted", Toast.LENGTH_LONG).show();

                mListener.nextActivity(Constants.REGISTRATION_ACTIVITY);

                //startActivity(new Intent(getContext(), MainActivity.class));
            }
            // Atleast one or all permission denied

            else {
                for (Map.Entry<String, Integer> entry : permissionResult.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    //permission is denied this is the first time, when 'never ask again is not checked'
                    //so ask again explain the usage of permission
                    // shouldShowRequestPermissionRational will return true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permName)) {
                        //show dialog with explanation
                        Utils.showDialog(getContext(), "", "This app needs Camera and Storage permission to scan barcode",
                                "Yes, Grant permission",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        checkForPermissionAndRequest(getContext(), getActivity());
                                    }
                                },
                                "No Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        getActivity().finish();
                                    }
                                }
                                , false);
                    } else {
                        Utils.showDialog(getContext(), "",
                                "You have denied some permissions, Allow all permissions at [Setting] > [Permissions]",
                                "Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getActivity().getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                },

                                "No, Exit app",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        getActivity().finish();
                                    }
                                }
                                , false);
                        break;
                    }

                }
            }
        }//
    }
}
