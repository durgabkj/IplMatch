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
import com.ottego.iplHub.Model.MatchModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MatchPlayedAdapter extends RecyclerView.Adapter<MatchPlayedAdapter.MyViewHolder> {
    List<MatchModel> list;
    Context context;
    String id = "";

    public MatchPlayedAdapter(Context context, List<MatchModel> list, String id) {
        this.list = list;
        this.context = context;
        this.id = id;
    }

    @Override
    public MatchPlayedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_played_items, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MatchPlayedAdapter.MyViewHolder holder, int i) {
        final MatchModel model = list.get(i);


// image insertion.......
        if (model.team1.id.equals(id)) {

            holder.tvMatchPlayedTeamName1.setText(model.team1.short_name);
        } else {
            holder.tvMatchPlayedTeamName2.setText(model.team1.short_name);
        }

        if (model.team2.id.equals(id)) {
            holder.tvMatchPlayedTeamName1.setText(model.team2.short_name);
        } else {
            holder.tvMatchPlayedTeamName2.setText(model.team2.short_name);
        }

        if (model.team1.id.equals(id)) {
            Glide.with(holder.ivMatchPlayedImageTeam1)
                    .load(model.team1.team_logo)
                    .into(holder.ivMatchPlayedImageTeam1);
        } else {
            Glide.with(holder.ivMatchPlayedImageTeam2)
                    .load(model.team1.team_logo)
                    .into(holder.ivMatchPlayedImageTeam2);
        }


        // Team name define......
        if (model.team2.id.equals(id)) {
            Glide.with(holder.ivMatchPlayedImageTeam1)
                    .load(model.team2.team_logo)
                    .into(holder.ivMatchPlayedImageTeam1);
        } else {
            Glide.with(holder.ivMatchPlayedImageTeam2)
                    .load(model.team2.team_logo)
                    .into(holder.ivMatchPlayedImageTeam2);
        }

        //Item Background.....
        if (model.win!= null) {
            if (model.win.id.equals(id)) {
                holder.tvMatchPlayedRun.setTextColor(Color.parseColor("#62bf69"));
                //   holder.llMatch_Played_item.setBackgroundColor(Color.parseColor("#e8f5e9"));
                holder.llMatch_Played_item.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.side_border_green));
                holder.tvScore.setText(model.winner_run + "/" + model.winner_wicket + "(" + model.winner_over + ")");
                holder.tvScore1.setText(model.looser_run + "/" + model.looser_wicket + "(" + model.looser_over + ")");
                holder.tvMatchPlayedRun.setText(model.winner_description);
            } else {
                holder.tvMatchPlayedRun.setTextColor(Color.parseColor("#e86478"));
                holder.llMatch_Played_item.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.side_border_red));
                // holder.llMatch_Played_item.setBackgroundColor(Color.parseColor("#ffebee"));
                holder.tvScore.setText(model.looser_run + "/" + model.looser_wicket + "(" + model.looser_over + ")");
                holder.tvScore1.setText(model.winner_run + "/" + model.winner_wicket + "(" + model.winner_over + ")");
                holder.tvMatchPlayedRun.setText(model.looser_description);
                holder.tvMatchPlayedRun.setTextSize(12);
            }
            holder.tvMatchPlayedLocation.setVisibility(View.GONE);
            holder.tvMatchName.setVisibility(View.GONE);
            holder.tvMatchPlayedUpcoming.setVisibility(View.GONE);

       } else{
            holder.llMatch_Played_item.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
            holder.tvMatchPlayedLocation.setVisibility(View.VISIBLE);
            holder.tvMatchName.setVisibility(View.VISIBLE);
            holder.tvMatchPlayedUpcoming.setVisibility(View.VISIBLE);

//            holder.tvMatchPlayedTeamName1.setText(model.team1.short_name);
//            holder.tvMatchPlayedTeamName2.setText(model.team2.short_name);
            holder.tvScore.setText("0/0");
            holder.tvScore1.setText("0/0");
            holder.tvMatchPlayedRun.setText((Utils.getTimeInMonth(model.time) + " | " + (Utils.getDate(model.date))));
//            holder.tvMatchPlayedDate.setText(model.date);
             holder.tvMatchPlayedRun.setTextSize(9);
            holder.tvMatchPlayedRun.setTextColor(Color.parseColor(("#FA3B5A")));
            holder.tvMatchPlayedLocation.setText(model.location);

        }


        if (model.team1.id.equals(id)) {
            ((MatchPlayedActivity) context).materialMatchPlayed.setTitle(model.team1.short_name + "   (All Matches)");
        } else {
            ((MatchPlayedActivity) context).materialMatchPlayed.setTitle(model.team2.short_name + "   (All Matches)");
        }

        long date = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date1 = DateFormat.format(date);

// Show Upcoming and today match

       if((Utils.getDate(model.date).compareTo(date1) == 0))
        {
            holder.tvMatchPlayedUpcoming.setText("Today Match");
            holder.tvMatchPlayedUpcoming.setTextColor(Color.parseColor(("#FF000000")));
        }
       else
       {
           holder.tvMatchPlayedUpcoming.setText("Upcoming Match");

       }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatchPlayedTeamName1, tvMatchPlayedTeamName2, tvMatchPlayedRun, tvScore, tvScore1, tvMatchName, tvMatchPlayedDate, tvMatchPlayedLocation, tvMatchPlayedUpcoming;
        ImageView ivMatchPlayedImageTeam1, ivMatchPlayedImageTeam2;
        LinearLayout llMatch_Played_item;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatchPlayedTeamName1 = itemView.findViewById(R.id.tvMatchPlayedTeamName1);
            tvMatchPlayedTeamName2 = itemView.findViewById(R.id.tvMatchPlayedTeamName2);
            ivMatchPlayedImageTeam1 = itemView.findViewById(R.id.ivMatchPlayedImageTeam1);
            ivMatchPlayedImageTeam2 = itemView.findViewById(R.id.ivMatchPlayedImageTeam2);
            llMatch_Played_item = itemView.findViewById(R.id.llMatch_Played_item);
            tvMatchPlayedRun = itemView.findViewById(R.id.tvMatchPlayedRun);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvScore1 = itemView.findViewById(R.id.tvScore1);
            tvMatchPlayedLocation = itemView.findViewById(R.id.tvMatchPlayedLocation);
            tvMatchPlayedUpcoming = itemView.findViewById(R.id.tvMatchPlayedUpcoming);
            tvMatchName = itemView.findViewById(R.id.tvMatchName);

        }
    }


}
