package com.example.project_lthdt.manageUsers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.project_lthdt.R;
import com.example.project_lthdt.ipConfig;
import com.example.project_lthdt.manageProduct.manageproduct;

import java.util.HashMap;
import java.util.Map;

public class PermissionUser extends AppCompatActivity {
    TextView name, email, mobile, address, gender, isAdmin, isStore;
    String id,nameUser, emailUser, mobileUser, addressUser, genderUser, isAdminUser, isStoreUser;
    RadioGroup radioGroupButtonAdmin,radioGroupButtonStore;
    RadioButton radioButtonAdmin,radioButtonStore;
    Button btnPermission;
    String textRadioAdmin, textRadioStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission_user);
        name = findViewById(R.id.txt_name);
        email = findViewById(R.id.txt_email);
        mobile = findViewById(R.id.txt_mobile);
        address = findViewById(R.id.txt_address);
        gender = findViewById(R.id.txt_gender);
        isAdmin = findViewById(R.id.txt_isAdmin);
        isStore = findViewById(R.id.txt_isStore);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("id");
            nameUser = bundle.getString("name");
            emailUser = bundle.getString("email");
            mobileUser = bundle.getString("mobile");
            addressUser = bundle.getString("address");
            genderUser = bundle.getString("gender");
            isAdminUser = bundle.getString("isAdmin");
            isStoreUser = bundle.getString("isStore");

            name.setText(nameUser);
            email.setText(emailUser);
            mobile.setText(mobileUser);
            address.setText(addressUser);
            gender.setText(genderUser);
            isAdmin.setText(isAdminUser);
            isStore.setText(isStoreUser);
        }
        String idAdmin = "radioAdmin_"+isAdminUser;
        int idRadioAdmin = getResources().getIdentifier(idAdmin, "id", getPackageName());
        radioButtonAdmin = findViewById(idRadioAdmin);
        radioButtonAdmin.setChecked(true);
        String idStore = "radioStore_"+isStoreUser;
        int idRadioStore = getResources().getIdentifier(idStore, "id", getPackageName());
        radioButtonStore = findViewById(idRadioStore);
        radioButtonStore.setChecked(true);

        btnPermission = findViewById(R.id.btnPermission);
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonAdmin = findViewById(radioGroupButtonAdmin.getCheckedRadioButtonId());
                textRadioAdmin = radioButtonAdmin.getText().toString();
                textRadioAdmin = textRadioAdmin.equals("true") ? "1" : "0";
                radioButtonStore = findViewById(radioGroupButtonStore.getCheckedRadioButtonId());
                textRadioStore = radioButtonStore.getText().toString();
                textRadioStore = textRadioStore.equals("true") ? "1" : "0";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://"+ ipConfig.getIp()+"/MyApp/ManageUsers/PermissionUser.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Intent intent = new Intent(getApplicationContext(), manageproduct.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Edit Product Failed "+ response, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("id", id);
                        paramV.put("isAdmin", textRadioAdmin);
                        paramV.put("isStore",textRadioStore);

                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}