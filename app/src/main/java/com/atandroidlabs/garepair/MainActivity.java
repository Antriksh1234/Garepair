package com.atandroidlabs.garepair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> companyList;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        listView=findViewById(R.id.listView);
//        companyList=new ArrayList<>();
//        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,companyList);
        addToList();
    }
    public void addToList(){
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
    }
}