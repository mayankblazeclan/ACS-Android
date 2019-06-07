package com.hrfid.acs.view.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hrfid.acs.R;

public class ReplicateActivity extends AppCompatActivity {

    EditText et_text;
    Button btn_submit;
    Spinner sp_qtyc, sp_qtyl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replicate);

        et_text = findViewById(R.id.et_text);
        btn_submit = findViewById(R.id.btn_submit);
        sp_qtyc = findViewById(R.id.sp_qtyc);
        sp_qtyl = findViewById(R.id.sp_qtyl);

        String[] items = new String[]{"1 ", "2", "3 ", "4", "5 ", "6",
                "7 ", "8", "9 ", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReplicateActivity.this, R.layout.layout_popup_spinner, items);
        sp_qtyc.setAdapter(adapter);
        sp_qtyl.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_text.getText().toString().equals("")) {
                    Toast.makeText(ReplicateActivity.this, "Please Enter Text", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(ReplicateActivity.this, ShowReplicateListActivity.class);
                    i.putExtra("qtyc", sp_qtyc.getSelectedItem().toString());
                    i.putExtra("qtyl", sp_qtyl.getSelectedItem().toString());
                    i.putExtra("text", et_text.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
