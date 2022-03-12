package com.ottego.iplHub;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.TeamModel;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {

    List<TeamModel> list;
    Context context;

    public TeamsAdapter(Context context, List<TeamModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item_layout, parent, false);
        return new TeamsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamsAdapter.MyViewHolder holder, int i) {
        final TeamModel model = list.get(i);
        holder.tvTeamName.setText(model.team_name + "  (" + model.short_name + ")");


//        Picasso.get()
//                .load(model.photo)
//              .into(holder.ivBlogItemImage);


        Glide.with(holder.ivTeamImage)
                .load(model.team_logo)
                .into(holder.ivTeamImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MatchPlayedActivity.class);
                intent.putExtra("data", model.id);
                Log.e("dataid", model.id);
                context.startActivity(intent);

            }

        });

        holder.mbTeamDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra("data", new Gson().toJson(model));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName;
        ImageView ivTeamImage;
        MaterialButton mbTeamPlayers, mbTeamDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTeamImage = itemView.findViewById(R.id.ivTeamImage);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            mbTeamDescription = itemView.findViewById(R.id.mbTeamDescription);
        }
    }

}
