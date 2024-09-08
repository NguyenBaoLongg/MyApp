package com.example.project_lthdt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_lthdt.model.GetDataProduct;

import java.util.List;

public class Home extends AppCompatActivity {
    ImageView imageViewChicken;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        imageViewChicken  = findViewById(R.id.imgChicken);
        imageViewChicken.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, OrderActivity.class);
            startActivity(intent);
        });
    }

}