package com.atandroidlabs.garepair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = ".MainActivity";
    CircularProgressIndicator indicator;
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Fragment mainFragment;

    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        indicator = findViewById(R.id.loading);

        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            //Need to authenticate, not signed im
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        } else {
            indicator.setVisibility(View.VISIBLE);

            setupFragment();
        }
    }

    OnCompleteListener<DocumentSnapshot> listener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    String orderId = (String) document.get("orderId");
                    if (orderId == null) {
                        Log.d(TAG, "Place Order --------------------------------");
                        indicator.setVisibility(View.INVISIBLE);
                        mainFragment = new PlaceOrderFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
                    } else {
                        //There exists an order and we set up fragment based,
                        setUpFragmentUtil(db, orderId);
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        }
    };

    private void setupFragment() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = user.getUid();
        DocumentReference docRef = db.collection("User").document(id);
        docRef.get().addOnCompleteListener(listener);
    }

    OnCompleteListener<DocumentSnapshot> completeListener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                String status = (String) task.getResult().get("status");
                assert status != null;
                if (status.contentEquals("done")) {
                    indicator.setVisibility(View.INVISIBLE);
                    mainFragment = new AllDoneFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
                    Log.d(TAG, "All Done --------------------------------");
                } else {
                    Log.d(TAG, "Working --------------------------------");
                    indicator.setVisibility(View.INVISIBLE);
                    mainFragment = new WorkingOnItFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
            } else {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setUpFragmentUtil(FirebaseFirestore db, String orderId) {
        DocumentReference orderDoc = db.collection("Orders").document(orderId);
        orderDoc.get().addOnCompleteListener(completeListener);
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
    }

    public void viewStatus(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    public void callOwner(View view) {

    }

    public void openFAQ(View view) {
        Intent intent = new Intent(this, FaqActivity.class);
        startActivity(intent);
    }
}