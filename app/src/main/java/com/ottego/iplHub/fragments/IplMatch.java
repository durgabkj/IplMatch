package com.ottego.iplHub.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.gson.Gson;
import com.ottego.iplHub.MatchAdapter;
import com.ottego.iplHub.Model.DataModelMatch;
import com.ottego.iplHub.MySingleton;
import com.ottego.iplHub.Player_List_Activity;
import com.ottego.iplHub.R;
import com.ottego.iplHub.Utils;

import org.json.JSONException;
import org.json.JSONObject;


public class IplMatch extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url = Utils.URL + "getmatch";
    DataModelMatch data;
    AdView adView;
    RecyclerView rvIplMatch;
    LinearLayout banner_container;
    SwipeRefreshLayout srlRecycleViewIplMatch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IplMatch() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IplMatch newInstance(String param1, String param2) {
        IplMatch fragment = new IplMatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ipl_match, container, false);
        rvIplMatch = view.findViewById(R.id.rvIplMatch);
        srlRecycleViewIplMatch = view.findViewById(R.id.srlRecycleViewIplMatch);

        listener();
        getData("");

        // Find the Ad Container
        banner_container = view.findViewById(R.id.banner_container);
        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(getContext(), "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);

// Add the ad view to your activity layout
        banner_container.addView(adView);

// Request an ad
        adView.loadAd();
        return view;
    }

    private void listener() {
        srlRecycleViewIplMatch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("");
            }
        });

    }


    @Override
    public void onDestroy() {
        if (adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }


    public void getData(String id) {
       // final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                srlRecycleViewIplMatch.setRefreshing(false);
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
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressDialog.dismiss();
                        error.printStackTrace();
                        srlRecycleViewIplMatch.setRefreshing(false);
                        Toast.makeText(getActivity(), "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(getContext()).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvIplMatch.setLayoutManager(layoutManager);
        rvIplMatch.setHasFixedSize(true);
        rvIplMatch.setNestedScrollingEnabled(true);
        MatchAdapter matchAdapter = new MatchAdapter(getContext(), data.match);
        rvIplMatch.setAdapter(matchAdapter);
    }
}