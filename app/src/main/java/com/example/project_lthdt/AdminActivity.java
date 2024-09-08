package com.example.project_lthdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_lthdt.manageProduct.manageproduct;
import com.example.project_lthdt.manageUsers.ManageUsers;

public class AdminActivity extends AppCompatActivity {
    ImageView imgManageProduct,imgManageUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        imgManageProduct = findViewById(R.id.imgManageProduct);
        imgManageProduct.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, manageproduct.class);
            startActivity(intent);
        });
        imgManageUsers = findViewById(R.id.imgManageUsers);
        imgManageUsers.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, ManageUsers.class);
            startActivity(intent);
        });

    }
}