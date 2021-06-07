package com.atandroidlabs.garepair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<ServicePojo> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    int TotalPrice=0;
    private TextView priceTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        priceTotal=findViewById(R.id.order_total_textView);
        Intent intent=getIntent();
        list=TabbedActivity.selectedList;
        for (int i=0;i<list.size();i++){
            TotalPrice+=list.get(i).getPrice();
        }
        priceTotal.setText("₹"+TotalPrice);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.cart_recyclerview);
        adapter=new CartAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}