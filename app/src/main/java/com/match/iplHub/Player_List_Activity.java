package com.match.iplHub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.match.iplHub.Model.DataModelPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class Player_List_Activity extends AppCompatActivity {
    private final String TAG = Player_List_Activity.class.getSimpleName();
    Context context;
    String url = Utils.URL + "get_player";
    DataModelPlayer data;
    MaterialToolbar mtPlayerListToolBar;
    RecyclerView idRVPlayer;
    String id = "";
    String id1 = "";

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        // Inflate the layout for this fragment


        id = getIntent().getStringExtra("data");
        id1 = getIntent().getStringExtra("data1");
       Log.e("data",id);
        Log.e("data1",id1);
        context = Player_List_Activity.this;
      fromXml();
       //listener();
        getData(id,id1);

        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "1065267967364028_1065619153995576");
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


    private void fromXml() {
        idRVPlayer = findViewById(R.id.idRVPlayer);
    }


    private void showAdWithDelay () {
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
        }, (long) (1000 * 60*0.13333333333333)); // Show the ad after 8 second
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }



    public void getData(String id,String id1) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"/"+id+"/"+id1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");

                    if (code.equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonObject.getString("data"), DataModelPlayer.class);
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
                        error.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("teamId1", id);
//                params.put("teamId2",id1);
//
//                return params;
//            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
         // GridLayoutManager layoutManager = new GridLayoutManager(context, 2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        idRVPlayer.setLayoutManager(layoutManager);
        idRVPlayer.setHasFixedSize(true);
        idRVPlayer.setNestedScrollingEnabled(true);
        TeamAdapterNew adapter = new TeamAdapterNew(context, data.getTeamList());
        idRVPlayer.setAdapter(adapter);

    }
}