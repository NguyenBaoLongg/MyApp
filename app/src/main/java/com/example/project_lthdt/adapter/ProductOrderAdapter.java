package com.example.project_lthdt.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.project_lthdt.R;
import com.example.project_lthdt.ipConfig;
import com.example.project_lthdt.manageProduct.EditProduct;
import com.example.project_lthdt.model.GetDataProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductOrderAdapter extends ArrayAdapter<GetDataProduct> {
    ArrayList<GetDataProduct> arrDataProduct;
    Activity context;
    int idLayout;

    public ProductOrderAdapter(  Activity context, int idLayout, ArrayList<GetDataProduct> arrDataProduct) {
        super(context,idLayout, arrDataProduct);
        this.arrDataProduct = arrDataProduct;
        this.context = context;
        this.idLayout = idLayout;
    }


    @Override
    public int getCount() {
        return arrDataProduct.size();
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView name;
        ImageView image;
        TextView price;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(idLayout, null);

            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.textView_title_product);
            viewHolder.image = view.findViewById(R.id.imageProduct);
            viewHolder.price = view.findViewById(R.id.textView_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GetDataProduct getDataProduct = arrDataProduct.get(i);

        viewHolder.name.setText(getDataProduct.getName());
        viewHolder.price.setText(String.valueOf(getDataProduct.getPrice()));
        Glide.with(context)
                .load(getDataProduct.getImage())
                .into(viewHolder.image);

        return view;
    }
}
