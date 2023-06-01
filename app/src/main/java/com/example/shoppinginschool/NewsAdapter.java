package com.example.shoppinginschool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<JsonToBean.ShowapiResBodyDTO.ListDTO> mNewslist;
    private Context mcontext;
    public NewsAdapter( Context mcontext,List<JsonToBean.ShowapiResBodyDTO.ListDTO> mNewslist) {
        this.mNewslist = mNewslist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_news,parent,false);
        ViewHolder myholder = new ViewHolder(view);
        return  myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final JsonToBean.ShowapiResBodyDTO.ListDTO courseinfo =mNewslist.get(position);
        //holder.NewsYear.setText(courseinfo.getYear());
        //holder.NewsMonth.setText(courseinfo.getMonth());
        //holder.NewsDay.setText(courseinfo.getDay());
        holder.NewsTitle.setText(courseinfo.getTitle());
        holder.NewsTitle.setTextColor(Color.parseColor("#732AF6"));
        //Glide.with(mcontext).load(courseinfo.getImg()).into(holder.NewsImg);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,RecipeNewsActivity.class);
                intent.putExtra("data",(Serializable) courseinfo);
                mcontext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mNewslist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView NewsTitle,NewsDay,NewsYear,NewsMonth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NewsTitle = itemView.findViewById(R.id.i_news_title);
            //NewsDay = itemView.findViewById(R.id.i_news_day);
            //NewsYear = itemView.findViewById(R.id.i_news_year);
            //NewsMonth = itemView.findViewById(R.id.i_news_month);
        }
    }
}


