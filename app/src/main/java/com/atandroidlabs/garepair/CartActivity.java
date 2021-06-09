package com.atandroidlabs.garepair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<ServicePojo> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    int TotalPrice=0;
    private TextView priceTotal;
    private TextView CarName,Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        setPrice();
        setField();
        recyclerView=findViewById(R.id.cart_recyclerview);
        adapter=new CartAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void setPrice(){
        priceTotal=findViewById(R.id.order_total_textView);
        list=TabbedActivity.selectedList;
        for (int i=0;i<list.size();i++){
            TotalPrice+=list.get(i).getPrice();
        }
        priceTotal.setText("₹"+TotalPrice);
    }
    public void setField(){
        CarName=findViewById(R.id.estimated_time_text);
        Time=findViewById(R.id.change_button);
        CarName.setText(MainActivity.brandName+" "+MainActivity.carName);
        int TotalTime=0;
        for(int i=0;i<TabbedActivity.selectedList.size();i++){
            String[] arr=TabbedActivity.selectedList.get(i).getDuration().split(" ");
            if(arr[1].equals("Hours")) TotalTime+=Integer.parseInt(arr[0]);
            else TotalTime+=Integer.parseInt(arr[0])*24;
        }
        int hours=TotalTime%24;
        int days=(int)TotalTime/24;
        if(hours==0 || days==0){
            if(hours==0) Time.setText(days+" Days");
            else Time.setText(hours+" Hours");
        }
        else Time.setText(days+" Days and "+hours+" Hours");
    }
}