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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by MS on 2019-05-31.
 */
public class SubjectDetailsAdapter extends RecyclerView.Adapter<SubjectDetailsAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    ArrayList personNames;
    String[] status = { "APPROVED", "REJECTED", "INPROGRESS"};
    Context context;

    String[] spnBloodGroup = {"O+","B-","B+", "A+", "A-"};

    String[] spnStudyID = {"10012","10011","10010", "10015", "10016"};

    String[] spnGender = {"Male","Female","Other"};

    String[] spnGroup = {"G1","G2","G3", "G4", "G5"};

    public SubjectDetailsAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject_details_row, parent, false);
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

      if( position ==1 || position ==4 || position ==7) {

          holder.txtStatus.setText("APPROVED");
          holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));

      }else if( position ==2 || position ==5 || position ==8) {

          holder.txtStatus.setText("REJECTED");
          holder.txtStatus.setTextColor(Color.RED);
      }else {

          holder.txtStatus.setText("INPROGRESS");
          holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
      }

        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showModifyDialog();
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
        return personNames.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        TextView txtStatus;
        Button btnModify, btnDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.study_date);
            btnModify = (Button) itemView.findViewById(R.id.btnModify);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            txtStatus = itemView.findViewById(R.id.txt_status);
        }
    }

    private void showModifyDialog() {

        ImageButton btnStartDatePicker, btnEndDatePicker;
        final TextView txtStartDate, txtEndDate;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_subject_modify);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spnBloodGroups = (Spinner) dialog.findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);

        Spinner spnStudyIDs = (Spinner) dialog.findViewById(R.id.spnStudyId);
        spnStudyIDs.setOnItemSelectedListener(this);

        Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
        spnGroups.setOnItemSelectedListener(this);

        Spinner spnPersonGender = (Spinner) dialog.findViewById(R.id.spnPersonGender);
        spnPersonGender.setOnItemSelectedListener(this);

        Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatusId);
        spnStatus.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter studyIdAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnStudyID);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);

        ArrayAdapter genderAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGender);
        genderAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonGender.setAdapter(genderAdp);

        ArrayAdapter statusAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, status);
        statusAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(statusAdp);

        ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGroup);
        groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroups.setAdapter(groupAdp);
        /*btnEndDatePicker=(ImageButton)dialog.findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)dialog.findViewById(R.id.txt_end_date);*/


     /*   btnStartDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog

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

                                txtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.show();
            }
        });*/

        // Set dialog title
        //--- dialog.setTitle("MODIFY STUDY");

        // set values for custom dialog components - text, image and button
        /*TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("Custom dialog Android example.");
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        image.setImageResource(R.drawable.image0);*/

        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
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

