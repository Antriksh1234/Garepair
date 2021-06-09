package com.atandroidlabs.garepair;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter  extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private ArrayList<Car> cars;
    public View mView;
    Context context;
    private int CheckedPostion=0;
    CarAdapter(Context context, ArrayList<Car> cars) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item_layout, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.carName.setText(cars.get(position).name);
        holder.bind(cars.get(position));

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carName;

        CarViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            carName = itemView.findViewById(R.id.name_of_car);
        }
        void bind(final Car obj){
            if(MainActivity.carName.equals(obj.name)){
                itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MainActivity.carName.equals(obj.name)){
                        MainActivity.carName="";
                        itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                    else{
                        itemView.setBackgroundColor(Color.parseColor("#FFFACD"));
                        MainActivity.carName=obj.name;
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
