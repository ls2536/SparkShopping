package com.example.wangyaoguang1218.model;

import com.example.wangyaoguang1218.bean.Root;
import com.example.wangyaoguang1218.bean.User;
import com.example.wangyaoguang1218.utils.Utils;
import com.google.gson.Gson;

public class DataModel {
    public static User getData(int page){
        String dataResults = Utils.get("http://www.xieast.com/api/news/news.php?page="+page);
        Gson gson = new Gson();
        User user = gson.fromJson(dataResults, User.class);
        return user;
    }
}
