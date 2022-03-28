package com.ottego.iplHub;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.gson.Gson;
import com.ottego.iplHub.Model.NewsModel;

public class NewsDetails extends AppCompatActivity {
    private final String TAG = NewsDetails.class.getSimpleName();
    ImageView ivNewsImage, ivNewsDetailsBack;
    TextView tvNewsDetailTitle, tvNewsDescription, getTvNewsDetailDate;
    NewsModel model;
    String id = "";
    Context context;
    private InterstitialAd interstitialAd;
    AdView adView;
    LinearLayout banner_containerNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, NewsModel.class);
        context = NewsDetails.this;
        fromXml();
        listener();
        getData();

        AudienceNetworkAds.initialize(this);


        // Find the Ad Container
        banner_containerNews = findViewById(R.id.banner_containerNews);
        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);


// Add the ad view to your activity layout
        banner_containerNews.addView(adView);
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


    @Override
    protected void onDestroy() {
        if (adView!= null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void fromXml() {
        ivNewsImage = findViewById(R.id.ivNewsImage);
        tvNewsDetailTitle = findViewById(R.id.tvNewsDetailTitle);
        tvNewsDescription = findViewById(R.id.tvNewsDescription);
        getTvNewsDetailDate = findViewById(R.id.tvNewsDetailDate);
        ivNewsDetailsBack = findViewById(R.id.ivNewsDetailsBack);
    }

    private void listener() {
        ivNewsDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        Log.e("data", new Gson().toJson(model));

        Glide.with(context)
                .load(model.photo)
                .into(ivNewsImage);
        tvNewsDetailTitle.setText(model.title);
        tvNewsDescription.setText(model.description);
        getTvNewsDetailDate.setText(Utils.getDate(model.date));


    }
}