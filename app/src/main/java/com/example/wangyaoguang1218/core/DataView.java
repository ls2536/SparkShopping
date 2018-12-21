package com.example.wangyaoguang1218.core;

import com.example.wangyaoguang1218.bean.Data;
import com.example.wangyaoguang1218.bean.User;

import java.util.List;

public interface DataView {

    void onSuccess(List<User.DataBean> user);

    void onFailer(Exception e);
}
