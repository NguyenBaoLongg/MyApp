package com.example.project_lthdt.manageProduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_lthdt.MainActivity;
import com.example.project_lthdt.OrderActivity;
import com.example.project_lthdt.adapter.ProductAdapter;
import com.example.project_lthdt.adapter.ProductOrderAdapter;
import com.example.project_lthdt.ipConfig;
import com.example.project_lthdt.model.GetDataProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.example.project_lthdt.adapter.ProductAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_lthdt.databinding.ActivityManageproductBinding;

import com.example.project_lthdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class manageproduct extends AppCompatActivity {
    ListView listViewProduct;
    ArrayList<GetDataProduct> arrDataProduct;
    ProductOrderAdapter productAdapter;
    FloatingActionButton addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct);
        listViewProduct = findViewById(R.id.lv_products);
        arrDataProduct = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://" + ipConfig.getIp() + "/MyApp/GetData.php";
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
                                    JSONObject product = arr.getJSONObject(i);
                                    int id = product.getInt("id");
                                    String name = product.getString("name");
                                    int price = product.getInt("price");
                                    String image = product.getString("image");
                                    String classify = product.getString("classify");
                                    arrDataProduct.add(new GetDataProduct(id, name, price, image, classify));
                                }

                                productAdapter = new ProductOrderAdapter(manageproduct.this, R.layout.products_in_admin, arrDataProduct);
                                listViewProduct.setAdapter(productAdapter);


                                productAdapter.notifyDataSetChanged();
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

        addProduct = findViewById(R.id.add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AddProduct.class);
                startActivity(it);
            }
        });
    }
}