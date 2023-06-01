package com.example.shoppinginschool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private List<Goods> mGoodslist;
    Context mcontext;
    Goods goods;
    public GoodsAdapter(List<Goods> mGoodslist,Context context) {
        this.mGoodslist = mGoodslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_item_goods_show,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        /*holder.goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goodsdata",name);
                parent.getContext().startActivity(intent);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        goods = mGoodslist.get(position);
        holder.goodsImage.setImageBitmap(MainActivity.convertStringToIcon(goods.getGoodsImg()));
        holder.goodsName.setText(goods.getGoodsName());
        holder.goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, GoodsDetailActivity.class);
                intent.putExtra("goodsdata",holder.goodsName.getText());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsName;
        View goodsView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsView = itemView;
            goodsImage = itemView.findViewById(R.id.goods_image);
            goodsName = itemView.findViewById(R.id.goods_name);
        }
    }
}
