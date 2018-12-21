package com.example.wangyaoguang1218.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.core.ShopView;
import com.example.wangyaoguang1218.model.ShopCarModel;

public class ShopCarPresenter {
    private ShopView shopView;

    public ShopCarPresenter(ShopView shopView) {
        this.shopView = shopView;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ShopCar shopCar = (ShopCar) msg.obj;
            shopView.onSuccess(shopCar.getData());
        }
    };

    public void  getShopCar(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopCar shop = (ShopCar) ShopCarModel.getShop();
                Message message = handler.obtainMessage();
                message.obj = shop;
                handler.sendMessage(message);
            }
        }).start();
    }

    //内存泄漏接口
    public void unBindCall(){
        this.shopView = null;
    }
}
