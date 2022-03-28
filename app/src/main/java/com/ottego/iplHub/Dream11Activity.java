package com.ottego.iplHub;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.DataModelDescription;
import com.ottego.iplHub.Model.DataModelDream11;
import com.ottego.iplHub.Model.DescriptionModel;
import com.ottego.iplHub.fragments.TodayMatchFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class Dream11Activity extends AppCompatActivity {
    Context context;
    String url = Utils.URL + "getDream11_Player";
    String descriptionUrl = Utils.URL + "getDream11_Description";
    AdView adView;
    DataModelDream11 data;
    RecyclerView rvDream11, rvDream11Description;
    DataModelDescription data1;
    LinearLayout banner_container_Dream11,ll_no_data_Dream11,llMatchIteam;
    SwipeRefreshLayout srlRecycleViewDream11;
    MaterialButton mbDescription;
    MaterialToolbar mtDream11ToolBar;

    private InterstitialAd interstitialAd;
    private final String TAG = Dream11Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream11);

        context = Dream11Activity.this;
        rvDream11 = findViewById(R.id.rvDream11);
        rvDream11Description = findViewById(R.id.rvDream11Description);
        srlRecycleViewDream11 = findViewById(R.id.srlRecycleViewDream11);
        mbDescription = findViewById(R.id.mbDescription);
        ll_no_data_Dream11=findViewById(R.id.ll_no_data_Dream11);
        llMatchIteam=findViewById(R.id.llMatchIteam);
        banner_container_Dream11=findViewById(R.id.banner_container_Dream11);
        mtDream11ToolBar=findViewById(R.id.mtDream11ToolBar);

        listener();
        getData("");
        getDescriptionData("");



        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(context, "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);


// Add the ad view to your activity layout
        banner_container_Dream11.addView(adView);

// Request an ad
        adView.loadAd();



        interstitialAd = new InterstitialAd(context, "293876256047333_294753515959607");
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                // interstitialAd.show();
                showAdWithDelay();
            }


            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

    }



    private void showAdWithDelay() {
        /**
         * displaying the ad with delay;
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if (interstitialAd.isAdInvalidated()) {
                    return;
                }
                // Show the ad
                interstitialAd.show();
            }
        }, (long) (1000 * 60 * 0.13333333333333)); // Show the ad after 8 second
    }


    private void listener() {
        srlRecycleViewDream11.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("");
            }
        });
        mtDream11ToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    public void getData(String id) {
        // final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                srlRecycleViewDream11.setRefreshing(false);
                //  progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonObject.getString("data"), DataModelDream11.class);
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
                        srlRecycleViewDream11.setRefreshing(false);
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }


    public void getDescriptionData(String id) {
        // final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, descriptionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // srlRecycleViewDream11.setRefreshing(false);
                //  progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data1 = gson.fromJson(jsonObject.getString("data"), DataModelDescription.class);
                        setRecyclerView1();

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
                       // srlRecycleViewDream11.setRefreshing(false);
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
        rvDream11.setLayoutManager(layoutManager);
        rvDream11.setHasFixedSize(true);
        rvDream11.setNestedScrollingEnabled(true);
        Dream11Adapter adapter = new Dream11Adapter(context, data.dream11);
        rvDream11.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            rvDream11.setAdapter(adapter);
            ll_no_data_Dream11.setVisibility(View.GONE);
            llMatchIteam.setVisibility(View.VISIBLE);

        } else {
            llMatchIteam.setVisibility(View.GONE);
            ll_no_data_Dream11.setVisibility(View.VISIBLE);
        }

    }
    
    private void setRecyclerView1() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvDream11Description.setLayoutManager(layoutManager);
        rvDream11Description.setHasFixedSize(true);
        rvDream11Description.setNestedScrollingEnabled(true);
        DescriptionAdapter adapter = new DescriptionAdapter(context,data1.description);
        rvDream11Description.setAdapter(adapter);
    }
}