package com.ottego.iplHub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ottego.iplHub.Model.MatchModel;

import java.util.List;

public class AllMatchPlayedAdapter extends RecyclerView.Adapter<AllMatchPlayedAdapter.MyViewHolder> {
    List<MatchModel> list;
    Context context;

    public AllMatchPlayedAdapter(Context context, List<MatchModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_played_match, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AllMatchPlayedAdapter.MyViewHolder holder, int i) {
        final MatchModel model = list.get(i);


            holder.tvAll_Played_MatchTeamName2.setText(model.team2.short_name);
            holder.tvAll_Played_MatchTeamName1.setText(model.team1.short_name);
            holder.tvAll_Played_MatchRun.setText(model.looser_description);
        holder.tvScoreAllMatchPlayed.setText(model.winner_run + "/" + model.winner_wicket + "(" + model.winner_over + ")");
        holder.tvAllMatchPlayedScore1.setText(model.looser_run + "/" + model.looser_wicket + "(" + model.looser_over + ")");

            Glide.with(holder.ivAll_Played_Match)
                    .load(model.team1.team_logo)
                    .into(holder.ivAll_Played_Match);

            Glide.with(holder.ivAll_Played_MatchImageTeam2)
                    .load(model.team2.team_logo)
                    .into(holder.ivAll_Played_MatchImageTeam2);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAll_Played_MatchTeamName1, tvAll_Played_MatchTeamName2, tvAll_Played_MatchRun, tvScoreAllMatchPlayed, tvAllMatchPlayedScore1;
        ImageView ivAll_Played_Match, ivAll_Played_MatchImageTeam2;
        LinearLayout llMatch_Played_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAll_Played_MatchTeamName1 = itemView.findViewById(R.id.tvAll_Played_MatchTeamName1);
            tvAll_Played_MatchTeamName2 = itemView.findViewById(R.id.tvAll_Played_MatchTeamName2);
            ivAll_Played_Match = itemView.findViewById(R.id.ivAll_Played_Match);
            ivAll_Played_MatchImageTeam2 = itemView.findViewById(R.id.ivAll_Played_MatchImageTeam2);
            llMatch_Played_item = itemView.findViewById(R.id.llMatch_Played_item);
            tvAll_Played_MatchRun = itemView.findViewById(R.id.tvAll_Played_MatchRun);
            tvAllMatchPlayedScore1 = itemView.findViewById(R.id.tvAllMatchPlayedScore1);
            tvScoreAllMatchPlayed = itemView.findViewById(R.id.tvScoreAllMatchPlayed);
        }
    }
}
