package com.example.wangyaoguang1218.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.core.DTApplication;
import com.example.wangyaoguang1218.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder2> {

    private Context context;
    private List<ShopCar.ListBean> list = new ArrayList<>();


    public void addAll(List<ShopCar.ListBean> data){
        list.addAll(data);

    }


    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.recycler_right_item, null);
        ViewHolder2 viewHolder2 = new ViewHolder2(view);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 viewHolder2, int i) {
        final ShopCar.ListBean listBean = list.get(i);
        viewHolder2.text.setText(listBean.getTitle());
        viewHolder2.price.setText("单价:"+listBean.getPrice());


        String imageurl = "https" + listBean.getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        String[] split = imageurl.split("\\|");
        if (split.length>0){
            Glide.with(DTApplication.getInstance()).load(split[0]).into(viewHolder2.image);
        }


        viewHolder2.addSub.setCount(listBean.getNum());//设置商品数量
        viewHolder2.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                listBean.setNum(count);
                onNumListener.onNum();//计算价格
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearlist(){
        list.clear();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            price = itemView.findViewById(R.id.text_price);
            image = itemView.findViewById(R.id.image);
            addSub = itemView.findViewById(R.id.add_sub_layout);
        }
    }

    private OnNumListener onNumListener;

    public void setOnNumListener(OnNumListener onNumListener) {
        this.onNumListener = onNumListener;
    }

    public interface OnNumListener{
        void onNum();
    }
}
