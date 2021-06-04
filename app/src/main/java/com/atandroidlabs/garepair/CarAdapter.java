package com.atandroidlabs.garepair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter  extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private ArrayList<Car> cars;
    Context context;
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
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carName;
        CarViewHolder(View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.name_of_car);
        }
    }
}
