package com.atandroidlabs.garepair;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
    List<Service> list;
    private Context mContext;
    private int CheckedPostiion=0;

    public FragmentAdapter(List<Service> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.cart_item_test,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter.ViewHolder holder, int position) {
        Service obj=list.get(position);

//        if (TabbedActivity.selectedList.contains(obj)) {
//            holder.addToCartButton.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.disabled_color));
//            holder.addToCartButton.setText("Remove from cart");
//            holder.addToCartButton.setIconTintResource(R.color.black);
//            holder.addToCartButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.black));
//            holder.addToCartButton.setIconResource(R.drawable.ic_baseline_remove_shopping_cart_24);
//        } else {
//            holder.addToCartButton.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_500));
//            holder.addToCartButton.setText("Add To Cart");
//            holder.addToCartButton.setIconTintResource(R.color.white);
//            holder.addToCartButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.white));
//            holder.addToCartButton.setIconResource(R.drawable.ic_baseline_shopping_cart_24);
//        }

        holder.Name.setText(obj.getServiceName());
        holder.Duration.setText("Estimated Time : "+obj.getDuration());
        holder.Warrenty.setText("Warranty : "+obj.getWarrenty());
        holder.price.setText("₹"+String.valueOf(obj.getPrice()));
        holder.image.setImageDrawable(mContext.getResources().getDrawable(obj.getResource()));

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Service service : list) {
                    if (service == obj) {
                        continue;
                    }
                    service.setSelected(false);
                }
                obj.setSelected(!obj.getIsSelected());
                if (TabbedActivity.selectedList.contains(obj)){
                    TabbedActivity.selectedList.remove(obj);
                }
                else {
                    for (int i = 0; i < getItemCount(); i++) {
                        TabbedActivity.selectedList.remove(list.get(i));
                    }
                    TabbedActivity.selectedList.add(obj);
                }
                notifyDataSetChanged();
            }
        });
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Warrenty, Duration,price;
        public View mView;
        public ImageView image;
        MaterialButton addToCartButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.ServiceName);
            image=(ImageView) itemView.findViewById(R.id.service_select_imageView);
            Warrenty = (TextView) itemView.findViewById(R.id.ServiceWarrenty);
            Duration = (TextView) itemView.findViewById(R.id.ServiceDuration);
            price=(TextView)itemView.findViewById(R.id.service_item_price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
            mView=itemView;
        }
        void bind(final ServicePojo obj){
            if(TabbedActivity.selectedList.contains(obj)){
                addToCartButton.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.disabled_color));
                addToCartButton.setText("Remove from cart");
                addToCartButton.setIconTintResource(R.color.black);
                addToCartButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.black));
                addToCartButton.setIconResource(R.drawable.ic_baseline_remove_shopping_cart_24);
            } else {
                addToCartButton.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.purple_500));
                addToCartButton.setText("Add To Cart");
                addToCartButton.setIconTintResource(R.color.white);
                addToCartButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.white));
                addToCartButton.setIconResource(R.drawable.ic_baseline_shopping_cart_24);
            }
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
//                    if (TabbedActivity.selectedList.contains(obj)){
//                        TabbedActivity.selectedList.remove(obj);
//                        itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    }
//                    else {
//                        for (int i = 0; i < getItemCount(); i++) {
//                            TabbedActivity.selectedList.remove(list.get(i));
//                        }
//                        TabbedActivity.selectedList.add(obj);
//                        if (CheckedPostiion != getAdapterPosition()) {
//                            notifyItemChanged(CheckedPostiion);
//                            CheckedPostiion = getAdapterPosition();
//                        }
//                        Check();
//                    }
//                }
//            });
        }
        public void Check(){
            if(CheckedPostiion==-1){
                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else{
                if(CheckedPostiion==getAdapterPosition()){
                    itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
                }
                else {
                    itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        }
    }
}