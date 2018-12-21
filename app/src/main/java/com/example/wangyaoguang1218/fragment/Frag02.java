package com.example.wangyaoguang1218.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.adapters.CartAdapter;
import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.core.ShopView;
import com.example.wangyaoguang1218.presenter.ShopCarPresenter;

import java.util.List;

public class Frag02 extends Fragment implements ShopView,CartAdapter.TotalPriceListener {

    private View view;
    private ExpandableListView list_cart;
    private CheckBox check_all;
    private TextView goods_sum_price;
    private CartAdapter cartAdapter;
    private ShopCarPresenter shopCarPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_frag_02, container, false);
        initView();
        shopCarPresenter = new ShopCarPresenter(this);
        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartAdapter.checkAll(b);
            }
        });
        cartAdapter = new CartAdapter();
        list_cart.setAdapter(cartAdapter);
        cartAdapter.setTotalPriceListener(this);
        list_cart.setGroupIndicator(null);
        //让group不能点击
        list_cart.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        shopCarPresenter.getShopCar();
        return view;
    }

    private void initView() {
        list_cart = view.findViewById(R.id.list_cart);
        check_all = view.findViewById(R.id.check_all);
        goods_sum_price = view.findViewById(R.id.goods_sum_price);
    }

    @Override
    public void totalPrice(double totalPrice) {
        goods_sum_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onSuccess(List<ShopCar.DataBean> shopCar) {
        cartAdapter.addAll(shopCar);

        int size = shopCar.size();
        for (int i = 0; i<size; i++){
            list_cart.expandGroup(i);
        }

        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailer(Error e) {

    }
}
