package com.atandroidlabs.garepair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyCustomAdapter  extends ArrayAdapter<Brand> {

    public MyCustomAdapter(Context context, ArrayList<Brand> brands) {
        super(context, 0, brands);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView (int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.brand_item_layout, parent, false);
        }

        Brand curr = getItem(position);

        ImageView brandImg = convertView.findViewById(R.id.brand_img);
        TextView brandName = convertView.findViewById(R.id.name_of_brand);

        brandName.setText(curr.name);
        brandImg.setImageResource(curr.img_res);

        return convertView;
    }
}
