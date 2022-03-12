package com.ottego.iplHub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.ottego.iplHub.Model.PtsTableModel;

import java.util.List;

public class PtsTableAdapter extends RecyclerView.Adapter<PtsTableAdapter.MyViewHolder> {

    List<PtsTableModel> list;
    Context context;

    public PtsTableAdapter(Context context, List<PtsTableModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_table_item_layout, parent, false);
        return new PtsTableAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PtsTableAdapter.MyViewHolder holder, int i) {
        final PtsTableModel model = list.get(i);
        // holder.tvSerialNo.setText(model.id);
        Glide.with(holder.ivMatchPts)
                .load(model.team.team_logo)
                .into(holder.ivMatchPts);

        holder.tvTeams.setText(model.team.short_name);
        holder.tvMatches.setText(model.matchs);
        holder.tvWon.setText(model.won);
        holder.tvLoss.setText(model.loss);
        holder.tvTie.setText(model.tie);
        holder.tvPts.setText(model.pts);
        holder.tvNRR.setText(model.NRR);

        if (i % 2 == 1) {
            holder.llpts.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.llpts.setBackgroundColor(Color.parseColor("#eeeeee"));
            //holder.llPts.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeams, tvMatches, tvWon, tvLoss, tvTie, tvPts, tvNRR, tvSerialNo;
        TableLayout tlPointTable;
        TableRow trPts;
        MaterialCardView mcvPts;
        ImageView ivMatchPts;
        LinearLayout llpts;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTeams = itemView.findViewById(R.id.tvTeams);
            tvMatches = itemView.findViewById(R.id.tvMatches);
            tvWon = itemView.findViewById(R.id.tvWon);
            tvLoss = itemView.findViewById(R.id.tvLoss);
            tvTie = itemView.findViewById(R.id.tvTie);
            tvPts = itemView.findViewById(R.id.tvPts);
            // tvSerialNo = itemView.findViewById(R.id.tvSerialNo);
            tvNRR = itemView.findViewById(R.id.tvNRR);
            llpts = itemView.findViewById(R.id.llpts);
//            mcvPts=itemView.findViewById(R.id.mcvPts);
            ivMatchPts = itemView.findViewById(R.id.ivMatchPts);
        }
    }
}
