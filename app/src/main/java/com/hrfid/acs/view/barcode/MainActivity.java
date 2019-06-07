package com.hrfid.acs.view.barcode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hrfid.acs.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import android.print.PageRange;
public class MainActivity extends AppCompatActivity {

    private String message = "";
    private String type = "";
    private Button button_generate;
    private EditText editText1;
    private Spinner type_spinner;
    private ImageView imageView;
    private int size = 660;
    private int size_width = 660;
    private int size_height = 264;

    private TextView success_text;
    private ImageView success_imageview;
    private TextView success_text1, label, label1;
    private ImageView success_imageview1;

    private String time;

    private Bitmap myBitmap;
    Button btn_print, btn_replicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_print = findViewById(R.id.btn_print);
        btn_replicate = findViewById(R.id.btn_replicate);
        btn_print.setVisibility(View.INVISIBLE);

        setSupportActionBar(toolbar);

        message = "";
        type = "Barcode";
        button_generate = (Button) findViewById(R.id.generate_button);
        editText1 = (EditText) findViewById(R.id.edittext2);
        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        imageView = (ImageView) findViewById(R.id.image_imageview);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0: type = "Barcode";break;
                    /*
                    case 1: type = "Barcode";break;
                    case 2: type = "Data Matrix";break;
                    case 3: type = "PDF 417";break;
                    case 4: type = "Barcode-39";break;
                    case 5: type = "Barcode-93";break;
                    case 6: type = "AZTEC";break;*/
                    default: type = "Barcode";break;
                }
                Log.d("type", type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = editText1.getText().toString();

                if (message.equals("") || type.equals(""))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Error");
                    dialog.setMessage("Invalid input!");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  do nothing
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                else
                {
                    Bitmap bitmap = null;
                    try
                    {
                        bitmap = CreateImage(message, type);
                        myBitmap = bitmap;
                    }
                    catch (WriterException we)
                    {
                        we.printStackTrace();
                    }
                    if (bitmap != null)
                    {
                        imageView.setImageBitmap(bitmap);
                        label1 = findViewById(R.id.label1);
                        label1.setText(message);
                        btn_print.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Print barcode label for -")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Trial", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                success_text1 = (TextView) findViewById(R.id.success_text1);
                                success_text1.setText(message + "\n\n" + "Barcode GENERATED");
                                success_imageview1 = (ImageView) findViewById(R.id.success_imageview1);
                                label = findViewById(R.id.label);
                                label.setText(message);
                                success_imageview1.setImageBitmap(myBitmap);
                                RelativeLayout printlayout = findViewById(R.id.print_layout);
                                printlayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

                                printDocument("1");
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Screening", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                success_text1 = (TextView) findViewById(R.id.success_text1);
                                success_text1.setText(message + "\n\n" + "Barcode GENERATED");
                                success_imageview1 = (ImageView) findViewById(R.id.success_imageview1);
                                label = findViewById(R.id.label);
                                label.setText(message);
                                success_imageview1.setImageBitmap(myBitmap);
                                RelativeLayout printlayout = findViewById(R.id.print_layout);
                                printlayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

                                printDocument("2");
                            }
                        })
                        .show();



            }
        });


        btn_replicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] s = { "1 ", "2", "3 ", "4", "5 ", "6",
//                        "7 ", "8", "9 ", "10","11","12","13","14","15","16","17","18","19","20", };
//                final ArrayAdapter<String> adp = new ArrayAdapter<String>(MainActivity.this,
//                        android.R.layout.simple_spinner_item, s);
//
//                final Spinner sp = new Spinner(MainActivity.this);
//                sp.setBackgroundColor(getResources().getColor(R.color.colorBackground));
//                sp.setGravity(0);
//                sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                sp.setAdapter(adp);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setView(sp);
//                builder.setTitle("Select No of time to Replicate: ");
//                builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        String count = sp.getSelectedItem().toString();
//                        Intent i = new Intent(MainActivity.this, ShowReplicateListActivity.class);
//                        i.putExtra("count",count);
//                        i.putExtra("text",message);
//                        startActivity(i);
//
//                    }
//                });
//                builder.create().show();

                Intent i = new Intent(MainActivity.this, ReplicateActivity.class);
                startActivity(i);

            }
        });

    }

    public Bitmap CreateImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            //case "QR Code": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            case "Barcode": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);break;
            /*case "Data Matrix": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.DATA_MATRIX, size, size);break;
            case "PDF 417": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.PDF_417, size_width, size_height);break;
            case "Barcode-39":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_39, size_width, size_height);break;
            case "Barcode-93":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_93, size_width, size_height);break;
            case "AZTEC": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.AZTEC, size, size);break;*/
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
    public void printDocument(String selectedValue)
    {
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";
        // printManager.print(jobName, new MyPrintDocumentAdapter(this,selectedValue),null);
        printManager.print(jobName, new PrintAdapter(this,selectedValue,message,myBitmap),null);


    }
