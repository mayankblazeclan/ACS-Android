package com.hrfid.acs.view.barcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintAdapter extends PrintDocumentAdapter {

    Context context;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 1;
    String selectedValue;
    String message;
    Bitmap bitmap;
    List<ReplicateModel> replicateArrayList;

    public PrintAdapter(Context context, String selectedValue, String message, Bitmap bitmap) {
        this.context = context;
        this.selectedValue = selectedValue;
        this.message = message;
        this.bitmap = bitmap;
    }

    public PrintAdapter(Context context, String selectedValue, List<ReplicateModel> replicateArrayList) {
        this.context = context;
        this.selectedValue = selectedValue;
        this.replicateArrayList = replicateArrayList;
    }

    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    private void drawPage(PdfDocument.Page page, int pagenumber) {
        pagenumber = 1;

        Canvas canvas = page.getCanvas();

        int titleBaseLine = 72;
        int leftMargin = 45;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(22);

        if (selectedValue.equals("1")) {
            canvas.drawText(
                    " " + message,
                    leftMargin,
                    titleBaseLine,
                    paint);

            paint.setTextSize(20);
            canvas.drawText("Initials: ", leftMargin, titleBaseLine + 25, paint);
            canvas.drawText("Visit: ", 250, titleBaseLine + 25, paint);
            canvas.drawText("DOB: ", leftMargin, titleBaseLine + 55, paint);
            canvas.drawText("Sex: ", 250, titleBaseLine + 55, paint);
            canvas.drawText("Date: _ _/_ _ _/20_ _ ", leftMargin, titleBaseLine + 80, paint);
            canvas.drawText("Time: _ _:_ _ _hrs", leftMargin, titleBaseLine + 105, paint);
            Bitmap resized = getResizedBitmap(bitmap, 500);
            /*canvas.drawBitmap(resized, 25, 200, paint);

            canvas.drawText(message, 200, 420, paint);*/

            canvas.drawBitmap(resized, 35, 200, paint);
            canvas.drawText(message, 80, 275, paint);
        } else if (selectedValue.equals("2")) {
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(
                    " " + message,
                    leftMargin,
                    titleBaseLine,
                    paint);

            paint.setTextSize(20);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvas.drawText("SCR#: A049", leftMargin, titleBaseLine + 25, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("ADA/NAb ", 250, titleBaseLine + 25, paint);
            //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            //canvas.drawText("RAND#: 1038 ", leftMargin, titleBaseLine + 55, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("VISIT -SCR", 250, titleBaseLine + 55, paint);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            canvas.drawText("Time: _ _:_ _ _hrs", leftMargin, titleBaseLine + 55, paint);
            Bitmap resized = getResizedBitmap(bitmap, 500);
           /* canvas.drawBitmap(resized, 25, 200, paint);

            canvas.drawText(message, 200, 420, paint);*/
            canvas.drawBitmap(resized, 35, 200, paint);
            canvas.drawText(message, 80, 275, paint);

        } else if (selectedValue.equals("4")) {

            int height = 50;
            int textXPos = 70;
            int textYPos = 120;

            int row2height = 50;
            int row2textYPos = 120;
            int row3height = 50;
            int row3textYPos = 120;
            int row4height = 50;
            int row4textYPos = 120;
            for (int i = 0; i < replicateArrayList.size(); i++) {

                if (i > 6 && i < 14) {
                    paint.setTextSize(20);
                    paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                    Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                    canvas.drawBitmap(resized, 250, row2height, paint);

                    canvas.drawText(replicateArrayList.get(i).getTitle(), 300, row2textYPos, paint);

                    row2textYPos = row2textYPos + 100;

                    row2height = row2height + 100;
                }

                if (i > 12 && i < 20) {
                    paint.setTextSize(20);
                    paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                    Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                    canvas.drawBitmap(resized, 450, row3height, paint);

                    canvas.drawText(replicateArrayList.get(i).getTitle(), 500, row3textYPos, paint);

                    row3textYPos = row3textYPos + 100;

                    row3height = row3height + 100;
                }
                if (i > 21 && i < 29) {
                    paint.setTextSize(20);
                    paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                    Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                    canvas.drawBitmap(resized, 650, row4height, paint);

                    canvas.drawText(replicateArrayList.get(i).getTitle(), 700, row4textYPos, paint);

                    row4textYPos = row4textYPos + 100;

                    row4height = row4height + 100;
                } else {
                    if (i < 7) {
                        paint.setTextSize(20);
                        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                        Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                        canvas.drawBitmap(resized, 25, height, paint);

                        canvas.drawText(replicateArrayList.get(i).getTitle(), textXPos, textYPos, paint);

                        textYPos = textYPos + 100;

                        height = height + 100;
                    }


                }
            }
        } else {
            paint.setTextSize(20);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            Bitmap resized = getResizedBitmap(bitmap, 500);
            canvas.drawBitmap(resized, 25, 50, paint);
            canvas.drawText(message, 70, 120, paint);

        }
    }


    private void drawPage2(PdfDocument.Page page, int pagenumber) {
        pagenumber = 1;

        Canvas canvas = page.getCanvas();

        int titleBaseLine = 72;
        int leftMargin = 45;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(22);

        int height = 50;
        int textXPos = 70;
        int textYPos = 120;

        int row2height = 50;
        int row2textYPos = 120;
        int row3height = 50;
        int row3textYPos = 120;
        for (int i = 6; i < replicateArrayList.size(); i++) {


            if (i > 6) {
                pagenumber++;
                myPdfDocument.finishPage(page);

                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page2 =
                        myPdfDocument.startPage(newPage);
                paint.setTextSize(20);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                canvas.drawBitmap(resized, 250, row2height, paint);

                canvas.drawText(replicateArrayList.get(i).getTitle(), 300, row2textYPos, paint);

                row2textYPos = row2textYPos + 100;

                row2height = row2height + 100;

            }

            if (i > 13) {
                pagenumber++; // Make sure page numbers start at 1
                PdfDocument.PageInfo pageInfo = page.getInfo();

                paint.setTextSize(20);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                canvas.drawBitmap(resized, 450, row3height, paint);

                canvas.drawText(replicateArrayList.get(i).getTitle(), 500, row3textYPos, paint);

                row3textYPos = row3textYPos + 100;

                row3height = row3height + 100;

            } else {
                paint.setTextSize(20);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                Bitmap resized = getResizedBitmap(replicateArrayList.get(i).getBarcodeBitmap(), 500);
                canvas.drawBitmap(resized, 25, height, paint);

                canvas.drawText(replicateArrayList.get(i).getTitle(), textXPos, textYPos, paint);

                textYPos = textYPos + 100;

                height = height + 100;

            }
        }
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {
        myPdfDocument = new PrintedPdfDocument(context, newAttributes);

        pageHeight =
                newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth =
                newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }

    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal
                                cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i)) {
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i);


                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width - 350, height - 250, true);
    }
}
