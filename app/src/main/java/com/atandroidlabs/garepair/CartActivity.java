package com.atandroidlabs.garepair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<ServicePojo> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent=getIntent();
        list=TabbedActivity.selectedList;
        for (int i=0;i<list.size();i++){
            Log.i("ANS",list.get(i).getServiceName());
        }
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.cart_recyclerview);
        adapter=new CartAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}