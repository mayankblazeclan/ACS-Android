package com.hrfid.acs.view.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.SubjectList;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MS on 2019-05-31.
 */
public class KitDetailsAdapter extends RecyclerView.Adapter<KitDetailsAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    //ArrayList personNames;
    String[] status = { "APPROVED", "REJECTED", "IN_QUEUE"};

    Context context;

    String[] spnBloodGroup = {"O+","B-","B+", "A+", "A-"};

    String[] spnStudyID = {"10012","10011","10010", "10015", "10016"};

    String[] spnReason = {"DAMAGE", "MISSING"};

    String[] spnGroup = {"G1","G2","G3", "G4", "G5"};

    List<SubjectList> studyLists;
    private  List<Integer> lists;
    private  List<Integer> listSpinnerStudyID;

    public KitDetailsAdapter(Context context, List<SubjectList> studyLists, List<Integer> lists) {
        this.context = context;
        this.studyLists = studyLists;
        this.lists = lists;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_kit_details_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        // holder.name.setText(" "+ personNames.get(position));
        // implement setOnClickListener event on item view.
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, ""+ personNames.get(position), Toast.LENGTH_SHORT).show();

                showModifyDialog();
            }
        });*/

     /* if( position ==1 || position ==4 || position ==7) {

          holder.txtStatus.setText("APPROVED");
          holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));

      }else if( position ==2 || position ==5 || position ==8) {

          holder.txtStatus.setText("REJECTED");
          holder.txtStatus.setTextColor(Color.RED);
      }else {

          holder.txtStatus.setText("IN_QUEUE");
          holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
      }*/


        /*if(studyLists != null) {

            holder.txtScreenId.setText(studyLists.get(position).getScreenId().trim());
            holder.txtDob.setText
                    (Utilities.splitDateFromDesired(studyLists.get(position).getDob()));
            holder.txtGroup.setText("" + studyLists.get(position).getGroupId());
            holder.txtGender.setText("" + studyLists.get(position).getGender());
            holder.txtStudyId.setText("" + studyLists.get(position).getStudyId());
            if (studyLists.get(position).getStatus().equalsIgnoreCase("ACTIVE")) {
                holder.txtStatus.setText("ACTIVE");
                holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));
            } else if (studyLists.get(position).getStatus().equalsIgnoreCase("INACTIVE")) {
                holder.txtStatus.setText("INACTIVE");
                holder.txtStatus.setTextColor(Color.RED);
            } else {
                holder.txtStatus.setText("IN_QUEUE");
                holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
            }

        }*/

        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showModifyDialog();

                showModifyDialog(studyLists.get(position), lists);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteDialog();
            }
        });
    }




    @Override
    public int getItemCount() {
        return studyLists.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtScreenId;
        TextView txtGroup;
        TextView txtDob;
        TextView txtStatus;
        Button btnModify, btnDelete;
        TextView txtGender;
        TextView txtStudyId;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtScreenId = (TextView) itemView.findViewById(R.id.txtScreenId);
            btnModify = (Button) itemView.findViewById(R.id.btnModify);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtGroup = (TextView) itemView.findViewById(R.id.txtGroup);
            txtDob = itemView.findViewById(R.id.txtDob);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtStudyId = itemView.findViewById(R.id.txtStudyId);
        }
    }

    private void showModifyDialog(final SubjectList studyList, List<Integer> lists) {

        final ImageButton txt_dob;
        final TextView txtStartDate, txtDob;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];
        final EditText edtScreenId;

        listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(studyList.getStudyId());


        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });


        ImageView btnCancel = (ImageView) dialog.findViewById(R.id.btnCancel);
        // if decline button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });



        Button btnReturn = (Button) dialog.findViewById(R.id.btnReturn);
        // if decline button is clicked, close the custom dialog
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                showReturnDialog();
            }
        });
    }


    private void showDeleteDialog() {

        final ImageButton txt_dob;
        final TextView txtStartDate, txtDob;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];
        final EditText edtScreenId;

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_resaon_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        // if decline button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
    }

    private void showReturnDialog() {

        final ImageButton txt_dob;
        final TextView txtStartDate, txtDob;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];
        final EditText edtScreenId;
        final Spinner spnResaon;

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_return_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        spnResaon = (Spinner) dialog.findViewById(R.id.spnResaon);
        spnResaon.setOnItemSelectedListener(this);

        ArrayAdapter adpNumber = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnReason);
        adpNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnResaon.setAdapter(adpNumber);

        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        // if decline button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
    }

}

