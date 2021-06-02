package com.atandroidlabs.garepair.TabbedFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atandroidlabs.garepair.FragmentAdapter;
import com.atandroidlabs.garepair.ServicePojo;
import com.atandroidlabs.garepair.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcServiceAndRepairFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcServiceAndRepairFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    public AcServiceAndRepairFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcServiceAndRepairFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcServiceAndRepairFragment newInstance(String param1, String param2) {
        AcServiceAndRepairFragment fragment = new AcServiceAndRepairFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Hello","Before Loop");
        List<ServicePojo> acService=new ArrayList<>();
        View view=(View) inflater.inflate(R.layout.fragment_ac_service_and_repair,container,false);
        recyclerView=view.findViewById(R.id.ac_recyclerview);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        adapter=new FragmentAdapter(acService,getContext());
        recyclerView.setAdapter(adapter);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("services").document().collection("AC Service and Repair").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        ServicePojo obj=new ServicePojo();
                        Log.i("hello","in loop");
                        obj.setServiceName(document.get("Name").toString());
                        obj.setWarrenty(document.get("Warrenty").toString());
                        obj.setDuration(document.get("Duration").toString());
                        acService.add(obj);
                    }
                }
                else Log.i("Error","Task failure");
            }
        });
        adapter.notifyDataSetChanged();
        Log.i("Hello","After loop"+acService.size());
        for (int i=0;i<acService.size();i++){
            Log.i("Name",acService.get(i).getServiceName());
            Log.i("Duration",acService.get(i).getDuration());
            Log.i("Warrenty",acService.get(i).getWarrenty());
        }
        return inflater.inflate(R.layout.fragment_ac_service_and_repair, container, false);
    }
}