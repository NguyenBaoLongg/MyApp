package com.example.project_lthdt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    EditText Email,password;
    String textEmail,textPassword;
    TextView changeToRegister;
    ProgressBar progressBar;
    String fullname, email,apiKey,isAdmin;
    SharedPreferences sharedPreferences;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.editText_login_email);
        password = findViewById(R.id.editText_login_password);
        changeToRegister = findViewById(R.id.textView_change_register);
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);

        changeToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
            });
        Button buttonSubmit = findViewById(R.id.button_login);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEmail = Email.getText().toString();
                textPassword = password.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (textEmail.isEmpty() ) {
                    Email.setError("Valid email is required");
                    Email.requestFocus();
                    return;
                }
                else if(textPassword.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

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

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://"+ipConfig.ip+"/MyApp/login.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        fullname = jsonObject.getString("fullname");
                                        email = jsonObject.getString("email");
                                        apiKey = jsonObject.getString("apikey");
                                        isAdmin = jsonObject.getString("isAdmin");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isLogin",true);
                                        editor.putString("fullname", fullname);
                                        editor.putString("email", email);
                                        editor.putString("api_key", apiKey);
                                        editor.apply();
                                        if(isAdmin.equals("1")){
                                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }else{
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }
                                }catch(JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Error"+e.toString(),Toast.LENGTH_LONG).show();
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
                        paramV.put("email", textEmail);
                        paramV.put("password", textPassword);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
        };



};