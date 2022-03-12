package com.ottego.iplHub;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.ottego.iplHub.Model.DataModelPtsTable;

import org.json.JSONException;
import org.json.JSONObject;

public class PointTableActivity extends AppCompatActivity {

    Context context;
    String url = Utils.URL + "get_point_table";
    DataModelPtsTable data;
    SwipeRefreshLayout srlRecycleView;
    RecyclerView rvPointTable;
    MaterialToolbar mtPtsToolBar;
    TextView tv_no_data;
    LinearLayout llRanking, ll_no_data_PointTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_table);
        context = PointTableActivity.this;
        fromXml();
        listener();
        getData("");
    }

    private void fromXml() {
        srlRecycleView = findViewById(R.id.srlRecycleViewPointTable);
        rvPointTable = findViewById(R.id.rvPointTable);
        mtPtsToolBar = findViewById(R.id.mtPtsToolBar);
        ll_no_data_PointTable = findViewById(R.id.ll_no_data_PointTable);
        llRanking = findViewById(R.id.llRanking);
    }

    private void listener() {
        srlRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("");
            }
        });
        mtPtsToolBar.setNavigationOnClickListener(new View.OnClickListener() {
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
                        data = gson.fromJson(jsonObject.getString("data"), DataModelPtsTable.class);
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
        //GridLayoutManager layoutManager = new GridLayoutManager(context, 7);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvPointTable.setLayoutManager(layoutManager);
        rvPointTable.setHasFixedSize(true);
        rvPointTable.setNestedScrollingEnabled(true);
        PtsTableAdapter adapter = new PtsTableAdapter(context, data.point);
        rvPointTable.setAdapter(adapter);


        if (adapter.getItemCount() != 0) {
            rvPointTable.setAdapter(adapter);
            rvPointTable.setVisibility(View.VISIBLE);
            llRanking.setVisibility(View.VISIBLE);

        } else {
            ll_no_data_PointTable.setVisibility(View.VISIBLE);
            ll_no_data_PointTable.setVisibility(View.VISIBLE);

        }
    }
}