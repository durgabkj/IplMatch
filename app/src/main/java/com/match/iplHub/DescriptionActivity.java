package com.match.iplHub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.match.iplHub.Model.TeamModel;

public class DescriptionActivity extends AppCompatActivity {
    Context context;
    TeamModel model;
    TextView tvDescription, tvTeamNameDescription;
    MaterialToolbar mtDescriptionToolBar;
    ImageView ivDescriptionTeamLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, TeamModel.class);

        context = DescriptionActivity.this;


        fromXml();
        listener();
        setPreloadData();



    }

    private void fromXml() {
        tvDescription = findViewById(R.id.tvDescription);
        mtDescriptionToolBar = findViewById(R.id.mtDescriptionToolBar);
        ivDescriptionTeamLogo = findViewById(R.id.ivDescriptionTeamLogo);
        tvTeamNameDescription=findViewById(R.id.tvTeamNameDescription);
    }

    private void listener() {
        mtDescriptionToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setPreloadData() {
        Log.e("data", new Gson().toJson(model));
        Glide.with(context)
                .load(model.team_logo)
                .into(ivDescriptionTeamLogo);
        tvDescription.setText(model.team_desc);
       tvTeamNameDescription.setText(model.team_name+" ("+model.short_name+")");

    }
}