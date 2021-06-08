package com.atandroidlabs.garepair;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
    List<ServicePojo> list;
    private Context mContext;
    private int CheckedPostion=0;

    public FragmentAdapter(List<ServicePojo> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.choose_service_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter.ViewHolder holder, int position) {
        ServicePojo obj=list.get(position);
        holder.Name.setText(obj.getServiceName());
        holder.Duration.setText("Estimated Time : "+obj.getDuration());
        holder.Warrenty.setText("Warrenty : "+obj.getWarrenty());
        holder.price.setText("₹"+String.valueOf(obj.getPrice()));
        holder.image.setImageDrawable(mContext.getResources().getDrawable(obj.getResource()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.ServiceName);
            image=(ImageView) itemView.findViewById(R.id.service_select_imageView);
            Warrenty = (TextView) itemView.findViewById(R.id.ServiceWarrenty);
            Duration = (TextView) itemView.findViewById(R.id.ServiceDuration);
            price=(TextView)itemView.findViewById(R.id.service_item_price);
            mView=itemView;
        }
        void bind(final ServicePojo obj){
            if(TabbedActivity.selectedList.contains(obj)){
                itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
                    if(TabbedActivity.selectedList.contains(obj)){
                        TabbedActivity.selectedList.remove(obj);
                        itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                    else {
                        for (int i = 0; i < getItemCount(); i++) {
                            if (TabbedActivity.selectedList.contains(list.get(i))) {
                                TabbedActivity.selectedList.remove(list.get(i));
                            }
                        }
                        TabbedActivity.selectedList.add(obj);
                        if (CheckedPostion != getAdapterPosition()) {
                            notifyItemChanged(CheckedPostion);
                            CheckedPostion = getAdapterPosition();
                        }
                        Check();
                    }
                }
            });
        }
        public void Check(){
            if(CheckedPostion==-1){
                itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else{
                if(CheckedPostion==getAdapterPosition()){
                    itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
                }
                else{
                    itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        }
    }
}
