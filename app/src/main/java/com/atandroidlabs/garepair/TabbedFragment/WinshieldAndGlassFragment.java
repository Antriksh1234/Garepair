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
import com.atandroidlabs.garepair.Service;
import com.atandroidlabs.garepair.ServicePojo;
import com.atandroidlabs.garepair.TabbedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinshieldAndGlassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinshieldAndGlassFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Service> list;
    private String type;

    public WinshieldAndGlassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WinshieldAndGlassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinshieldAndGlassFragment newInstance(String param1, String param2) {
        WinshieldAndGlassFragment fragment = new WinshieldAndGlassFragment();
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
        type= TabbedActivity.carType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_winshield_and_glass, container, false);
        list=new ArrayList<>();
        adapter=new FragmentAdapter(list,getContext());
        recyclerView=view.findViewById(R.id.windshield_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore.getInstance().collection("services").document("LzhImDCVx6jyDivEx2z6")
                .collection("Windshield and Glass").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Service obj=new Service();
                        obj.setServiceName(document.get("Name").toString());
                        obj.setWarrenty(document.get("Warrenty").toString());
                        obj.setDuration(document.get("Duration").toString());
                        obj.setResource(R.drawable.glass);
                        obj.setSelected(false);
                        FirebaseFirestore.getInstance().collection("services").document("LzhImDCVx6jyDivEx2z6")
                                .collection("Windshield and Glass").document(document.getId()).collection("GetPrice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                else Log.i("Error","Task Failure");
            }
        });
        return view;
    }
}