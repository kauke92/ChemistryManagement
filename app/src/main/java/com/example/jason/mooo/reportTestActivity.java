package com.example.jason.mooo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pdfjet.A4;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.TextLine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class reportTestActivity extends AppCompatActivity {


    File outputFile;
    PDF pdf;
    Font titleFont;
    Font f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_test2);

        outputFile = new File(getExternalFilesDir(null), "test.pdf");

        try {

            if (outputFile.exists()) {
                outputFile.delete();
            }

            outputFile.createNewFile();

            /****************
             *	Create PDF	*
             ****************/
            pdf = new PDF(
                    new BufferedOutputStream(
                            new FileOutputStream(outputFile)));

            // Define page A4 Landscape
            Page page = new Page(pdf, A4.LANDSCAPE);


            titleFont = new Font(pdf, CoreFont.HELVETICA_BOLD);

            f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
            f1.setSize(12f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Font getF1() {
        return f1;
    }

    public String getPDFname(String reportType) {

        SimpleDateFormat date = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);

        if (reportType.equalsIgnoreCase("MedicineAdministered")) {
            return "Administered-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else if (reportType.equalsIgnoreCase("Stock")) {
            return "Stock-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else if (reportType.equalsIgnoreCase("Purchase")) {
            return "Purchase-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else {
            return "empty";
        }

    }

    /**
     *  Return title for report
     *
     * @param type
     * @param titleFont
     * @return TextLIne
     */
    public TextLine getTitle(String type, Font titleFont) {

        TextLine title;

        switch (type) {
            case "MedicineAdministered":
                return title = new TextLine(titleFont, "Administered Cow Report");
            case "Purchase":
                return title = new TextLine(titleFont, "Medicine Purchase Report");
            case "Stock":
                return title = new TextLine(titleFont, "Medicine Stocktake Report");

            default:
                return title = new TextLine(titleFont, "");
        }
    }

    /**
     * Return column heading
     *
     * @param type
     * @param f1
     * @return List<Cell> row
     */
    public List<Cell> getHeader(String type, Font f1) {

        List<Cell> row = new ArrayList<Cell>();
        //Cell cell;
        String[] treatment_header = {"Cow ID", "Medicine", "Quantity", "Treatment date", "Withholding milk", "Withholding slaughter"};
        String[] purchase_header = {"Purchase Date", "Medicine", "Quantity", "Batch Number", "Purchase Place", "Expiry Date", "Signed By"};
        String[] stock_header = {"Purchase Date", "Medicine", "Purchase Amount", "Batch Number", "Expiry Date", "Stock level"};

        switch (type) {
            case "MedicineAdministered":
                for (int i = 0; i < treatment_header.length; i++) {
                    row.add(new Cell(f1, treatment_header[i]));
                }
                return row;

            case "Purchase":
                for (int i = 0; i < purchase_header.length; i++) {
                    row.add(new Cell(f1, purchase_header[i]));
                }
                return row;

            case "Stock":
                for (int i = 0; i < stock_header.length; i++) {
                    row.add(new Cell(f1, stock_header[i]));
                }
                return row;

            default:
                return row;
        }
    }
    public  File getOutputFile(){
        return outputFile;
    }
}