package com.hrfid.acs.view.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.StudyList;
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

    String[] spnGender = {"Male","Female","Other"};

    String[] spnGroup = {"G1","G2","G3", "G4", "G5"};

    List<StudyList> studyLists;
    private  List<Integer> lists;
    private  List<Integer> listSpinnerStudyID;

    public KitDetailsAdapter(Context context, List<StudyList> studyLists, List<Integer> lists) {
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

    private void showModifyDialog(final StudyList studyList, List<Integer> lists) {

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
        dialog.setContentView(R.layout.dialog_subject_modify);


        //Adding value for ScreenID

        edtScreenId = dialog.findViewById(R.id.edtScreenId);
        edtScreenId.setText(studyList.getScreenId());

  /*      //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spnBloodGroups = (Spinner) dialog.findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);*/

        Spinner spnStudyIDs = (Spinner) dialog.findViewById(R.id.spnStudyId);
        spnStudyIDs.setOnItemSelectedListener(this);

        Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
        spnGroups.setOnItemSelectedListener(this);

        Spinner spnPersonGender = (Spinner) dialog.findViewById(R.id.spnPersonGender);
        spnPersonGender.setOnItemSelectedListener(this);

        Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatusId);
        spnStatus.setOnItemSelectedListener(this);


        if(studyList.getStatus().equalsIgnoreCase("ACTIVE")){
            status = new String[]{"ACTIVE", "INACTIVE", "IN_QUEUE"};
        }else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "ACTIVE", "IN_QUEUE"};
        }else if(studyList.getStatus().equalsIgnoreCase("INQUEUE")){
            status = new String[]{"IN_QUEUE", "INACTIVE", "ACTIVE"};
        }else {

        }

        if(listSpinnerStudyID.get(0).equals(status)){

            Logger.log("Element found :"+status);
        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter adpStatus = new ArrayAdapter(context,android.R.layout.simple_spinner_item, status);
        adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adpStatus);


       /* //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);*/

        if(studyList.getGender().equalsIgnoreCase("MALE")){
            spnGender = new String[]{"MALE", "FEMALE", "OTHER"};
        }else if(studyList.getGender().equalsIgnoreCase("FEMALE")){
            spnGender = new String[]{"FEMALE", "MALE", "OTHER"};
        }else if(studyList.getGender().equalsIgnoreCase("OTHER")){
            spnGender = new String[]{"OTHER", "MALE", "FEMALE"};
        }else {

        }

        ArrayAdapter genderAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGender);
        genderAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonGender.setAdapter(genderAdp);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter studyIdAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, lists);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);


        ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGroup);
        groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroups.setAdapter(groupAdp);

        txt_dob=(ImageButton)dialog.findViewById(R.id.btn_dob);
        txtDob=(TextView)dialog.findViewById(R.id.txt_dob);
        txtDob.setText(Utilities.splitDateFromDesired(studyList.getDob()));

        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                // dialog.dismiss();

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear[0] = c.get(Calendar.YEAR);
                mMonth[0] = c.get(Calendar.MONTH);
                mDay[0] = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                                String fmonth;
                                int month;
                                if (monthOfYear < 10 && dayOfMonth < 10) {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String fDate = "0" + dayOfMonth;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                                    txtDob.setText(year + "-" + paddedMonth + "-" + fDate);
                                    //endDate =txtEndDate.getText().toString();

                                } else {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                                    txtDob.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                                    //endDate =txtEndDate.getText().toString();
                                }

                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.show();
            }
        });

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


    private void showDeleteDialog() {

        Utils.createDialogTwoButtons(
                context, context.getString(R.string.study_delete),
                true, context.getString(R.string.delete_study_message),
                context.getString(R.string.dlg_yes_text),
                context.getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //CALL DELETE API
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
    }

}

