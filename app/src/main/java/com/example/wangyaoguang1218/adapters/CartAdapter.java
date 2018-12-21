package com.example.wangyaoguang1218.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.bean.ShopCar;
import com.example.wangyaoguang1218.core.DTApplication;
import com.example.wangyaoguang1218.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseExpandableListAdapter {
    private List<ShopCar.DataBean> list = new ArrayList<>();

    private TotalPriceListener totalPriceListener;


    public CartAdapter(){
    }

    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }



    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View convertView, ViewGroup viewGroup) {
        Holder2 holder2;

        if (convertView == null){
            convertView = View.inflate(viewGroup.getContext(), R.layout.cart2_group_item,null);
            holder2 = new Holder2();
            holder2.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder2);
        }else{
            holder2 = (Holder2) convertView.getTag();
        }
        final ShopCar.DataBean dataBean = list.get(i);
        holder2.checkBox.setText(dataBean.getSellerName());
        holder2.checkBox.setChecked(dataBean.isCheck());
        holder2.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                dataBean.setCheck(checkBox.isChecked());
                List<ShopCar.ListBean> datalist = CartAdapter.this.list.get(i).getList();
                for (int i = 0; i<datalist.size();i++){
                    datalist.get(i).setSelected(checkBox.isChecked()?1:0);
                }
                notifyDataSetChanged();

                //计算价格
                calculatePrice();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
         Holder1 holder1;
        if (convertView == null){
            convertView = View.inflate(viewGroup.getContext(),R.layout.cart_item,null);
            holder1 = new Holder1();
            holder1.text = convertView.findViewById(R.id.text_cart_title);
            holder1.price = convertView.findViewById(R.id.text_price);
            holder1.image = convertView.findViewById(R.id.img_cart_pic);
            holder1.addSub = convertView.findViewById(R.id.add_sub_layout);
            holder1.check = convertView.findViewById(R.id.cart_goods_check);
            convertView.setTag(holder1);
        }else{
          holder1 = (Holder1) convertView.getTag();
        }
        final ShopCar.ListBean listBean = list.get(i).getList().get(i1);
        //商品标题
        holder1.text.setText(listBean.getTitle());
        //商品价格
        holder1.price.setText("单价"+listBean.getPrice());
        //点击选中,计算价格
        holder1.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                listBean.setSelected(checkBox.isChecked()?1:0);
                calculatePrice();//计算价格
            }

        });

        if (listBean.getSelected() == 0){
            holder1.check.setChecked(false);
        }else{
            holder1.check.setChecked(true);
        }

        String imageurl = "https" + listBean.getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        String[] split = imageurl.split("\\|");
        if (split.length>0){
            Glide.with(DTApplication.getInstance()).load(split[0]).into(holder1.image);
        }
        holder1.addSub.setCount(listBean.getNum());//设置商品数量
        holder1.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                listBean.setNum(count);
                calculatePrice();//计算价格
            }
        });
        return convertView;
    }



    /**
     * @author dingtao
     * @date 2018/12/18 7:33 PM
     * 全部选中或者取消
     */
    public void checkAll(boolean isCheck){
        for (int i = 0; i < list.size(); i++) {//循环的商家
            ShopCar.DataBean dataBean = list.get(i);
            dataBean.setCheck(isCheck);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                ShopCar.ListBean listBean= dataBean.getList().get(j);
                listBean.setSelected(isCheck?1:0);
            }
        }
        notifyDataSetChanged();
        calculatePrice();
    }
    //计算总价格
    private void calculatePrice(){
        double totalPrice=0;
        for (int i = 0; i < list.size(); i++) {//循环的商家
            ShopCar.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                ShopCar.ListBean listBean = dataBean.getList().get(j);
                if (listBean.getSelected()==1) {//如果是选中状态
                    totalPrice = totalPrice + listBean.getNum() * listBean.getPrice();
                }
            }
        }
        if (totalPriceListener!=null)
            totalPriceListener.totalPrice(totalPrice);
    }

    public void addAll(List<ShopCar.DataBean> data) {
        if (data != null)
            list.addAll(data);
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    class Holder1 {
        CheckBox check;
        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;
    }

    class Holder2{
        CheckBox checkBox;
    }
    public interface TotalPriceListener{
        void totalPrice(double totalPrice);
    }
}
