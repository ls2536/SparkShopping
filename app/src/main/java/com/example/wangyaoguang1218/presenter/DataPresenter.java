package com.example.wangyaoguang1218.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.wangyaoguang1218.bean.Data;
import com.example.wangyaoguang1218.bean.Root;
import com.example.wangyaoguang1218.bean.User;
import com.example.wangyaoguang1218.core.DataView;
import com.example.wangyaoguang1218.model.DataModel;

public class DataPresenter {
    private DataView dataView;

    public DataPresenter(DataView dataView) {
        this.dataView = dataView;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
                User user = (User) msg.obj;
                dataView.onSuccess(user.getData());
        }
    };

    public void getData(final int page){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User data = DataModel.getData(page);
                Message message = handler.obtainMessage();
                message.obj = data;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void unBindCall(){
        this.dataView = null;
    }

}
