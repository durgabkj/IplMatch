package com.ottego.iplHub;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.DataModelMatch;
import com.ottego.iplHub.Model.DataModelTeam;

import org.json.JSONException;
import org.json.JSONObject;

public class TeamActivity extends AppCompatActivity {
    Context context;
    String url = Utils.URL + "getteams";
    DataModelTeam data;
    DataModelMatch data1;
    SwipeRefreshLayout srlRecycleView;
    RecyclerView rvTeam;
    MaterialToolbar mtTeamToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        context = TeamActivity.this;
        fromXml();
        listener();
        getData("");
    }

    private void fromXml() {
        srlRecycleView = findViewById(R.id.srlRecycleViewTeam);
        rvTeam = findViewById(R.id.rvTeam);
        mtTeamToolBar = findViewById(R.id.mtTeamToolBar);

    }

    private void listener() {
        srlRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("");
            }
        });

        mtTeamToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getData(String id) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                srlRecycleView.setRefreshing(false);
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonObject.getString("data"), DataModelTeam.class);
                        setRecyclerView();

                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        srlRecycleView.setRefreshing(false);
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvTeam.setLayoutManager(layoutManager);
        rvTeam.setHasFixedSize(true);
        rvTeam.setNestedScrollingEnabled(true);
        TeamsAdapter adapter = new TeamsAdapter(context, data.teams);
        rvTeam.setAdapter(adapter);


//        if (adapter.getItemCount() != 0) {
//            rvTeam.setAdapter(adapter);
//            n.setVisibility(View.GONE);
//        } else {
//
//            Toast.makeText(context, "Records not available", Toast.LENGTH_SHORT).show();
//        }
    }

}
