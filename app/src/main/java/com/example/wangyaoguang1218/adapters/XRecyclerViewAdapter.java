package com.example.wangyaoguang1218.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangyaoguang1218.R;
import com.example.wangyaoguang1218.bean.Data;
import com.example.wangyaoguang1218.bean.User;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class XRecyclerViewAdapter extends XRecyclerView.Adapter<XRecyclerViewAdapter.ViewHodler> {

    private Context context;
    private List<User.DataBean> list;

    public XRecyclerViewAdapter(Context context, List<User.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_layout, null);
        ViewHodler viewHodler = new ViewHodler(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        String images = list.get(i).getThumbnail_pic_s();
        String[] split = images.split("\\|");
        if (split.length>0){
            Glide.with(context).load(list.get(i).getThumbnail_pic_s()).into(viewHodler.pic1);
            Glide.with(context).load(list.get(i).getThumbnail_pic_s02()).into(viewHodler.pic2);
            Glide.with(context).load(list.get(i).getThumbnail_pic_s03()).into(viewHodler.pic3);

        }

        viewHodler.titles.setText(list.get(i).getTitle());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodler extends XRecyclerView.ViewHolder{

        private final ImageView pic1;
        private final ImageView pic2;
        private final ImageView pic3;
        private final TextView titles;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            pic1 = itemView.findViewById(R.id.text_pic1);
            pic2 = itemView.findViewById(R.id.text_pic2);
            pic3 = itemView.findViewById(R.id.text_pic3);
            titles = itemView.findViewById(R.id.text_title);
        }
    }
}
