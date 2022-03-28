package com.ottego.iplHub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.ottego.iplHub.Model.DescriptionModel;
import com.ottego.iplHub.Model.Dream11Model;

import java.util.List;

    public class DescriptionAdapter extends RecyclerView.Adapter<com.ottego.iplHub.DescriptionAdapter.MyViewHolder> {
        List<DescriptionModel> list;
        Context context;

        public DescriptionAdapter( Context context,List<DescriptionModel> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public com.ottego.iplHub.DescriptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_description_iteam, parent, false);
            return new com.ottego.iplHub.DescriptionAdapter.MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull com.ottego.iplHub.DescriptionAdapter.MyViewHolder holder, int i) {
            final DescriptionModel model = list.get(i);

            holder.mbDescription.setText(model.description);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            MaterialButton mbDescription;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mbDescription = itemView.findViewById(R.id.mbDescription);
            }
        }


    }




