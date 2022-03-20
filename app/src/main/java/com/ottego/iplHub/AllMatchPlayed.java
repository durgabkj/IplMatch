package com.ottego.iplHub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.DataModelMatch;
import com.ottego.iplHub.Model.MatchModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllMatchPlayed extends AppCompatActivity {
    String url = Utils.URL + "getmatch";
    DataModelMatch data;
    AdView adView;
    Context context;
    RecyclerView rvAll_Played_Match;
    LinearLayout banner_containerAll_Played_Match,ll_no_data_AllMatch;
    SwipeRefreshLayout srlRecycleViewAll_Played_Match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_match_played);

        context = AllMatchPlayed.this;

        listener();
        getData("");
        fromXml();
    }

    private void fromXml() {
        rvAll_Played_Match = findViewById(R.id.rvAll_Played_Match);
        ll_no_data_AllMatch=findViewById(R.id.ll_no_data_AllMatch);
        srlRecycleViewAll_Played_Match = findViewById(R.id.srlRecycleViewAll_Played_Match);
    }

    private void listener() {
//        srlRecycleViewAll_Played_Match.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getData("");
//            }
//        });
    }


    public void getData(String id) {
        // final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                srlRecycleViewAll_Played_Match.setRefreshing(false);
                // progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data = gson.fromJson(String.valueOf(jsonObject.getJSONObject("data")), DataModelMatch.class);
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
                        // progressDialog.dismiss();
                        error.printStackTrace();
//                        srlRecycleViewAll_Played_Match.setRefreshing(false);
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        List<MatchModel> list = new ArrayList<>();

        for (MatchModel m : data.match) {
            if (m.status.equals("1")) {
                list.add(m);
            }
        }
            //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            rvAll_Played_Match.setLayoutManager(layoutManager);
            rvAll_Played_Match.setHasFixedSize(true);
            rvAll_Played_Match.setNestedScrollingEnabled(true);
            AllMatchPlayedAdapter adapter = new AllMatchPlayedAdapter(context,list);
            rvAll_Played_Match.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            rvAll_Played_Match.setAdapter(adapter);
            ll_no_data_AllMatch.setVisibility(View.GONE);
            rvAll_Played_Match.setVisibility(View.VISIBLE);

        } else {

            ll_no_data_AllMatch.setVisibility(View.VISIBLE);
        }

        }
    }

