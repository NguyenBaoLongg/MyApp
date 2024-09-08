package com.example.project_lthdt.manageUsers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_lthdt.R;
import com.example.project_lthdt.adapter.ProductOrderAdapter;
import com.example.project_lthdt.adapter.UserAdapter;
import com.example.project_lthdt.ipConfig;
import com.example.project_lthdt.manageProduct.AddProduct;
import com.example.project_lthdt.manageProduct.manageproduct;
import com.example.project_lthdt.model.GetDataProduct;
import com.example.project_lthdt.model.GetDataUsers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageUsers extends AppCompatActivity {

    ListView listViewUser;
    ArrayList<GetDataUsers> arrDataUser;
    FloatingActionButton addUser;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        listViewUser = findViewById(R.id.list_manage_user);
        arrDataUser = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://" + ipConfig.getIp() + "/MyApp/ManageUsers/GetDataUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            JSONArray arr = jsonObject.getJSONArray("result");

                            if (status.equals("success")) {
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject user = arr.getJSONObject(i);
                                    int id = user.getInt("id");
                                    Toast.makeText(ManageUsers.this, user.getString("fullname"), Toast.LENGTH_SHORT).show();
                                    String name = user.getString("fullname");
                                    String email = user.getString("email");
                                    String isAdmin = user.getString("isAdmin");
                                    String gender = user.getString("gender");
                                    String address = user.getString("address");
                                    String mobile = user.getString("mobile");
                                    String isStore = user.getString("isStore");


                                    arrDataUser.add(new GetDataUsers(id, name, email,mobile,address, isAdmin,gender,isStore));
                                }

                                userAdapter = new UserAdapter(ManageUsers.this, R.layout.user_in_admin, arrDataUser);
                                listViewUser.setAdapter(userAdapter);


                                userAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "JSON Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(stringRequest);

//        addUser = findViewById(R.id.add_user);
//        addUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //-----------------------------------------------------------------------
//                Intent it = new Intent(getApplicationContext(), AddProduct.class);
//                startActivity(it);
//            }
//        });
    }

}