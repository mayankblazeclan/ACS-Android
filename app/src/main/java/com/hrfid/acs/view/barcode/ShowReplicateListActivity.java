package com.hrfid.acs.view.barcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hrfid.acs.R;

import java.util.ArrayList;
import java.util.List;

public class ShowReplicateListActivity extends AppCompatActivity {

    String qtyc, qtyl;
    private List<ReplicateModel> replicateListQtyc = new ArrayList<>();
    private List<ReplicateModel> replicateListQtyl = new ArrayList<>();
    String replicateText;
    Button btn_print_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_replicate_list);
        btn_print_all = findViewById(R.id.btn_print_all);
        Intent i = getIntent();
        qtyc = i.getStringExtra("qtyc").trim();
        qtyl = i.getStringExtra("qtyl").trim();

        replicateText = i.getStringExtra("text");

        final RecyclerView recyclerViewQtyc, recyclerViewQtyl;
        recyclerViewQtyc = findViewById(R.id.recycler_view_qtyc);
        recyclerViewQtyl = findViewById(R.id.recycler_view_qtyl);
        replicateData();
        ReplicateAdapter mAdapter = new ReplicateAdapter(replicateListQtyc);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewQtyc.setLayoutManager(mLayoutManager);
        recyclerViewQtyc.setItemAnimator(new DefaultItemAnimator());
        recyclerViewQtyc.setAdapter(mAdapter);

        ReplicateAdapter mAdapterqtyl = new ReplicateAdapter(replicateListQtyl);
        RecyclerView.LayoutManager mLayoutManagerqtyl = new LinearLayoutManager(getApplicationContext());
        recyclerViewQtyl.setLayoutManager(mLayoutManagerqtyl);
        recyclerViewQtyl.setItemAnimator(new DefaultItemAnimator());
        recyclerViewQtyl.setAdapter(mAdapterqtyl);

        btn_print_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

                String jobName = getString(R.string.app_name) +
                        " Document";

                replicateListQtyc.addAll(replicateListQtyl);
                printManager.print(jobName, new PrintAdapter(ShowReplicateListActivity.this, "4", replicateListQtyc), null);
            }
        });

    }

    private void replicateData() {
        try {
            for (int i = 1; i < (Integer.parseInt(qtyc) + 1); i++) {
                try {
                    ReplicateModel replicateModel = new ReplicateModel(replicateText + "_" + i, CreateImage(replicateText + "_" + i));
                    replicateListQtyc.add(replicateModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 1; i < (Integer.parseInt(qtyl) + 1); i++) {
                try {
                    ReplicateModel replicateModel = new ReplicateModel(replicateText + "_" + i + "l", CreateImage(replicateText + "_" + i + "L"));
                    replicateListQtyl.add(replicateModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public Bitmap CreateImage(String message) throws WriterException {
        BitMatrix bitMatrix;
        int size_width = 660;
        int size_height = 264;
        bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bitMatrix.get(j, i)) {
                    pixels[i * width + j] = 0xff000000;
                } else {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
