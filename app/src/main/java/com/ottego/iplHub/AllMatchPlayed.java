package com.ottego.iplHub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.ottego.iplHub.Model.DataModelMatch;
import com.ottego.iplHub.Model.MatchModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllMatchPlayed extends AppCompatActivity {

    private final String TAG = AllMatchPlayed.class.getSimpleName();
    String url = Utils.URL + "getmatch";
    DataModelMatch data;
    AdView adView;
    Context context;
    RecyclerView rvAll_Played_Match;
    LinearLayout banner_containerAll_Played_Match,ll_no_data_AllMatch;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_match_played);

        context = AllMatchPlayed.this;

        getData("");
        fromXml();


        AudienceNetworkAds.initialize(this);



        // Find the Ad Container
        banner_containerAll_Played_Match = findViewById(R.id.banner_containerAll_Played_Match);
        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(context, "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);


// Add the ad view to your activity layout
        banner_containerAll_Played_Match.addView(adView);

// Request an ad
        adView.loadAd();


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

    private void fromXml() {
        rvAll_Played_Match = findViewById(R.id.rvAll_Played_Match);
        ll_no_data_AllMatch=findViewById(R.id.ll_no_data_AllMatch);
    }


    @Override
    public void onDestroy() {
        if (adView != null){
            adView.destroy();
        }
        super.onDestroy();

    }


    public void getData(String id) {
         final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 progressDialog.dismiss();
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
                         progressDialog.dismiss();
                        error.printStackTrace();
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

