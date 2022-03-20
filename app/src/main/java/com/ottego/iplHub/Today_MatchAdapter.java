package com.ottego.iplHub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.ottego.iplHub.Model.MatchModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Today_MatchAdapter extends RecyclerView.Adapter<Today_MatchAdapter.MyViewHolder> {
    List<MatchModel>list;
    Context context;

    public Today_MatchAdapter(Context context, List<MatchModel> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public Today_MatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_match_layout, parent, false);
        return new Today_MatchAdapter.MyViewHolder(view);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final MatchModel model = list.get(i);

        holder.tvTodayMatchTitle.setText("Indian Premier League " + model.id + " Match");
        holder.tvTodayMatchDate.setText((Utils.getDate(model.date) + " | "));
        holder.tvTodayMatchTime.setText(Utils.getTimeInMonth(model.time));

        holder.tvTodayMatchLocation.setText(model.location);
        holder.tvTodayMatchTeamName2.setText(model.team2.short_name);
        holder.tvTodayMatchTeamName1.setText(model.team1.short_name);


        Glide.with(holder.ivTodayMatchImageTeam1)
                .load(model.team1.team_logo)
                .into(holder.ivTodayMatchImageTeam1);


        Glide.with(holder.ivTodayMatchImageTeam2)
                .load(model.team2.team_logo)
                .into(holder.ivTodayMatchImageTeam2);

        long date = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date1 = DateFormat.format(date);


        if ((Utils.getDate(model.date).compareTo(date1) == 0) && (model.live.equals("1"))) {
            holder.tvLiveTodayMatch.setText("Live");
            holder.tvLiveTodayMatch.setVisibility(View.VISIBLE);
            holder.tvLiveTodayMatch.setBackgroundColor(Color.parseColor("#db4035"));
        } else {
            holder.tvLiveTodayMatch.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Player_List_Activity.class);
                intent.putExtra("data", model.team1.id);
                intent.putExtra("data1", model.team2.id);
                Log.e("dataid", model.id);
                context.startActivity(intent);

            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTodayMatchTitle, tvTodayMatchDate, tvTodayMatchTime, tvTodayMatchLocation, tvTodayMatchTeamName1, tvTodayMatchTeamName2, tvLiveTodayMatch;
        ImageView ivTodayMatchImageTeam1, ivTodayMatchImageTeam2;
        MaterialCardView mcvTodayMatch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTodayMatchImageTeam1 = itemView.findViewById(R.id.ivTodayMatchImageTeam1);
            ivTodayMatchImageTeam2 = itemView.findViewById(R.id.ivTodayMatchImageTeam2);
            tvTodayMatchTitle = itemView.findViewById(R.id.tvTodayMatchTitle);
            tvTodayMatchDate = itemView.findViewById(R.id.tvTodayMatchDate);
            tvTodayMatchTime = itemView.findViewById(R.id.tvTodayMatchTime);
            tvTodayMatchTeamName1 = itemView.findViewById(R.id.tvTodayMatchTeamName1);
            tvTodayMatchTeamName2 = itemView.findViewById(R.id.tvTodayMatchTeamName2);
            tvTodayMatchLocation = itemView.findViewById(R.id.tvTodayMatchLocation);
            mcvTodayMatch = itemView.findViewById(R.id.mcvTodayMatch);
            tvLiveTodayMatch = itemView.findViewById(R.id.tvLiveTodayMatch);
        }


    }
}
