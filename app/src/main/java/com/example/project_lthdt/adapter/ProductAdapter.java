package com.example.project_lthdt.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.project_lthdt.manageProduct.manageproduct;
import com.example.project_lthdt.model.GetDataProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends ArrayAdapter<GetDataProduct> {
    ArrayList<GetDataProduct> arrDataProduct;
    Activity context;
    int idLayout;

    public ProductAdapter(  Activity context, int idLayout, ArrayList<GetDataProduct> arrDataProduct) {
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

        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        GetDataProduct getDataProduct = arrDataProduct.get(i);

        viewHolder.name.setText(getDataProduct.getName());
        viewHolder.price.setText(String.valueOf(getDataProduct.getPrice()));
        Glide.with(context)
                .load(getDataProduct.getImage())
                .into(viewHolder.image);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProduct.class);
                intent.putExtra("id", String.valueOf(getDataProduct.getId()));
                intent.putExtra("name", getDataProduct.getName());
                intent.putExtra("price", String.valueOf(getDataProduct.getPrice()));
                intent.putExtra("image", getDataProduct.getImage());
                intent.putExtra("classify", getDataProduct.getClassify());
                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Gọi hàm xóa sản phẩm khi xác nhận
                        deleteProduct(getDataProduct.getId());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
            }
        });

        return view;
    }

    void deleteProduct(int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://"+ ipConfig.getIp()+"/MyApp/ManageProducts/DeleteProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            arrDataProduct.remove(id);
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "Delete Product Failed "+ response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("id", String.valueOf(id));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }


}

