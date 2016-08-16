package com.example.jason.mooo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import com.example.jason.mooo.reportTestActivity;
import com.pdfjet.Cell;
import com.pdfjet.TextLine;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2015-10-15.
 */

public class reportTest2 extends ActivityInstrumentationTestCase2<reportTestActivity> {

    reportTestActivity activity;

    public reportTest2() {
        super(reportTestActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testFileExists() {
        assertTrue(activity.getOutputFile().exists());
    }

    @SmallTest
    public void testPdfNameAdministered() {
        SimpleDateFormat date = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);

        String a = "Administered-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        String testCase1 = "MedicineAdministered";

        assertEquals("report name Administer: ", a, activity.getPDFname(testCase1));
    }

    @SmallTest
    public void testPdfNameStock() {
        SimpleDateFormat date = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);

        String s = "Stock-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        String testCase2 = "Stock";

        assertEquals("report name Stock:", s, activity.getPDFname(testCase2));

    }

    @SmallTest
    public void testPdfNamePurchase() {
        SimpleDateFormat date = new SimpleDateFormat("ddMMyy", Locale.ENGLISH);

        String p = "Purchase-" + date.format(Calendar.getInstance().getTime()) + ".pdf";
        String testCase3 = "Purchase";

        assertEquals("report name Purchase:", p, activity.getPDFname(testCase3));
    }

    @SmallTest
    public void testGetTitleAdministered() {

        String testCase = "MedicineAdministered";
        String expect = "Administered Cow Report";
        TextLine t = activity.getTitle(testCase, activity.getTitleFont());

        assertEquals(expect, t.getActualText());
    }

    @SmallTest
    public void testGetTitleStock() {

        String testCase = "Stock";
        String expect = "Medicine Stocktake Report";
        TextLine t = activity.getTitle(testCase, activity.getTitleFont());

        assertEquals(expect, expect);

    }

    @SmallTest
    public void testGetTitlePurchase() {

        String testCase = "Purchase";
        String expect = "Medicine Purchase Report";
        TextLine t = activity.getTitle(testCase, activity.getTitleFont());

        assertEquals(expect, expect);
    }

    @SmallTest
    public void testGetHeaderAdministeredColumnCount() throws Exception {

        String testCase = "MedicineAdministered";
        List<Cell> row = activity.getHeader(testCase,activity.getTitleFont());
        int output = row.size();
        int expect = 6;

        assertEquals( expect , output);
    }

    @SmallTest
    public void testGetHeaderAdministered() throws Exception {

        String testCase = "MedicineAdministered";
        List<Cell> row = activity.getHeader(testCase, activity.getTitleFont());
        String expect[] = {"Cow ID", "Medicine", "Quantity", "Treatment date", "Withholding milk", "Withholding slaughter"};

        for(int i =0; i < row.size(); i++) {
            assertEquals(expect[i], row.get(i).getText());
        }
    }

    @SmallTest
    public void testGetHeaderPurchaseColumnCount() throws Exception {

        String testCase = "Purchase";
        List<Cell> row = activity.getHeader(testCase,activity.getTitleFont());
        int output = row.size();
        int expect = 7;

        assertEquals( expect , output);
    }

    @SmallTest
    public void testGetHeaderPurchase() throws Exception {

        String testCase = "Purchase";
        List<Cell> row = activity.getHeader(testCase, activity.getTitleFont());
        String expect[] = {"Purchase Date", "Medicine", "Quantity", "Batch Number", "Purchase Place", "Expiry Date", "Signed By"};

        for(int i =0; i < row.size(); i++) {
            assertEquals(expect[i], row.get(i).getText());
        }
    }

    @SmallTest
    public void testGetHeaderStockColumnCount() throws Exception {

        String testCase = "Stock";
        List<Cell> row = activity.getHeader(testCase,activity.getTitleFont());
        int output = row.size();
        int expect = 6;

        assertEquals( expect , output);
    }

    @SmallTest
    public void testGetHeaderStock() throws Exception {

        String testCase = "Stock";
        List<Cell> row = activity.getHeader(testCase, activity.getTitleFont());
        String expect[] = {"Purchase Date", "Medicine", "Purchase Amount", "Batch Number", "Expiry Date", "Stock level"};

        for(int i =0; i < row.size(); i++) {
            assertEquals(expect[i], row.get(i).getText());
        }
    }

}