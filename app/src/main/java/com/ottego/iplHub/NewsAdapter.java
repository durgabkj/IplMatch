package com.ottego.iplHub;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.NewsModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    List<NewsModel> list;
    Context context;
    Handler setDelay;
    Runnable startDelay;

    public NewsAdapter(Context context, List<NewsModel> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final NewsModel model = list.get(i);
        holder.tvNewsTitle.setText(model.title);
        holder.tvNewsDescription.setText(model.description);
        holder.tvNewsDate.setText(model.date);

        setDelay = new Handler();

        Glide.with(holder.ivNewsItemImage)
                .load(model.photo)
                .into(holder.ivNewsItemImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startDelay = new Runnable() {
                    @Override
                    public void run() {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                Pair.create(holder.ivNewsItemImage, "tnBlogImage"),
                                Pair.create(holder.tvNewsTitle, "tnBlogTitle"),
                                Pair.create(holder.tvNewsDate, "tnNewsDate"));
                        Intent intent = new Intent(context, NewsDetails.class);
                        intent.putExtra("data", new Gson().toJson(model));
                        context.startActivity(intent, options.toBundle());
                    }
                };
                setDelay.postDelayed(startDelay, 1000);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsTitle, tvNewsDescription, tvNewsDate;
        ImageView ivNewsItemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNewsItemImage = itemView.findViewById(R.id.ivBlogItemImage);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvNewsDate = itemView.findViewById(R.id.tvNewsDate);
            tvNewsDescription = itemView.findViewById(R.id.tvNewsDescription);

        }
    }
}
