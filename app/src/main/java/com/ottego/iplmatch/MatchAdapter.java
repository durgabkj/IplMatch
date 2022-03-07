package com.ottego.iplmatch;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.format.DateFormat;
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
import com.ottego.iplmatch.Model.MatchModel;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {
    List<MatchModel> list;
    Context context;
    String time;

    public MatchAdapter(Context context, List<MatchModel> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public MatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iplmatch_item_layout, parent, false);
        return new MatchAdapter.MyViewHolder(view);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.MyViewHolder holder, int i) {
        final MatchModel model = list.get(i);

//        try {
//            if ((Utils.getDateInMilliSecond(model.date)-Calendar.getInstance().getTimeInMillis())<86400000 && model.live. ) {
//
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        long date=Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date1=DateFormat.format(date);


        if ((Utils.getDate(model.date).compareTo(date1)==0) && (model.live.equals("1"))) {
            holder.tvLive.setText("Live");
            holder.tvLive.setVisibility(View.VISIBLE);
            holder.tvLive.setBackgroundColor(Color.parseColor("#db4035"));
        } else if ((Utils.getDate(model.date).compareTo(date1)==0)) {
            holder.tvLive.setText("Today " + Utils.getTimeInMonth(model.time));
            holder.tvLive.setVisibility(View.VISIBLE);
            holder.tvLive.setBackgroundColor(Color.parseColor("#81c784"));
        } else {
            holder.tvLive.setVisibility(View.GONE);
        }

        holder.tvIplTitle.setText("Indian Premier League " + model.id + " Match");
        holder.tvIplDate.setText(Utils.getDate(model.date) + " | ");
        holder.tvIplTime.setText(Utils.getTimeInMonth(model.time));

        holder.tvIplLocation.setText(model.location);
        holder.tvTeamName2.setText(model.team2.short_name);
        holder.tvTeamName1.setText(model.team1.short_name);


        Glide.with(holder.ivIplImageTeam1)
                .load(model.team1.team_logo)
                .into(holder.ivIplImageTeam1);

        Glide.with(holder.ivIplImageTeam2)
                .load(model.team2.team_logo)
                .into(holder.ivIplImageTeam2);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIplTitle, tvIplDate, tvIplTime, tvIplLocation, tvTeamName1, tvTeamName2, tvLive;
        ImageView ivIplImageTeam1, ivIplImageTeam2;
        MaterialCardView mcvUpcomingMatch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIplImageTeam1 = itemView.findViewById(R.id.ivIplImageTeam1);
            ivIplImageTeam2 = itemView.findViewById(R.id.ivIplImageTeam2);
            tvIplTitle = itemView.findViewById(R.id.tvIplTitle);
            tvIplDate = itemView.findViewById(R.id.tvIplDate);
            tvIplTime = itemView.findViewById(R.id.tvIplTime);
            tvTeamName1 = itemView.findViewById(R.id.tvTeamName1);
            tvTeamName2 = itemView.findViewById(R.id.tvTeamName2);
            tvIplLocation = itemView.findViewById(R.id.tvIplLocation);
            mcvUpcomingMatch = itemView.findViewById(R.id.mcvUpcomingMatch);
            tvLive = itemView.findViewById(R.id.tvLive);

        }
    }
}
