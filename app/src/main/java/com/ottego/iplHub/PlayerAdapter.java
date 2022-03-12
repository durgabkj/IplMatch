package com.ottego.iplHub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ottego.iplHub.Model.PlayerModel;

import java.util.List;


public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {
    List<PlayerModel> list;
    Context context;


    public PlayerAdapter(Context context, List<PlayerModel> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_items, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final PlayerModel model = list.get(i);
        if (model.status.equals("1")) {
            holder.tvTeamName_player.setText(model.player_name);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName_player;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamName_player = itemView.findViewById(R.id.tvTeamName_player);
        }
    }


}
