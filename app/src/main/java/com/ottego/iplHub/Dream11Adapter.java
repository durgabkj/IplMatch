package com.ottego.iplHub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ottego.iplHub.Model.Dream11Model;
import com.ottego.iplHub.Model.MatchModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

    public class Dream11Adapter extends RecyclerView.Adapter<Dream11Adapter.MyViewHolder> {
        List<Dream11Model> list;
        Context context;

        public Dream11Adapter( Context context,List<Dream11Model> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public com.ottego.iplHub.Dream11Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dream11_iteam, parent, false);
            return new com.ottego.iplHub.Dream11Adapter.MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull com.ottego.iplHub.Dream11Adapter.MyViewHolder holder, int i) {
            final Dream11Model model = list.get(i);

                holder.tvDream11PlayerName.setText(model.name);

                Glide.with(holder.ivDream11PlayerImage)
                        .load(model.photo)
                        .into(holder.ivDream11PlayerImage);
            }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvDream11PlayerName;
            ImageView ivDream11PlayerImage;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDream11PlayerName = itemView.findViewById(R.id.tvDream11PlayerName);
                ivDream11PlayerImage = itemView.findViewById(R.id.ivDream11PlayerImage);
            }
        }


    }


