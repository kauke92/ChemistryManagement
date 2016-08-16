package UnitTest_Trung;

import android.app.ActivityManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import com.example.jason.mooo.BarcodeScan;
import com.example.jason.mooo.R;

import net.sourceforge.zbar.ImageScanner;

import java.util.List;

/**
 * Created by hngu4421 on 2/9/2015.
 */
public class BarcodeScanTest extends ActivityInstrumentationTestCase2<BarcodeScan> {

    BarcodeScan barcodeScan;

    Button proceedButton;
    Button scanButton;
    EditText resultText;
    public BarcodeScanTest() {
        super(BarcodeScan.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        barcodeScan= getActivity();
        proceedButton = (Button) barcodeScan.findViewById(R.id.proceedButton);
        scanButton = (Button) barcodeScan.findViewById(R.id.ScanButton);
        resultText = (EditText) barcodeScan.findViewById(R.id.resultText);
    }

    @SmallTest
    public void test1ShouldNotBeNull() {
        assertNotNull(proceedButton);
        assertNotNull(scanButton);
        assertNotNull(resultText);

    }

    @SmallTest
    public void test2GetCameraInstance() {
        assertNotNull(barcodeScan.getCamera());
    }



    @SmallTest
    public void test3Scanner() {
        ImageScanner scanner = barcodeScan.getScanner();
        assertNotNull(scanner);

        //assertNull(barcodeScan.getCamera());
    }

    @SmallTest
    public void test9OnBackPressed(){

        barcodeScan.onBackPressed();
        Context ctx = barcodeScan.getApplicationContext();
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        boolean isRunning = false;
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                isRunning = true;
        }

        assertTrue(isRunning);
    }
}
