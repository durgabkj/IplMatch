package com.ottego.iplHub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends AppCompatActivity {

    EditText etFeedbackMobile, etFeedback;
    MaterialButton mbSendFeedback;
    Context context;
    String url = Utils.URL + "feedback";
    String mobile = "";
    String feedbacks = "";
    MaterialToolbar mtFeedbackToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        context = FeedBack.this;
        fromXml();
        listener();
    }


    private void fromXml() {
        etFeedbackMobile = findViewById(R.id.etFeedbackMobile);
        etFeedback = findViewById(R.id.etFeedback);
        mbSendFeedback = findViewById(R.id.mbSendFeedback);
        mtFeedbackToolBar=findViewById(R.id.mtFeedbackToolBar);
    }

    private void listener() {
        mbSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    submitForm();
                }
            }
            });

        mtFeedbackToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

            private boolean checkForm() {
                mobile = etFeedbackMobile.getText().toString().trim();
                feedbacks = etFeedback.getText().toString().trim();


                if (mobile.isEmpty()) {
                    etFeedbackMobile.setError("Please enter mobile number");
                    etFeedbackMobile.setFocusableInTouchMode(true);
                    etFeedbackMobile.requestFocus();
                    return false;
                } else if (!Utils.isValidMobile(mobile)) {
                    etFeedbackMobile.setError("Invalid mobile number");
                    etFeedbackMobile.setFocusableInTouchMode(true);
                    etFeedbackMobile.requestFocus();
                    return false;
                } else if (feedbacks.isEmpty()) {
                    etFeedback.setError("Please give feedback");
                    etFeedback.setFocusableInTouchMode(true);
                    etFeedback.requestFocus();
                    return false;
                } else {
                    etFeedback.setError(null);
                }
                return true;
            }

        private void submitForm () {
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
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            startActivity(intent);
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
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile", mobile);
                    params.put("feedbacks", feedbacks);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
        }
    }
