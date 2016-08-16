package com.example.jason.mooo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

public class BarcodeScan extends ActionBarActivity {
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private Button scanButton;
    private ImageScanner scanner;
    private TextView result;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    private Button proceedButton;
    private TextView resultText;

    public Camera getCamera() {
        return mCamera;
    }
    public ImageScanner getScanner() {
        return scanner;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        initControls();
    }
    @Override
    public void onBackPressed(){
        finish();
        final Intent test_intent;
        test_intent = new Intent(this, add_new_livestock.class);
        test_intent.putExtra("barcode", "");
        startActivity(test_intent);

    }

    private void initControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(BarcodeScan.this, mCamera, previewCb,
                autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanButton = (Button) findViewById(R.id.ScanButton);
        resultText = (TextView) findViewById(R.id.resultText);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
                resultText.setText("");
            }
        });
        Intent intent = getIntent();
        final Intent test_intent;
        if (intent.getIntExtra("type",0) == 0) {
            test_intent = new Intent(this, add_new_livestock.class);
        } else {
            test_intent = new Intent(this, add_new_chemical.class);
        }
        proceedButton = (Button) findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = resultText.getText().toString();

                test_intent.putExtra("barcode",message.toString());
                //test_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(test_intent);
                finish();
            }
        });

    }
//    protected void onResume() {
//        super.onResume();
//        // TODO Auto-generated method stub
//        // deleting image from external storage
//        //FileHandler.deleteFromExternalStorage();
//        // Create an instance of Camera.
//        if (this.mCamera == null){
//            this.mCamera = getCameraInstance();}
//            this.mCamera = getCameraInstance();}

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // SCAdminTapToScanScreen.isFromAssetDetail = false;
//            releaseCamera();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {

            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
//    private void releaseCamera() {
//        if (mCamera != null) {
//            previewing = false;
//            mCamera.setPreviewCallback(null);
//            mCamera.release();
//            mCamera = null;
//        }
//    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing && mCamera != null)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {

                    Log.i("<<<<<<Asset Code>>>>> ",
                            "<<<<Bar Code>>> " + sym.getData());
                    String scanResult = sym.getData().trim();
                    resultText = (TextView) findViewById(R.id.resultText);
                    resultText.setText(scanResult);
                    Toast.makeText(BarcodeScan.this, scanResult,
                            Toast.LENGTH_SHORT).show();

                    barcodeScanned = true;

                    break;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };


}