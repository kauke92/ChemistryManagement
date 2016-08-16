package com.example.jason.mooo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.MedicineAdministered;
import com.example.jason.mooo.Model.Purchase;
import com.example.jason.mooo.Model.Stock;
import com.example.jason.mooo.Resources.AlertButton;
import com.pdfjet.A4;
import com.pdfjet.Cell;
import com.pdfjet.Color;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;

import java.io.BufferedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// this class function take care of different type of report generation.
public class reportGenerator extends AsyncTask<Object, Void, String> {

    private Context context;
    private String  userName;

    public reportGenerator(Context context) {

        this.context = context;
        userName = new Database(context).getUsername();

    }

    @Override
    protected String doInBackground(Object... stuff) {

        ArrayList<?> list = (ArrayList<?>) stuff[0];

        if (list.isEmpty()) {
            Log.i("PurchaseCheck", "List is empty");

            return "empty";
        }

        String type = list.get(0).getClass().getSimpleName();

        Log.i("PurchaseCheck", "The type is " + type);

        ArrayList<MedicineAdministered> medicines = new ArrayList<>();
        ArrayList<Stock> stocks = new ArrayList<>();
        ArrayList<Purchase> purchases = new ArrayList<>();

        switch (type) {
            case "MedicineAdministered":
                medicines = (ArrayList<MedicineAdministered>) stuff[0];
                break;
            case "Purchase":
                purchases = (ArrayList<Purchase>) stuff[0];
                break;

            case "Stock":
                stocks = (ArrayList<Stock>) stuff[0];
                break;

            default:
                AlertButton box = new AlertButton(context, "Error");
                return null;
        }

        File outputFile;
        String pdfName = getPDFname(type);

        // use getExternalFilesDir to store in external storage.
        outputFile = new File(context.getExternalFilesDir(null), pdfName);

        try {

            if (outputFile.exists()) {
                outputFile.delete();
            }

            outputFile.createNewFile();


            /****************
             *	Create PDF	*
             ****************/
            PDF pdf = new PDF(
                    new BufferedOutputStream(
                            new FileOutputStream(outputFile)));

            // Define page A4 Landscape
            Page page = new Page(pdf, A4.LANDSCAPE);

            // Define fonts
            Font titleFont = new Font(pdf, CoreFont.HELVETICA_BOLD);

            Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
            f1.setSize(12f);

            Font f2 = new Font(pdf, CoreFont.HELVETICA);
            f2.setSize(10f);

            Font f3 = new Font(pdf, CoreFont.HELVETICA_BOLD_OBLIQUE);
            f3.setSize(7f);

            // Set report title
            TextLine title = getTitle(type, titleFont);
            title.setFontSize(30f);

            //center the title horizontally on the page
            title.setPosition(page.getWidth() / 2 - title.getWidth() / 2, 70f);

            //SimpleDateFormat date = new SimpleDateFormat("ddMMyy");
            // TODO GET USER NAME
            TextLine sub = new TextLine(f3, "Report generated by: " + userName + ", " + new Date());
            sub.setPosition(page.getWidth() / 2 - sub.getWidth() / 2, 90f);
            sub.setColor(Color.blue);

            //draw the title on the page
            title.drawOn(page);
            sub.drawOn(page);

            /****************************
             *	Create COLUMN HEADING	*
             ****************************/

            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<List<Cell>>();
            List<Cell> row = new ArrayList<Cell>();

            tableData.add(getHeader(type, f1));

            Log.i("PurchaseCheck", "Hitting here");

            /****************************************************
             *	Create data from Firebase and put into row	    *
             ****************************************************/

            switch (type) {
                case "MedicineAdministered":
                    for (int i = 0; i < medicines.size(); i++) {

                        row = new ArrayList<Cell>();

                        row.add(new Cell(f2, medicines.get(i).getCowID()));
                        row.add(new Cell(f2, medicines.get(i).getDrugName()));
                        row.add(new Cell(f2, Integer.toString(medicines.get(i).getQuantity())));
                        row.add(new Cell(f2, Integer.toString(medicines.get(i).getTreatmentDate())));
                        row.add(new Cell(f2, Integer.toString(medicines.get(i).getWithholdingMilk())));
                        row.add(new Cell(f2, Integer.toString(medicines.get(i).getWithholdingSlaughter())));

                        tableData.add(row);

                    }

                    break;
                case "Purchase":
                    for (int i = 0; i < purchases.size(); i++) {

                        row = new ArrayList<Cell>();

                        row.add(new Cell(f2, Integer.toString(purchases.get(i).getPurchaseDate())));
                        row.add(new Cell(f2, purchases.get(i).getDrugName()));
                        row.add(new Cell(f2, Integer.toString(purchases.get(i).getAmount())));
                        row.add(new Cell(f2, purchases.get(i).getBatch()));
                        row.add(new Cell(f2, purchases.get(i).getPurchasePlace()));
                        row.add(new Cell(f2, Integer.toString(purchases.get(i).getExpireDate())));
                        row.add(new Cell(f2, purchases.get(i).getSignedBy()));

                        tableData.add(row);
                    }

                    break;
                case "Stock":
                    for (int i = 0; i < stocks.size(); i++) {
                        row = new ArrayList<Cell>();

                        row.add(new Cell(f2, Integer.toString(stocks.get(i).getPurchaseDate())));
                        row.add(new Cell(f2, stocks.get(i).getDrugName()));
                        row.add(new Cell(f2, Integer.toString(stocks.get(i).getAmount())));
                        row.add(new Cell(f2, stocks.get(i).getBatch()));
                        row.add(new Cell(f2, Integer.toString(stocks.get(i).getExpireDate())));
                        row.add(new Cell(f2, Integer.toString(stocks.get(i).getAmountLeft())));

                        tableData.add(row);
                    }
                    break;
                default:
                    return "empty";
            }


            table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
            table.setCellBordersWidth(1.2f);
            table.setCellBordersWidth(0.2f);

            table.autoAdjustColumnWidths();

            /****************************************************
             *	Adjust column with depend on report type        *
             ****************************************************/

            switch (type) {
                case "MedicineAdministered":
                    //Cow ID
                    table.setColumnWidth(0, 60f);
                    //Medicine
                    table.setColumnWidth(1, 70f);
                    //Quantity
                    table.setColumnWidth(2, 70f);
                    //Treatment date
                    table.setColumnWidth(3, 100f);
                    //Withholding milk
                    table.setColumnWidth(4, 110f);
                    //Withholding slaughter
                    table.setColumnWidth(5, 140f);

                    //table.rightAlignNumbers();
                    break;

                case "Purchase":
                    //Purchase Date
                    table.setColumnWidth(0, 100f);
                    //Medicine
                    table.setColumnWidth(1, 70f);
                    //Quantity
                    table.setColumnWidth(2, 70f);
                    //Batch Number
                    table.setColumnWidth(3, 100f);
                    //Purchase Place
                    table.setColumnWidth(4, 100f);
                    //Expiry Date
                    table.setColumnWidth(5, 80f);
                    //Signed by
                    table.setColumnWidth(6, 100f);

                    break;

                case "Stock":
                    //Purchase Date
                    table.setColumnWidth(0, 100f);
                    //Medicine
                    table.setColumnWidth(1, 70f);
                    //Purchase Amount
                    table.setColumnWidth(2, 120f);
                    //Batch Number
                    table.setColumnWidth(3, 100f);
                    //Expiry Date
                    table.setColumnWidth(4, 80f);
                    //Stock Level
                    table.setColumnWidth(5, 80f);
                    break;

                default:
                    break;
            }


            //table.rightAlignNumbers();
            table.wrapAroundCellText();
            table.setLocation((page.getWidth() - table.getWidth()) / 2, 120f);

            Log.i("PurchaseCheck", "Hitting here");


            while (true) {
                table.drawOn(page);

                if (!table.hasMoreData()) {

                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A4.LANDSCAPE);
                table.setLocation((page.getWidth() - table.getWidth()) / 2, 30f);
            }

            pdf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputFile.getPath();
    }

    @Override
    protected void onPostExecute(String filePath) {

        Toast toast = Toast.makeText(context,"Your report contains no results. Please change the dates", Toast.LENGTH_SHORT);
        if (filePath.equalsIgnoreCase("empty")) {
            toast.show();
            main_report_ui.toggle();
        } else if (filePath == null) {
            toast.show();
            main_report_ui.toggle();
        } else {
            // Report is good to show and open
            viewPdf(filePath);

        }
    }

    /**
     *  Open PDF intent with given file path. User select preferred PDF reader to open generated pdf.
     *
     * @param filePath
     */
    private void viewPdf(String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent pdf = Intent.createChooser(intent, "Open File");
        try {
            context.startActivity(pdf);
            main_report_ui.toggle();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Return PDF name depend on report type + add today's date.
     *
     * @param reportType
     * @return fileName
     */
    private String getPDFname(String reportType) {

        SimpleDateFormat date = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);

        if (reportType.equalsIgnoreCase("MedicineAdministered")) {
            return "Administered-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else if (reportType.equalsIgnoreCase("Stock")) {
            return "Stock-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else if (reportType.equalsIgnoreCase("Purchase")) {
            return "Purchase-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        } else {
            Log.i("PurchaseCheck", "Hit the final statement and has returned null");
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
    private TextLine getTitle(String type, Font titleFont) {

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
    private List<Cell> getHeader(String type, Font f1) {

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
}