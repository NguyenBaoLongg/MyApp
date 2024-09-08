package com.example.project_lthdt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class RegisterActivity extends AppCompatActivity {
    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterMobile, editTextRegisterPassword, editTextRegisterConfirmPassword,editTextRegisterAddress;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private static final String TAG = "RegisterActivity";
    private TextView changeToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Toast to indicate that the activity is open
        Toast.makeText(RegisterActivity.this, "You can register now", Toast.LENGTH_LONG).show();

        // Switch to login activity
        changeToLogin = findViewById(R.id.textView_change_login);
        changeToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize EditTexts and RadioGroup
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPassword = findViewById(R.id.editText_register_confirm_password);
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        editTextRegisterAddress = findViewById(R.id.editText_register_address);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Button
        Button buttonRegister = findViewById(R.id.button_register);

        // Set OnClickListener for the button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPassword = editTextRegisterPassword.getText().toString();
                String textConfirmPassword = editTextRegisterConfirmPassword.getText().toString();
                String textAddress = editTextRegisterAddress.getText().toString();

                // Validation checks
                if (textFullName.isEmpty()) {
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                    return;
                } else if (textEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                    return;
                } else if (textMobile.length() != 10) {
                    editTextRegisterMobile.setError("Mobile number must be 10 digits");
                    editTextRegisterMobile.requestFocus();
                    return;
                } else if (textPassword.isEmpty()) {
                    editTextRegisterPassword.setError("Password is required");
                    editTextRegisterPassword.requestFocus();
                    return;
                } else if (textConfirmPassword.isEmpty()) {
                    editTextRegisterConfirmPassword.setError("Confirm password is required");
                    editTextRegisterConfirmPassword.requestFocus();
                    return;
                } else if (!textPassword.equals(textConfirmPassword)) {
                    editTextRegisterConfirmPassword.setError("Passwords do not match");
                    editTextRegisterConfirmPassword.requestFocus();
                    return;
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (textAddress.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                radioButtonRegisterGenderSelected = findViewById(radioGroupRegisterGender.getCheckedRadioButtonId());
                String textGender = radioButtonRegisterGenderSelected.getText().toString();

                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);

                try {
                    TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }

                                @Override
                                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                }
                            }
                    };

                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                // Volley request
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url = "https://"+ipConfig.ip+"/MyApp/signup.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Register Failed "+ response, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "Error: " + response);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Error: " + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("fullname", textFullName);
                        paramV.put("email", textEmail);
                        paramV.put("mobile", textMobile);
                        paramV.put("password", textPassword);
                        paramV.put("gender", textGender);
                        paramV.put("address",textAddress);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}
