package com.example.shoppinginschool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private String currentID;
    private MyDBopenHelper myDBopenHelper;
    private SQLiteDatabase db,dbReader;
    private  List<Cart> mCartslist;
    private Context mcontext;
    private Cart cart;

    private OnCheckedChangeListener onCheckedChangeListener;
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public CartAdapter(List<Cart> mCartslist,Context context) {
        this.mCartslist = mCartslist;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_detail,parent,false);
        myDBopenHelper = new MyDBopenHelper(mcontext,"GOODS_Database.db",null,1);
        db = myDBopenHelper.getWritableDatabase();
        dbReader = myDBopenHelper.getReadableDatabase();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        cart = mCartslist.get(position);
        holder.cart_img.setImageBitmap(MainActivity.convertStringToIcon(cart.getRecipeImg()));
        holder.cart_id.setText(position+1+"");
        holder.cart_name.setText(cart.getRecipeName());
        holder.cart_des.setText(cart.getRecipeDes());
        holder.cart_price.setText("￥"+cart.getRecipePrice()+"元");
        holder.cartView.setOnClickListener(view -> {
            Intent intent = new Intent(mcontext,GoodsDetailActivity.class);
            intent.putExtra("goodsdata",holder.cart_name.getText());
            mcontext.startActivity(intent);
        });
        /*复选框
       holder.cart_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.cart_check.isChecked()){
                Cursor cursor = dbReader.rawQuery("SELECT _id FROM cart_recipe WHERE recipeName =?",new String[]{cart.getRecipeName()});
                if (cursor.moveToFirst()){
                    do {
                        String itemID= cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        currentID = itemID;
                    }while (cursor.moveToNext());
                }
                db.execSQL("UPDATE cart_recipe SET isCheck = ? WHERE _id = ?",new String[]{"1",currentID});
            }else {
                Cursor cursor = dbReader.rawQuery("SELECT _id FROM cart_recipe WHERE recipeName =?",new String[]{cart.getRecipeName()});
                if (cursor.moveToFirst()){
                    do {
                        String itemID= cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        currentID = itemID;
                    }while (cursor.moveToNext());
                }
                db.execSQL("UPDATE cart_recipe SET isCheck = ? WHERE _id = ?",new String[]{"0",currentID});
            }
        });*/


        ////////////////////////////
        /*holder.cart_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int isCheckValue = isChecked ? 1 : 0;
            Cursor cursor = dbReader.rawQuery("SELECT _id FROM cart_recipe WHERE recipeName =?", new String[]{cart.getRecipeName()});
            if (cursor.moveToFirst()) {
                do {
                    String itemID = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    currentID = itemID;
                } while (cursor.moveToNext());
            }
            db.execSQL("UPDATE cart_recipe SET isCheck = ? WHERE _id = ?", new String[]{String.valueOf(isCheckValue), currentID});
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChange(isChecked);
            }
        });*/

        /*
*
* 删除后没有实现刷新功能，需要重新启动活动才能看到删除后的效果，有待改进。
* 暂且还是用了intent
*
* */
        //长按实现删除功能
        holder.cartView.setOnLongClickListener(v -> {
            TextView itemName = v.findViewById(R.id.cart_name);
            String currentName = itemName.getText().toString();
            Cursor cursor = dbReader.rawQuery("SELECT _id FROM cart_recipe WHERE recipeName =?",new String[]{currentName});
            if (cursor.moveToFirst()){
                do {
                    String itemID= cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    currentID = itemID;
                }while (cursor.moveToNext());
            }
            AlertDialog.Builder mydialog = new AlertDialog.Builder(mcontext);
            mydialog.setTitle("提示")
                    .setTitle("你确定要删除这条数据吗？商品名："+currentName)
                    .setNegativeButton("取消",null)
                    .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.delete("cart_recipe","_id=?",new String[]{currentID});
                            Toast.makeText(mcontext,"删除成功",Toast.LENGTH_SHORT).show();
                            mCartslist.remove(holder.getAdapterPosition());
                            // 通知适配器发生了数据变化
                            notifyDataSetChanged();
                            /*Intent intent = new Intent(mcontext,MainActivity.class);
                            mcontext.startActivity(intent);*/
                        }
                    }).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mCartslist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox cart_check;
        ImageView cart_img;
        TextView cart_name,cart_price,cart_des,cart_id;
        View cartView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartView = itemView;
            cart_check = itemView.findViewById(R.id.cart_check);
            cart_img = itemView.findViewById(R.id.cart_img);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_des = itemView.findViewById(R.id.cart_des);
            cart_id = itemView.findViewById(R.id.cart_id);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(boolean isChecked);
    }

}
