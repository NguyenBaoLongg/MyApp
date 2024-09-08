package com.example.project_lthdt.manageProduct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.project_lthdt.LoginActivity;
import com.example.project_lthdt.R;
import com.example.project_lthdt.ipConfig;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    EditText edtName, edtPrice, edtImage;
    String name,price,image,classify;
    RadioGroup radioGroupClassify;
    RadioButton radioButtonClassify;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtImage = findViewById(R.id.edtImage);
        radioGroupClassify = findViewById(R.id.radio_group_classify);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                price = edtPrice.getText().toString();
                image = edtImage.getText().toString();
                radioButtonClassify = findViewById(radioGroupClassify.getCheckedRadioButtonId());
                classify  = radioButtonClassify.getText().toString();
                if(name.isEmpty()){
                    edtName.setError("Yêu cầu điền tên");
                    edtName.requestFocus();
                }
                else if(price.isEmpty()){
                    edtPrice.setError("Yêu cầu điền giá");
                    edtPrice.requestFocus();
                }
                else if(image.isEmpty()){
                    edtImage.setError("Yêu cầu điền link ảnh");
                    edtImage.requestFocus();
                }
                else if(classify.isEmpty()){
                    Toast.makeText(AddProduct.this, "Chọn phân loại", Toast.LENGTH_SHORT).show();
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    String url = "https://"+ ipConfig.getIp()+"/MyApp/ManageProducts/AddProduct.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), manageproduct.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(),  response, Toast.LENGTH_LONG).show();
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
                            paramV.put("name", name);
                            paramV.put("price", price);
                            paramV.put("image", image);
                            paramV.put("classify", classify);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }

            }
        });
    }
}