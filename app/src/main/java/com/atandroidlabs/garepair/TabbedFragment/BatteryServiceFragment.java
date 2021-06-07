package com.atandroidlabs.garepair.TabbedFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atandroidlabs.garepair.FragmentAdapter;
import com.atandroidlabs.garepair.R;
import com.atandroidlabs.garepair.ServicePojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BatteryServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BatteryServiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    List<ServicePojo> list;
    RecyclerView.Adapter adapter;
    private String type;

    public BatteryServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BatteryServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BatteryServiceFragment newInstance(String param1, String param2) {
        BatteryServiceFragment fragment = new BatteryServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        type="XUV";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=(View)inflater.inflate(R.layout.fragment_battery_service,container,false);
        list=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.battery_recyclerview);
        adapter=new FragmentAdapter(list,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("services").document("LzhImDCVx6jyDivEx2z6")
                .collection("Battery Service").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        ServicePojo obj=new ServicePojo();
                        obj.setServiceName(document.get("Name").toString());
                        obj.setWarrenty(document.get("Warrenty").toString());
                        obj.setDuration(document.get("Duration").toString());
                        firebaseFirestore.collection("services").document("LzhImDCVx6jyDivEx2z6")
                                .collection("Battery Service").document(document.getId()).collection("GetPrice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                for(QueryDocumentSnapshot doc : task1.getResult()){
                                    if (doc.get("Type").equals(type)){
                                        obj.setPrice(Integer.parseInt(doc.get("Price").toString()));
                                        list.add(obj);
                                        adapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        });
                    }
                }
                else{
                    Log.i("Error","Task Failure");
                }
            }
        });
        return view;
    }
}