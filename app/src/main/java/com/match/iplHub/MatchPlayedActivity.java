package com.match.iplHub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.match.iplHub.Model.DataModelMatch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MatchPlayedActivity extends AppCompatActivity {

    Context context;
    String url = Utils.URL + "played";
    DataModelMatch data;
    SwipeRefreshLayout srlRecycleView;
    RecyclerView rvMatchPlayed;
    String id = "";
    LinearLayout ll_no_data_MatchPlayed,llItems;
    MaterialToolbar materialMatchPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_played);
        id = getIntent().getStringExtra("data");
//        int pos = Integer.parseInt(id);
        Log.e("id", id);
        context = MatchPlayedActivity.this;
        fromXml();
        listener();
        getData(id);
    }


    private void fromXml() {
        srlRecycleView = findViewById(R.id.srlRecycleViewMatchPlayed);
        rvMatchPlayed = findViewById(R.id.rvMatchPlayed);
        ll_no_data_MatchPlayed=findViewById(R.id.ll_no_data_MatchPlayed);
        llItems=findViewById(R.id.llItems);
        materialMatchPlayed= findViewById(R.id.mtbMatchPlayed);
    }


    private void listener() {
        srlRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(id);
            }
        });
    }

    public void getData(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                srlRecycleView.setRefreshing(false);
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data = gson.fromJson(response, DataModelMatch.class);
                        setRecyclerView();
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        srlRecycleView.setRefreshing(false);
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("teamId", id);

                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);

    }




    private void setRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvMatchPlayed.setLayoutManager(layoutManager);
        rvMatchPlayed.setHasFixedSize(true);
        rvMatchPlayed.setNestedScrollingEnabled(true);
        MatchPlayedAdapter adapter = new MatchPlayedAdapter(context, data.match,id);
        rvMatchPlayed.setAdapter(adapter);

        if  (adapter.getItemCount() != 0){
            rvMatchPlayed.setAdapter(adapter);
           rvMatchPlayed.setVisibility(View.VISIBLE);

        } else {
            ll_no_data_MatchPlayed.setVisibility(View.VISIBLE);
            ll_no_data_MatchPlayed.setVisibility(View.VISIBLE);

        }

    }

}