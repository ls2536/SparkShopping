package com.example.wangyaoguang1218.model;

import android.util.Log;

import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.utils.Utils;
import com.google.gson.Gson;

public class ShopCarModel {
    public static ShopCar getShop(){
        String shopData = Utils.get("http://www.zhaoapi.cn/product/getCarts?uid=71");
        Gson gson = new Gson();
        ShopCar shopCar = gson.fromJson(shopData, ShopCar.class);
        return shopCar;

    }
}
