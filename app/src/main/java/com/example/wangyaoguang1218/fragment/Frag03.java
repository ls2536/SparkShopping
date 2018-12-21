package com.example.wangyaoguang1218.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.adapters.LeftAdapter;
import com.example.wangyaoguang1218.adapters.RightAdapter;
import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.core.ShopView;
import com.example.wangyaoguang1218.presenter.ShopCarPresenter;

import java.util.ArrayList;
import java.util.List;

public class Frag03 extends Fragment implements View.OnClickListener ,ShopView {

    private View view;
    private RecyclerView leftrecycler;
    private RecyclerView rightrecycler;
    private TextView goods_sum_price;
    private TextView goods_number;
    private List<ShopCar.DataBean> shopCarList = new ArrayList<>();
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private ShopCarPresenter shopCarPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_flag_03, container, false);
        initView();
        initViewOper();
        shopCarPresenter = new ShopCarPresenter(this);
        leftAdapter = new LeftAdapter();
        leftAdapter.setOnItemClickListenter(new LeftAdapter.OnItemClickListenter() {
            @Override
            public void onItemClick(ShopCar.DataBean dataBean) {
                rightAdapter.clearlist();
                rightAdapter.addAll(dataBean.getList());
                rightAdapter.notifyDataSetChanged();
            }
        });
        leftrecycler.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter();
        rightAdapter.setOnNumListener(new RightAdapter.OnNumListener() {
            @Override
            public void onNum() {
                calculatePrice(leftAdapter.getList());
            }
        });
        rightrecycler.setAdapter(rightAdapter);
        shopCarPresenter.getShopCar();
        return view;
    }

    private void initViewOper() {
        leftrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        rightrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initView() {
        leftrecycler = view.findViewById(R.id.left_recycler);
        rightrecycler = view.findViewById(R.id.right_recycler);
        goods_sum_price = view.findViewById(R.id.goods_sum_price);
        goods_number = view.findViewById(R.id.goods_number);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(List<ShopCar.DataBean> shopCar) {
        calculatePrice(shopCar);//计算价格和数量
        leftAdapter.addAll(shopCar);//左边的添加类型

        //得到默认选中的shop，设置上颜色和背景
        ShopCar.DataBean dataBean = shopCar.get(1);
        dataBean.setTextColor(0xff000000);
        dataBean.setBackground(R.color.white);
        rightAdapter.addAll(dataBean.getList());

        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailer(Error e) {

    }

    private void calculatePrice(List<ShopCar.DataBean> shopList){
        double totalPrice=0;
        int totalNum = 0;
        for (int i = 0; i < shopList.size(); i++) {//循环的商家
            ShopCar.DataBean dataBean = shopList.get(i);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                ShopCar.ListBean listBean = dataBean.getList().get(j);
                //计算价格
                totalPrice = totalPrice + listBean.getNum() * listBean.getPrice();
                totalNum+=listBean.getNum();//计数
            }
        }
        goods_sum_price.setText("价格："+totalPrice);
        goods_number.setText(""+totalNum);
    }



    //内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
        shopCarPresenter.unBindCall();
    }
}