//    public class MyPrintDocumentAdapter extends PrintDocumentAdapter
//    {
//        Context context;
//        private int pageHeight;
//        private int pageWidth;
//        public PdfDocument myPdfDocument;
//        public int totalpages = 1;
//        String selectedValue;
//        public MyPrintDocumentAdapter(Context context, String selectedValue)
//        {
//            this.context = context;
//            this.selectedValue = selectedValue;
//        }
//        private boolean pageInRange(PageRange[] pageRanges, int page)
//        {
//            for (int i = 0; i<pageRanges.length; i++)
//            {
//                if ((page >= pageRanges[i].getStart()) &&
//                        (page <= pageRanges[i].getEnd()))
//                    return true;
//            }
//            return false;
//        }
//
//        private void drawPage(PdfDocument.Page page) {
//            Canvas canvas = page.getCanvas();
//
//            int titleBaseLine = 72;
//            int leftMargin = 45;
//
//            Paint paint = new Paint();
//            paint.setColor(Color.BLACK);
//            paint.setTextSize(22);
//
//            if (selectedValue.equals("1")) {
//                canvas.drawText(
//                        " " + message,
//                        leftMargin,
//                        titleBaseLine,
//                        paint);
//
//                paint.setTextSize(20);
//                canvas.drawText("Initials: ", leftMargin, titleBaseLine + 25, paint);
//                canvas.drawText("Visit: ", 250, titleBaseLine + 25, paint);
//                canvas.drawText("DOB: ", leftMargin, titleBaseLine + 55, paint);
//                canvas.drawText("Sex: ", 250, titleBaseLine + 55, paint);
//                canvas.drawText("Date: _ _/_ _ _/20_ _ ", leftMargin, titleBaseLine + 80, paint);
//                canvas.drawText("Time: _ _:_ _ _hrs", leftMargin, titleBaseLine + 105, paint);
//                Bitmap resized = getResizedBitmap(myBitmap, 500);
//                canvas.drawBitmap(resized, 25, 200, paint);
//                if (type.contains("Barcode")) {
//                    canvas.drawText(message, 200, 420, paint);
//                } else {
//                    canvas.drawText(message, 200, 650, paint);
//                }
//
//            }
//            else{
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//                canvas.drawText(
//                        " " + message,
//                        leftMargin,
//                        titleBaseLine,
//                        paint);
//
//                paint.setTextSize(20);
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//                canvas.drawText("SCR#: A049", leftMargin, titleBaseLine + 25, paint);
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//                canvas.drawText("ADA/NAb ", 250, titleBaseLine + 25, paint);
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//                canvas.drawText("RAND#: 1038 ", leftMargin, titleBaseLine + 55, paint);
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//                canvas.drawText("Day 36 ", 250, titleBaseLine + 55, paint);
//                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//                canvas.drawText("Time: _ _:_ _ _hrs", leftMargin, titleBaseLine + 80, paint);
//                Bitmap resized = getResizedBitmap(myBitmap, 500);
//                canvas.drawBitmap(resized, 25, 200, paint);
//                if (type.contains("Barcode")) {
//                    canvas.drawText(message, 200, 420, paint);
//                } else {
//                    canvas.drawText(message, 200, 650, paint);
//                }
//            }
//
//        }
//
//        @Override
//        public void onLayout(PrintAttributes oldAttributes,
//                             PrintAttributes newAttributes,
//                             CancellationSignal cancellationSignal,
//                             LayoutResultCallback callback,
//                             Bundle metadata) {
//            myPdfDocument = new PrintedPdfDocument(context, newAttributes);
//
//            pageHeight =
//                    newAttributes.getMediaSize().getHeightMils()/1000 * 72;
//            pageWidth =
//                    newAttributes.getMediaSize().getWidthMils()/1000 * 72;
//
//            if (cancellationSignal.isCanceled() ) {
//                callback.onLayoutCancelled();
//                return;
//            }
//
//            if (totalpages > 0) {
//                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
//                        .Builder("print_output.pdf")
//                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
//                        .setPageCount(totalpages);
//
//                PrintDocumentInfo info = builder.build();
//                callback.onLayoutFinished(info, true);
//            } else {
//                callback.onLayoutFailed("Page count is zero.");
//            }
//        }
//
//
//        @Override
//        public void onWrite(final PageRange[] pageRanges,
//                            final ParcelFileDescriptor destination,
//                            final CancellationSignal
//                                    cancellationSignal,
//                            final WriteResultCallback callback) {
//
//            for (int i = 0; i < totalpages; i++) {
//                if (pageInRange(pageRanges, i))
//                {
//                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
//                            pageHeight, i).create();
//
//                    PdfDocument.Page page =
//                            myPdfDocument.startPage(newPage);
//
//                    if (cancellationSignal.isCanceled()) {
//                        callback.onWriteCancelled();
//                        myPdfDocument.close();
//                        myPdfDocument = null;
//                        return;
//                    }
//                    drawPage(page);
//                    myPdfDocument.finishPage(page);
//                }
//            }
//
//            try {
//                myPdfDocument.writeTo(new FileOutputStream(
//                        destination.getFileDescriptor()));
//            } catch (IOException e) {
//                callback.onWriteFailed(e.toString());
//                return;
//            } finally {
//                myPdfDocument.close();
//                myPdfDocument = null;
//            }
//
//            callback.onWriteFinished(pageRanges);
//        }
//
//    }

//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float)width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
}
