package com.ottego.iplHub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ottego.iplHub.Model.TeamItemModel;

import java.util.List;


public class TeamAdapterNew extends RecyclerView.Adapter<TeamAdapterNew.MyViewHolder> {
    List<TeamItemModel> list;
    Context context;
    String time;

    public TeamAdapterNew(Context context, List<TeamItemModel> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_team_item, parent, false);
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final TeamItemModel model = list.get(i);
        holder.tvTeamItemName.setText(model.name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.rvTeamItem.setLayoutManager(layoutManager);
        holder.rvTeamItem.setHasFixedSize(true);
        holder.rvTeamItem.setNestedScrollingEnabled(true);
        PlayerAdapter adapter = new PlayerAdapter(context, model.members);
        holder.rvTeamItem.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamItemName;
        RecyclerView rvTeamItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTeamItemName = itemView.findViewById(R.id.tvTeamItemName);
            rvTeamItem = itemView.findViewById(R.id.rvTeamItem);

        }
    }
}
