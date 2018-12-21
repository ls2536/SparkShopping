package com.example.wangyaoguang1218.core;

import com.example.wangyaoguang1218.bean.ShopCar;

import java.util.List;

public interface ShopView {
    void  onSuccess(List<ShopCar.DataBean> shopCar);

    void  onFailer(Error e);
}
