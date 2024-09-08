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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_lthdt.R;
import com.example.project_lthdt.ipConfig;
import com.example.project_lthdt.manageUsers.PermissionUser;
import com.example.project_lthdt.model.GetDataUsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class UserAdapter extends ArrayAdapter<GetDataUsers> {
    ArrayList<GetDataUsers> arrDataUsers;
    Activity context;
    int idLayout;

    public UserAdapter(  Activity context, int idLayout, ArrayList<GetDataUsers> arrDataUsers) {
        super(context,idLayout, arrDataUsers);
        this.arrDataUsers = arrDataUsers;
        this.context = context;
        this.idLayout = idLayout;
    }


    @Override
    public int getCount() {
        return arrDataUsers.size();
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView name;
        TextView mobile;
        TextView address;
        TextView isAdmin;
        static TextView gender;
        TextView email;
        static TextView isStore;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserAdapter.ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(idLayout, null);

            viewHolder = new UserAdapter.ViewHolder();
            viewHolder.name = view.findViewById(R.id.txt_name);
            viewHolder.email = view.findViewById(R.id.txt_email);
            viewHolder.mobile = view.findViewById(R.id.txt_mobile);
            viewHolder.address = view.findViewById(R.id.txt_address);
            viewHolder.isAdmin = view.findViewById(R.id.txt_isAdmin);
            viewHolder.gender = view.findViewById(R.id.txt_gender);
            ViewHolder.isStore = view.findViewById(R.id.txt_isStore);

            view.setTag(viewHolder);
        } else {
            viewHolder = (UserAdapter.ViewHolder) view.getTag();
        }

        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        GetDataUsers getDataUsers = arrDataUsers.get(i);

        viewHolder.name.setText(getDataUsers.getName());
        viewHolder.email.setText(String.valueOf(getDataUsers.getEmail()));
        viewHolder.mobile.setText(String.valueOf(getDataUsers.getMobile()));
        viewHolder.address.setText(String.valueOf(getDataUsers.getAddress()));
        viewHolder.isAdmin.setText(String.valueOf(getDataUsers.getIsAdmin()));
        ViewHolder.gender.setText(String.valueOf(getDataUsers.getGender()));
        ViewHolder.isStore.setText(String.valueOf(getDataUsers.getIsStore()));


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PermissionUser.class);
                intent.putExtra("id", String.valueOf(getDataUsers.getId()));
                intent.putExtra("name", getDataUsers.getName());
                intent.putExtra("email", String.valueOf(getDataUsers.getEmail()));
                intent.putExtra("mobile", getDataUsers.getMobile());
                intent.putExtra("address", getDataUsers.getAddress());
                intent.putExtra("isAdmin", getDataUsers.getIsAdmin());
                intent.putExtra("gender", getDataUsers.getGender());
                intent.putExtra("isStore",getDataUsers.getIsStore());
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
                                deleteUser(getDataUsers.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return view;
    }

    void deleteUser(int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://"+ ipConfig.getIp()+"/MyApp/ManageProducts/DeleteUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            arrDataUsers.remove(id);
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

