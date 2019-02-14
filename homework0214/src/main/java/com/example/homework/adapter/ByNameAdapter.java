package com.example.homework.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homework.R;
import com.example.homework.bean.ByName;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ByNameAdapter extends RecyclerView.Adapter<ByNameAdapter.ViewHolder> {

    private List<ByName.ResultBean> list;
    private Context context;

    public ByNameAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setData(List<ByName.ResultBean> datas) {
        list.clear();
        if (datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_pin,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        String masterPic = list.get(i).getMasterPic();
        Uri parse = Uri.parse(masterPic);
        viewHolder.imageView.setImageURI(parse);
        viewHolder.name.setText(list.get(i).getCommodityName());
        viewHolder.price.setText(list.get(i).getPrice()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.setonclicklisener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView imageView;
        private TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
    private ByNameAdapter.Cicklistener listener;

    public void result(ByNameAdapter.Cicklistener listener) {
        this.listener = listener;
    }
    public interface Cicklistener {
        void setonclicklisener(int index);
    }
}