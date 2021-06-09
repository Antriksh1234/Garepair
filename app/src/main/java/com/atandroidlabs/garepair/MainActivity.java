package com.atandroidlabs.garepair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = ".MainActivity";
    CircularProgressIndicator indicator;
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Fragment mainFragment;
    ArrayList<Brand> brands;
    private Spinner spinner;
    private AlertDialog dialog;
    public static String brandName="",carName="",type="";

    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        brands = getBrandList();
        View view = LayoutInflater.from(this).inflate(R.layout.select_car_layout, null, false);
        spinner = view.findViewById(R.id.car_select_spinner);
        CircularProgressIndicator indicator = view.findViewById(R.id.circle_indicator);
        RecyclerView recyclerView = view.findViewById(R.id.cars_rcv);
        indicator.setVisibility(View.INVISIBLE);
        MyCustomAdapter adapter = new MyCustomAdapter(getApplicationContext(), brands);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = (Brand) adapterView.getItemAtPosition(i);
                indicator.setVisibility(View.VISIBLE);
                brandName=brand.name;
                showCarsOfBrand(brand.name, indicator, recyclerView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void showCarsOfBrand(String brandName, CircularProgressIndicator indicator, RecyclerView recyclerView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cars").document("RuBANTgnmgI3kXWl1Elu").collection(brandName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Car> cars = new ArrayList<>();
                            for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                //Toast.makeText(MainActivity.this, "doc found", Toast.LENGTH_SHORT).show();
                                if (document.contains("Name")) {
                                    cars.add(new Car((String) document.get("Name")));
                                }
                            }
                            //Toast.makeText(MainActivity.this, cars.size() + "", Toast.LENGTH_SHORT).show();
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new CarAdapter(getApplicationContext(), cars));
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        indicator.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private ArrayList<Brand> getBrandList() {
        ArrayList<Brand> brands = new ArrayList<>();
        brands.add(new Brand("Ashok Leyland", R.drawable.ashok_leyland));
        brands.add(new Brand("Audi", R.drawable.audi));
        brands.add(new Brand("BMW", R.drawable.bmw));
        brands.add(new Brand("Chevrolet", R.drawable.chervolet));
        brands.add(new Brand("Daewoo", R.drawable.daewoo));
        brands.add(new Brand("Datsun", R.drawable.datsun));
        brands.add(new Brand("Fiat", R.drawable.fiat));
        brands.add(new Brand("Force Motors", R.drawable.force_motors));
        brands.add(new Brand("Ford", R.drawable.ford));
        brands.add(new Brand("Honda", R.drawable.honda));
        brands.add(new Brand("Hyundai", R.drawable.hyundai));
        brands.add(new Brand("ICML", R.drawable.icml));
        brands.add(new Brand("Isuzu", R.drawable.isuzu));
        brands.add(new Brand("Jaguar", R.drawable.jaguar));
        brands.add(new Brand("Jeep", R.drawable.jeep));
        brands.add(new Brand("Kia", R.drawable.kia));
        brands.add(new Brand("Lamborghini", R.drawable.lamboghini));
        brands.add(new Brand("Lexus", R.drawable.lexus));
        brands.add(new Brand("MG", R.drawable.mg));
        brands.add(new Brand("Mahindra and Mahindra", R.drawable.mahindra));
        brands.add(new Brand("Maruti Suzuki", R.drawable.maruti_suzuki));
        brands.add(new Brand("Nisaan", R.drawable.nisaan));
        brands.add(new Brand("Porsche", R.drawable.porsche));
        brands.add(new Brand("Premier", R.drawable.premier));
        brands.add(new Brand("Renault", R.drawable.renault));
        brands.add(new Brand("Reva", R.drawable.reva));
        brands.add(new Brand("Skoda", R.drawable.skoda));
        brands.add(new Brand("Tata", R.drawable.tata));
        brands.add(new Brand("Toyota", R.drawable.toyota));
        brands.add(new Brand("Volkswagen", R.drawable.volkswagen));

        return brands;
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
        if(type.equals("")){
            Toast.makeText(getApplicationContext(),"Select a Car!",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, TabbedActivity.class);
            intent.putExtra("Type", type);
            startActivity(intent);
        }
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
    public void selectCar(View view){
        if(brandName.equals("") || carName.equals("")){
            Toast.makeText(getApplicationContext(),"Select a Car!",Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseFirestore.getInstance().collection("cars").document("RuBANTgnmgI3kXWl1Elu").collection(brandName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            if(document.get("Name").equals(carName)){
                                type= String.valueOf(document.get("Type"));
                                break;
                            }
                        }
                    }
                    else{
                        Log.i("Error","Task is Failed!");
                    }
                }
            });
            dialog.hide();
            TextView brand=(TextView)findViewById(R.id.brand_name);
            TextView car=(TextView)findViewById(R.id.car_name);
            brand.setText(brandName);
            car.setText(carName);
        }
    }
}