package com.atandroidlabs.garepair;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {
    List<ServicePojo> list;
    private Context mContext;

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
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=list.get(position).getServiceName();
                Toast.makeText(view.getContext(),name,Toast.LENGTH_SHORT).show();
                holder.mView.setBackgroundColor(Color.parseColor("#FFFACD"));
                TabbedActivity.selectedList.add(obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Warrenty, Duration;
        public View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.ServiceName);
            Warrenty = (TextView) itemView.findViewById(R.id.ServiceWarrenty);
            Duration = (TextView) itemView.findViewById(R.id.ServiceDuration);
            mView=itemView;
        }
    }
}
