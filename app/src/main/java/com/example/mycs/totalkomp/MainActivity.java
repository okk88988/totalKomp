package com.example.mycs.totalkomp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refresh = (Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
            refresh.setVisibility(View.VISIBLE);
        }

        AdView adView = findViewById(R.id.adView);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final TextView scannedResult = findViewById(R.id.barcodeScanned);

        DecoratedBarcodeView decoratedBarcodeView = findViewById(R.id.decoratedBarcodeView);
        CameraSettings cameraSettings = new CameraSettings();
        cameraSettings.setRequestedCameraId(0);
        decoratedBarcodeView.getBarcodeView().setCameraSettings(cameraSettings);

        decoratedBarcodeView.resume();

        decoratedBarcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                scannedResult.setText(result.toString());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }
}