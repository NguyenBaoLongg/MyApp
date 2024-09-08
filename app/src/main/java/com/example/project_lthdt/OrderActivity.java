package com.example.project_lthdt;
import android.os.Bundle;
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
import com.example.project_lthdt.adapter.ProductAdapter;
import com.example.project_lthdt.adapter.ProductOrderAdapter;
import com.example.project_lthdt.model.GetDataProduct;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;


public class OrderActivity extends AppCompatActivity {
    ListView listViewOrder;
    ArrayList<GetDataProduct> arrDataProduct;
    ProductOrderAdapter productOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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

                                // Thiết lập adapter cho ListView
                                productOrderAdapter = new ProductOrderAdapter(OrderActivity.this,R.layout.item_product ,arrDataProduct);
                                listViewOrder = findViewById(R.id.listViewOrder);
                                listViewOrder.setAdapter(productOrderAdapter);
                                productOrderAdapter.notifyDataSetChanged();

                            } else {
                                // Xử lý khi có lỗi từ server
                                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Xử lý lỗi phân tích JSON
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
    }

}