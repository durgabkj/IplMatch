package com.match.iplHub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.match.iplHub.Model.PlayerModel;

import java.util.List;

public class Parent_Adapter extends RecyclerView.Adapter<Parent_Adapter.MyViewHolder> {
    List<PlayerModel> list;
    Context context;
    String id = "";
    String id1 = "";

    public Parent_Adapter(Context context, List<PlayerModel> list, String id, String id1) {
        this.list = list;
        this.context = context;
        this.id = id;
        this.id1 = id1;
    }

    @Override
    public Parent_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_items, parent, false);
        return new Parent_Adapter.MyViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Parent_Adapter.MyViewHolder holder, int i) {
        final PlayerModel model = list.get(i);

        if (model.team_id.equals(id)) {
            holder.tvPlayer_Name.setText(model.player_name);
            holder.tvTeamName_player.setText(model.team_name);
        }

        else if (model.team_id.equals(id1)) {
            holder.tvPlayer_Name.setText(model.player_name);
            holder.tvTeamName_player.setText(model.team_name);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayer_Name,tvTeamName_player;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTeamName_player = itemView.findViewById(R.id.tvTeamName_player);

        }
    }


}
