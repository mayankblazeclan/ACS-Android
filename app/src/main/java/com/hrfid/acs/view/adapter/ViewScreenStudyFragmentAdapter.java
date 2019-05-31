package com.hrfid.acs.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.SeniorStaffHomeActivity;

import java.util.ArrayList;

/**
 * Created by MS on 2019-05-31.
 */
public class ViewScreenStudyFragmentAdapter extends RecyclerView.Adapter<ViewScreenStudyFragmentAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    ArrayList personNames;
    String[] status = { "ACTIVE", "INACTIVE", "INPROGRESS"};
    Context context;

    public ViewScreenStudyFragmentAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_study_item_row, parent, false);
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

          holder.txtStatus.setText("ACTIVE");
          holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));

      }else if( position ==2 || position ==5 || position ==8) {

          holder.txtStatus.setText("INACTIVE");
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
        Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();
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

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_study_modify);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) dialog.findViewById(R.id.spnStatus);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

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

