package com.example.shoppinginschool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private final List<Cart> mCartslist;
    Context mcontext;
    Cart cart;
    public CartAdapter(List<Cart> mCartslist,Context context) {
        this.mCartslist = mCartslist;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        cart = mCartslist.get(position);
        holder.cart_img.setImageBitmap(MainActivity.convertStringToIcon(cart.getRecipeImg()));
        holder.cart_name.setText(cart.getRecipeName());
        holder.cart_des.setText(cart.getRecipeDes());
        holder.cart_price.setText("￥"+cart.getRecipePrice()+"元");
        holder.cartView.setOnClickListener(view -> {
            Intent intent = new Intent(mcontext, GoodsDetailActivity.class);
            intent.putExtra("goodsdata",holder.cart_name.getText());
            mcontext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCartslist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cart_check;
        ImageView cart_img;
        TextView cart_name,cart_price,cart_des;
        View cartView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartView = itemView;
            cart_check = itemView.findViewById(R.id.cart_check);
            cart_img = itemView.findViewById(R.id.cart_img);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_des = itemView.findViewById(R.id.cart_des);
        }
    }
}
