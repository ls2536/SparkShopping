package com.example.wangyaoguang1218;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.wangyaoguang1218.adapters.MyFragmentAdapter;
import com.example.wangyaoguang1218.fragment.Frag01;
import com.example.wangyaoguang1218.fragment.Frag02;
import com.example.wangyaoguang1218.fragment.Frag03;
import com.example.wangyaoguang1218.fragment.Frag04;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        List<Fragment> list = new ArrayList<>();
        list.add(new Frag01());
        list.add(new Frag02());
        list.add(new Frag03());
        list.add(new Frag04());
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),getBaseContext(),list);
        viewPager.setAdapter(adapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb4:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        radioGroup = findViewById(R.id.radio_group);
    }




}
