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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.DataModelMatch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MatchPlayedActivity extends AppCompatActivity {
    private final String TAG = MatchPlayedActivity.class.getSimpleName();
    Context context;
    String url = Utils.URL + "played";
    DataModelMatch data;
    SwipeRefreshLayout srlRecycleView;
    RecyclerView rvMatchPlayed;
    String id = "";
    LinearLayout ll_no_data_MatchPlayed, llItems, llBannerMatchPlayed;
    MaterialToolbar materialMatchPlayed;
    AdView adView;
    private InterstitialAd interstitialAd;

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

        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "293876256047333_294753515959607");
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

        // Find the Ad Container
        llBannerMatchPlayed = findViewById(R.id.llBannerMatchPlayed);
        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(context, "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);


// Add the ad view to your activity layout
        llBannerMatchPlayed.addView(adView);

// Request an ad
        adView.loadAd();
        return;
    }


    private void showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
         Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if(interstitialAd.isAdInvalidated()) {
                    return;
                }
                // Show the ad
                interstitialAd.show();
            }
        }, (long) (1000 * 60 * 0.13333333333333)); // Show the ad after 15 minutes
    }

    @Override
    protected void onDestroy() {
        if (adView!= null) {
            adView.destroy();
        } else if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    private void fromXml() {
        srlRecycleView = findViewById(R.id.srlRecycleViewMatchPlayed);
        rvMatchPlayed = findViewById(R.id.rvMatchPlayed);
        ll_no_data_MatchPlayed = findViewById(R.id.ll_no_data_MatchPlayed);
        llItems = findViewById(R.id.llItems);
        materialMatchPlayed = findViewById(R.id.mtbMatchPlayed);
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
        //  final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                srlRecycleView.setRefreshing(false);
                //   progressDialog.dismiss();
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
                        // progressDialog.dismiss();
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
        MatchPlayedAdapter adapter = new MatchPlayedAdapter(context, data.match, id);
        rvMatchPlayed.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            rvMatchPlayed.setAdapter(adapter);
            rvMatchPlayed.setVisibility(View.VISIBLE);

        } else {
            ll_no_data_MatchPlayed.setVisibility(View.VISIBLE);
            ll_no_data_MatchPlayed.setVisibility(View.VISIBLE);

        }

    }

}