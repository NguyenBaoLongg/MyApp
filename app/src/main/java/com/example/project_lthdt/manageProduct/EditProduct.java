package com.example.project_lthdt.manageProduct;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.project_lthdt.AdminActivity;
import com.example.project_lthdt.Home;
import com.example.project_lthdt.LoginActivity;
import com.example.project_lthdt.R;
import com.example.project_lthdt.ipConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProduct extends AppCompatActivity {
    EditText edtName, edtPrice, edtImage;
    String name,price,image,classify,id;
    String nameNew,priceNew,imageNew,classifyNew;
    Button btnEdit;
    RadioGroup radioButtonClassify;
    RadioButton radioButtonNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtImage = findViewById(R.id.edtImage);
        radioButtonClassify = findViewById(R.id.radio_group_classify);
        btnEdit = findViewById(R.id.btnEdit);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            price = bundle.getString("price");
            image = bundle.getString("image");
            classify = bundle.getString("classify");
            edtName.setHint(name);
            edtPrice.setHint(price);
            edtImage.setHint(image);
            String idClassify = "radio_"+classify;
            int idRadio = getResources().getIdentifier(idClassify, "id", getPackageName());
            RadioButton radioButton = findViewById(idRadio);
            radioButton.setChecked(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameNew = edtName.getText().toString();
                priceNew = edtPrice.getText().toString();
                imageNew = edtImage.getText().toString();
                radioButtonNew = findViewById(radioButtonClassify.getCheckedRadioButtonId());
                String textClassifyNew = radioButtonNew.getText().toString();
                if(!nameNew.isEmpty()){
                    name = nameNew;
                }
                if(!priceNew.isEmpty()){
                    price = priceNew;
                }
                if(!imageNew.isEmpty()){
                    image = imageNew;
                }
                if(!textClassifyNew.isEmpty()){
                    classify = textClassifyNew;
                }
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://"+ ipConfig.getIp()+"/MyApp/ManageProducts/EditProduct.php";
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
                        paramV.put("name", name);
                        paramV.put("image",image);
                        paramV.put("price",price);
                        paramV.put("classify",classify);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

}