package com.example.pdf_download;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pdf_creator {

    private static final String TAG = "Pdf_creator";
    Context context;


    public Pdf_creator(Context context) {
        this.context = context;
    }

    public void createPdf(String pdf_name, String batch, String courseId, ArrayList<String> ids, ArrayList<String> percentage) {


        //Creating Directory
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
                + File.separator
                + "BAUET Attendance"
                + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("_(yyyy-MM-dd)_(HH:mm)").format(date);

        //File Path
        String file_path = dir.getPath() + File.separator + pdf_name+timeStamp+".pdf";


        //If Same file already Exist then Delete the Existing File
        if (new File(file_path).exists()) {
            new File(file_path).delete();
        }

        try {
            /**
             * Creating Document
             */
            Document document = new Document();


            // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(file_path));

            // Open to write
            document.open();

            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Junaed Muhammad Chowdhury");
            document.addCreator("Junaed");

            /***
             * Variables for further use....
             */

            // Base Front For Front type
            final BaseFont baseFont = BaseFont.createFont(
                    BaseFont.TIMES_ROMAN,
                    BaseFont.CP1252,
                    BaseFont.EMBEDDED);

            // Color for Front
            BaseColor headingColorAccent = new BaseColor(0, 153, 204, 255);

            // Heading Front Size
            float headingFontSize = 17.5f;

            // Other Front Size
            float valueFontSize = 16.0f;

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Varsity Logo Added ti the Documents
            Drawable d = context.getResources().getDrawable(R.drawable.logo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.setAbsolutePosition(10f,720f);
            image.scaleToFit(850,100);
            document.add(image);

            //Adding Varsity Name
            Font varsityTitleFront = new Font(baseFont, 19.0f, Font.NORMAL, BaseColor.BLACK);
                Chunk varsityTitleChunk = new Chunk("      Bangladesh Army University Of Engineering & Technology", varsityTitleFront);
            Paragraph varsityTitleParagraph = new Paragraph(varsityTitleChunk);
            varsityTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(varsityTitleParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            //Adding Document Title Name
            Font documentTitleFront = new Font(baseFont, 17.5f, Font.NORMAL, BaseColor.BLACK);
            Chunk documentTitleChunk = new Chunk("Student Attendance Report", documentTitleFront);
            Paragraph documentTitleParagraph = new Paragraph(documentTitleChunk);
            documentTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(documentTitleParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(" "));
            // Adding Line Breakable Space....
            document.add(new Paragraph(" "));

            // Adding Chunks for Batch Title
            Font batchTitleFont = new Font(baseFont, headingFontSize, Font.NORMAL, headingColorAccent);
            Chunk batchTitleChunk = new Chunk("Batch :", batchTitleFont);
            Paragraph batchTitleParagraph = new Paragraph(batchTitleChunk);
            document.add(batchTitleParagraph);

            // Adding Chunks for Batch
            Font batchFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk batchChunk = new Chunk(batch, batchFont);
            Paragraph batchParagraph = new Paragraph(batchChunk);
            document.add(batchParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Adding Chunks for Course Id Title
            Font courseTitleFont = new Font(baseFont, headingFontSize, Font.NORMAL, headingColorAccent);
            Chunk courseTitleChunk = new Chunk("Course Id :", courseTitleFont);
            Paragraph courseTitleParagraph = new Paragraph(courseTitleChunk);
            document.add(courseTitleParagraph);

            // Adding Chunks for Course Id
            Font courseFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk courseChunk = new Chunk(courseId, courseFont);
            Paragraph courseParagraph = new Paragraph(courseChunk);
            document.add(courseParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(" "));


            //Creating Table For 3 Rows
            PdfPTable table = new PdfPTable(new float[]{5, 5, 5});
            table.setTotalWidth(PageSize.A4.getWidth());
            table.setWidthPercentage(100);

            //For Title Row
            //Title For Id
            PdfPCell cellValueForId = new PdfPCell(new Phrase("Id",batchTitleFont));
            cellValueForId.setFixedHeight(50);
            cellValueForId.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellValueForId.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellValueForId);

            //Title For Percentage
            PdfPCell cellValueForPercentage = new PdfPCell(new Phrase("Percentage",batchTitleFont));
            cellValueForPercentage.setFixedHeight(50);
            cellValueForPercentage.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellValueForPercentage.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellValueForPercentage);

            //Title For Qualification
            PdfPCell cellValueForQualification = new PdfPCell(new Phrase("Qualification",batchTitleFont));
            cellValueForQualification.setFixedHeight(50);
            cellValueForQualification.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellValueForQualification.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellValueForQualification);

            table.setHeaderRows(1);

            //Coloring Title Row
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            //Setting Values in Table fields
            for (int i = 0; i < ids.size(); i++) {

                String id_value = null;
                id_value = ids.get(i);
                String percentage_value = null;
                percentage_value = percentage.get(i);
                String qualification = null;
                if (Integer.parseInt(percentage_value)>=80){
                    qualification = "Collegiate";
                }else if (Integer.parseInt(percentage_value)<70){
                    qualification = "Dis-Collegiate";
                }else {
                    qualification = "Non-Collegiate";
                }


                //table.addCell(name[i]+i);
                PdfPCell idCellValue = new PdfPCell(new Phrase(id_value,batchFont));
                idCellValue.setFixedHeight(40);
                idCellValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                idCellValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(idCellValue);

                PdfPCell percentageCellValue = new PdfPCell(new Phrase(percentage_value,batchFont));
                percentageCellValue.setFixedHeight(40);
                percentageCellValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                percentageCellValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(percentageCellValue);

                PdfPCell qualificationCellValue = new PdfPCell(new Phrase(qualification,batchFont));
                qualificationCellValue.setFixedHeight(40);
                qualificationCellValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                qualificationCellValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(qualificationCellValue);
            }


            document.add(table);

            document.close();

            Toast.makeText(context, "Created... :)\nFile seved to the "+file_path, Toast.LENGTH_LONG).show();


        } catch (IOException | DocumentException ignored) {
            Toast.makeText(context, "DocumentException ignored", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void permission(final String pdf_name, final String batch, final String courseId, final ArrayList<String> ids, final ArrayList<String> percentage) {
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                createPdf(pdf_name,batch,courseId,ids,percentage);
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TedPermission.with(context)
                    .setPermissionListener(listener)
                    .setPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .check();
        }
    }
}
