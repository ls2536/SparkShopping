package com.example.wangyaoguang1218;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ErWeiMaActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView zXingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);
        initView();
    }

    private void initView() {
        zXingView = findViewById(R.id.zxingview);
        zXingView.setDelegate(this);

        //动态权限
        String[] p = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(ErWeiMaActivity.this, p, 1);

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(ErWeiMaActivity.this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            int result = grantResults[0];
            if (result == PackageManager.PERMISSION_GRANTED) {
                //权限同意
                zXingView.startSpot();
            } else {
                //权限拒绝
                Toast.makeText(ErWeiMaActivity.this, "请同意打开摄像头权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
