package com.ottego.iplHub.fragments;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.ottego.iplHub.Dream11Activity;
import com.ottego.iplHub.Model.DataModelMatch;
import com.ottego.iplHub.Model.MatchModel;
import com.ottego.iplHub.MySingleton;
import com.ottego.iplHub.Prediction;
import com.ottego.iplHub.R;
import com.ottego.iplHub.Today_MatchAdapter;
import com.ottego.iplHub.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayMatchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url = Utils.URL + "getmatch";
    DataModelMatch data;
    TextView tv_no_data_TodayMatch;
    AdView adView;
    MaterialButton mbDream11, mbPrediction;
    RecyclerView rvTodayMatch;
    LinearLayout banner_containerToday, ll_no_data_TodayMatch;
    SwipeRefreshLayout srlRecycleViewTodayMatch;

    private String mParam1;
    private String mParam2;

    public TodayMatchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TodayMatchFragment newInstance(String param1, String param2) {
        TodayMatchFragment fragment = new TodayMatchFragment();
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
        View view = inflater.inflate(R.layout.fragment_today_match, container, false);
        rvTodayMatch = view.findViewById(R.id.rvTodayMatch);
        srlRecycleViewTodayMatch = view.findViewById(R.id.srlRecycleViewTodayMatch);
        ll_no_data_TodayMatch = view.findViewById(R.id.ll_no_data_TodayMatch);
        mbDream11 = view.findViewById(R.id.mbDream11);
        mbPrediction = view.findViewById(R.id.mbPrediction);


        listener();
        getData("");

        AudienceNetworkAds.initialize(getContext());


        // Find the Ad Container
        banner_containerToday = view.findViewById(R.id.banner_containerToday);
        //  AudienceNetworkAds.initialize(this);
        adView = new AdView(getContext(), "293876256047333_293879839380308", AdSize.BANNER_HEIGHT_50);


// Add the ad view to your activity layout
        banner_containerToday.addView(adView);

// Request an ad
        adView.loadAd();
        return view;

    }


    private void listener() {

        srlRecycleViewTodayMatch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData("");
            }
        });

        mbDream11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Dream11Activity.class);
                startActivity(intent);
            }
        });

        mbPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Prediction.class);
                startActivity(intent);
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
        //final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                srlRecycleViewTodayMatch.setRefreshing(false);
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
                    //Toast.makeText(getActivity(), "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();
                        error.printStackTrace();
                        srlRecycleViewTodayMatch.setRefreshing(false);
                        Toast.makeText(getActivity(), "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(getContext()).myAddToRequest(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setRecyclerView() {
        List<MatchModel> list = new ArrayList<>();
        long date = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date1 = DateFormat.format(date);

        for (MatchModel m : data.match) {
            if ((Utils.getDate(m.date).compareTo(date1) == 0)) {
                list.add(m);
            }
        }
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTodayMatch.setLayoutManager(layoutManager);
        rvTodayMatch.setHasFixedSize(true);
        rvTodayMatch.setNestedScrollingEnabled(true);
        Today_MatchAdapter matchAdapter = new Today_MatchAdapter(getContext(), list);
        rvTodayMatch.setAdapter(matchAdapter);

        if (matchAdapter.getItemCount() != 0) {
            rvTodayMatch.setAdapter(matchAdapter);
            ll_no_data_TodayMatch.setVisibility(View.GONE);
            rvTodayMatch.setVisibility(View.VISIBLE);

        } else {

            ll_no_data_TodayMatch.setVisibility(View.VISIBLE);
        }
    }
}