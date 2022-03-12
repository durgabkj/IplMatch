package com.ottego.iplHub;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class About_us extends AppCompatActivity {

    MaterialToolbar mtAboutUsToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        fromXml();
        listener();

    }

    private void fromXml() {
        mtAboutUsToolBar = findViewById(R.id.mtAboutUsToolBar);

    }

    private void listener() {
        mtAboutUsToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}