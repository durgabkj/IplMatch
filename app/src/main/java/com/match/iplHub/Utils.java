package com.match.iplHub;
//https://financetrik.com/app/news/

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class Utils {
 public static String URL = "https://financetrik.com/app/news/";
//public static String URL = "http://10.0.2.2/ipl/news/";
   //public static String URL = "http://192.168.5.120/ipl/news/";
    public static int SERVER_TIMEOUT = 30000;

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public final static boolean isValidMobile(CharSequence target) {
        if (target == null || target.length() != 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static String utfString(String string) {
        try {
            return new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void sendDeviceId(final Context context) {
        final String url_device = Utils.URL + "deviceidset.php";
        //final SessionManager sessionManager = new SessionManager(context);
        SharedPreferences pref = context.getSharedPreferences("firebase_sh", 0);
        final String firebaseId = pref.getString("firebaseid", null);

        class SendDeviceId extends AsyncTask<String, Void, String> {
            protected String doInBackground(String... urls) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_device, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        // params.put("uid", sessionManager.());
                        params.put("deviceid", firebaseId);
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                com.match.iplHub.MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
                return null;
            }
        }
        new SendDeviceId().execute();
    }

    public static String getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(Objects.requireNonNull(sdf.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static Long getDateInMilliSecond(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final long time = sdf.parse(date).getTime();

        return time;
    }


    public static String getTimeInMonth(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm a", Locale.US);

        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(Objects.requireNonNull(sdf.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static String getDateFromMilliSec(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm", Locale.US);
        String dateString = formatter.format(new Date(time));
        return dateString;
    }


    public static void sendData(Context context, String url, Map<String, String> parms) {
        //final SessionManager sessionManager = new SessionManager(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("status");
                    if (code.equalsIgnoreCase("true")) {

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("api_key", sessionManager.getApiKey());
//                params.put("api_secret", sessionManager.getApiSecret());
                params.putAll(parms);

                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        com.match.iplHub.MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}
