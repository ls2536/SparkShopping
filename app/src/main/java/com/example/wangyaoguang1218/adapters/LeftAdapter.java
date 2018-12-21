package com.example.wangyaoguang1218.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.fragment.Frag03;

import java.util.ArrayList;
import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder1> {

    private List<ShopCar.DataBean> list = new ArrayList<>();


    public void addAll(List<ShopCar.DataBean> data){
        this.list.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.left_item_layout, null);
        ViewHolder1 viewHolder1 = new ViewHolder1(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 viewHolder1, final int i) {
        final ShopCar.DataBean dataBean = list.get(i);
        viewHolder1.text.setText(dataBean.getSellerName());
        viewHolder1.text.setBackgroundResource(dataBean.getBackground());
        viewHolder1.text.setTextColor(dataBean.getTextColor());

        viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int j = 0;j<list.size();j++){
                    list.get(j).setBackground(R.color.grayblack);
                    list.get(j).setTextColor(0xffffffff);
                }
                dataBean.setTextColor(0xff000000);
                dataBean.setBackground(R.color.white);
                notifyDataSetChanged();
                onItemClickListenter.onItemClick(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<ShopCar.DataBean> getList(){
        return list;
    }


    class ViewHolder1 extends RecyclerView.ViewHolder{

        private TextView text;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.left_text);
        }
    }

    private OnItemClickListenter onItemClickListenter;

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }

    public interface OnItemClickListenter{
        void onItemClick(ShopCar.DataBean dataBean);
    }

}
