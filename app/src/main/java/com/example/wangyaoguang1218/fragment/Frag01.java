package com.example.wangyaoguang1218.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.wangyaoguang1218.ErWeiMaActivity;
import com.example.wangyaoguang1218.ImageLoder.GlideImageLoader;
import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.adapters.XRecyclerViewAdapter;
import com.example.wangyaoguang1218.bean.Data;
import com.example.wangyaoguang1218.bean.User;
import com.example.wangyaoguang1218.core.DataView;
import com.example.wangyaoguang1218.presenter.DataPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Frag01 extends Fragment implements View.OnClickListener,DataView {

    private EditText editsearch;
    private ImageView qiehuan;
    private int page = 1;
    private ImageView imgsearch;
    private XRecyclerView xRecyclerView;
    private View view;
    private int i = 0;
    private List<User.DataBean> datalist = new ArrayList<>();
    private XRecyclerViewAdapter xRecyclerViewAdapter;
    private ImageView sao;
    private Banner banner;
    private String[] imgurl = {"https://m.360buyimg.com/n0/jfs/t6700/155/2098998076/156185/6cf95035/595dd5a5Nc3a7dab5.jpg!q70.jpg",
            "https://m.360buyimg.com/n12/jfs/t7768/184/1153704394/148460/f42e1432/599a930fN8a85626b.jpg!q70.jpg",};
    private DataPresenter pesenter;
    private PullToRefreshScrollView pull_listview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_frag_01, container, false);
        initView();
        initViewOper();
        pesenter = new DataPresenter(this);
        pesenter.getData(page);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
       pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
           @Override
           public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
               page=1;
               pesenter.getData(page);
               pull_listview.onRefreshComplete();
           }

           @Override
           public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
               page++;
               pesenter.getData(page);
               pull_listview.onRefreshComplete();
           }
       });

        //轮播图
        DataBanner();
        return view;
    }

    //轮播图
    private void DataBanner() {
        banner.setDelayTime(2000);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(Arrays.asList(imgurl));
        banner.start();
    }

    private void initViewOper() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerViewAdapter = new XRecyclerViewAdapter(getContext(),datalist);
        xRecyclerView.setAdapter(xRecyclerViewAdapter);

    }

    private void initView() {
        qiehuan = view.findViewById(R.id.qiehuan);
        editsearch = view.findViewById(R.id.edit_search);
        imgsearch = view.findViewById(R.id.img_search);
        xRecyclerView = view.findViewById(R.id.xrecycler_view);
        sao = view.findViewById(R.id.sao);
        pull_listview = view.findViewById(R.id.pull_listview);
        banner = view.findViewById(R.id.banner);
        sao.setOnClickListener(this);
        qiehuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qiehuan:
                i++;
                if (i%2==0){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    xRecyclerView.setLayoutManager(linearLayoutManager);
                }else{
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                    xRecyclerView.setLayoutManager(gridLayoutManager);
                }
                xRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.sao:
                Intent intent = new Intent(getActivity(), ErWeiMaActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onSuccess(List<User.DataBean> user) {
        datalist.addAll(user);
        xRecyclerViewAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailer(Exception e) {
        Toast.makeText(getContext(),"shiabi",Toast.LENGTH_SHORT).show();
    }



    //内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
        pesenter.unBindCall();
    }
}
