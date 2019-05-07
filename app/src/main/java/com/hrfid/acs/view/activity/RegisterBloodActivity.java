package com.hrfid.acs.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.FontAwesomeIcons;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.operations.BarcodeOps;
import com.hrfid.acs.operations.BarcodeResusltOps;
import com.hrfid.acs.service.api.componentdetails.GetComponentDetails;
import com.hrfid.acs.service.api.specialtestingcode.SpecialTestingCodeApi;
import com.hrfid.acs.service.api.verifydonation.GetVerifyDonationId;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.util.TagIdInputOps;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/*public class RegisterBloodActivity extends TagIdInputOps implements
        //RegisterBloodTasks.View,
        ScanBloodFragment.OnFragmentInteractionListener,
        BloodDataFragment.OnFragmentInteractionListener,
        TagHRFIDFragment.OnFragmentInteractionListener,
        BarcodeResusltOps,
        Barcode2DWithSoft.ScanCallback,
        UserAuthInterface,
        ScanBloodFragment.OnBCUserLoginInterface,
        GetVerifyDonationId.DonationIdAPIInterface,
        GetComponentDetails.ComponentDetailsAPIInterface,
        SpecialTestingCodeApi.SpecialTestingCodeApiInterface,
        BC2DScanFragment.OnBC2DInterface,
        View.OnClickListener,
        AlertDialogInterface
//        BC2DScanDialogFragment.OnBC2DDialogInterface
{*/


