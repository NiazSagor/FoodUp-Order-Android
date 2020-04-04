package com.angik.android.foodup_order;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.angik.android.foodup_order.Model.OrderDetailAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView listView;//Shows items and quantity
    private TextView totalTextView;//Shows total price
    private TextView idTextView;//Shows id
    private FloatingActionButton doneButton;//Confirmation button

    //Lists for names and their quantity
    private ArrayList<String> itemName;
    private ArrayList<Integer> itemCount;

    private Toolbar toolbar;

    private OrderDetailAdapter mAdapter;//Our custom array adapter

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);//Setting custom icon at the toolbar
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
        totalTextView = findViewById(R.id.totalPrice);
        idTextView = findViewById(R.id.idTextView);
        idTextView.setText("ID : " + getIntent().getStringExtra("id"));

        doneButton = findViewById(R.id.floatingActionButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                orderServed(getIntent().getStringExtra("id"));
                addToTotalTransaction();
            }
        });

        getData(getIntent().getStringExtra("id"));
        getTotal(totalTextView, getIntent().getStringExtra("id"));
    }

    private void getData(String id) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(id);
        databaseReference.child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemName = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String string = Objects.requireNonNull(postSnapshot.getValue()).toString();
                        itemName.add(string);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Quantity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemCount = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        int i = Integer.parseInt(Objects.requireNonNull(postSnapshot.getValue()).toString());
                        itemCount.add(i);
                    }
                    mAdapter = new OrderDetailAdapter(DetailActivity.this, itemName, itemCount);
                    listView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getTotal(final TextView textView, final String id) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        databaseReference.child("Current Order").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int price = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                    textView.setText("Total Price : " + price + " Tk");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void orderServed(String id) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        databaseReference.child("Status").setValue("Served");
    }

    private void addToTotalTransaction() {
        Calendar calendar = Calendar.getInstance();
        final String date = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());
        final String time = calendar.get(Calendar.HOUR_OF_DAY) + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(getIntent().getStringExtra("id"));
        databaseReference.child("Current Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final String s = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Transaction");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(date).exists()) {
                                databaseReference.child(date).push().setValue(s);
                            } else {
                                databaseReference.child(date).push().setValue(s);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
