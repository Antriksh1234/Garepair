package com.atandroidlabs.garepair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<ServicePojo> list;
    private Context mContext;
    public CartAdapter(Context context,List<ServicePojo> list){
        this.mContext=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.cart_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicePojo obj=list.get(position);
        holder.name.setText(obj.getServiceName());
        holder.duration.setText("Duration : "+obj.getDuration());
        holder.warrenty.setText("Warrenty : "+obj.getWarrenty());
        holder.price.setText("₹"+obj.getPrice());
        holder.image.setImageDrawable(mContext.getResources().getDrawable(obj.getResource()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,duration,warrenty,price;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=(TextView)itemView.findViewById(R.id.cart_item_price);
            image=(ImageView)itemView.findViewById(R.id.cart_item_image);
            name=(TextView)itemView.findViewById(R.id.service_name_cart);
            duration=(TextView)itemView.findViewById(R.id.service_duration_cart);
            warrenty=(TextView)itemView.findViewById(R.id.service_warrenty_cart);
        }
    }
}
