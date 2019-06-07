package com.hrfid.acs.view.barcode;

import android.content.Context;
import android.print.PrintManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrfid.acs.R;

import java.util.List;

public class ReplicateAdapter extends RecyclerView.Adapter<ReplicateAdapter.MyViewHolder> {

    private List<ReplicateModel> replicateList;

    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView barcodeImageView;
        Button btn_print;
        Context context;

        public MyViewHolder(View view, Context c) {
            super(view);
            title = (TextView) view.findViewById(R.id.serial_no);
            barcodeImageView = (ImageView) view.findViewById(R.id.iv_replicate_barcode);
            btn_print = (Button) view.findViewById(R.id.btn_replicate_print);
            this.context=c;
        }
    }


    public ReplicateAdapter(List<ReplicateModel> replicateList) {
        this.replicateList = replicateList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.replicate_row, parent, false);

        context = parent.getContext();
        return new MyViewHolder(itemView,parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ReplicateModel replicateModel = replicateList.get(position);
        holder.title.setText(replicateModel.getTitle());
        holder.barcodeImageView.setImageBitmap(replicateModel.getBarcodeBitmap());
        holder.btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintManager printManager = (PrintManager) context
                        .getSystemService(Context.PRINT_SERVICE);

                String jobName = context.getString(R.string.app_name) +
                        " Document";
                // printManager.print(jobName, new MyPrintDocumentAdapter(this,selectedValue),null);
                printManager.print(jobName, new PrintAdapter(context,"3",replicateModel.getTitle(),replicateModel.getBarcodeBitmap()),null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return replicateList.size();
    }
}
