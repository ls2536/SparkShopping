package com.example.wangyaoguang1218.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.example.wangyaoguang1218.R;

public class Frag04 extends Fragment implements View.OnClickListener {
    private AMapLocationClient mapLocationClient;
    private View view;
    private TextView text_dingwei;
    private Button btn_dingwei;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_flag_04, container, false);
        initView();
        //声明AMapLocationClient类对象
        //初始化定位
        mapLocationClient = new AMapLocationClient(getContext());
        //异步获取定位结果
        AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //解析定位结果
                        text_dingwei.setText(amapLocation.getAddress());
                        mapLocationClient.stopLocation();
                    }
                }
            }
        };
        //设置定位回调监听
        mapLocationClient.setLocationListener(mAMapLocationListener);


        return view;
    }

    private void initView() {
        text_dingwei = view.findViewById(R.id.text_dingwei);
        btn_dingwei = view.findViewById(R.id.btn_dingwei);
        btn_dingwei.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_dingwei){
            //启动定位
            mapLocationClient.startLocation();
        }
    }

    //请求允许的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            int result = grantResults[0];
            if (result == PackageManager.PERMISSION_GRANTED) {
                //权限同意
                mapLocationClient.startLocation();
            } else {
                //权限拒绝
                Toast.makeText(getActivity(), "请同意定位权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
