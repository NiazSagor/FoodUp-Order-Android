package com.angik.android.foodup_order.Super;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.angik.android.foodup_order.FaisalUncle.SelectLunchActivityFaisal;
import com.angik.android.foodup_order.HomeActivity;
import com.angik.android.foodup_order.Kaftan.SelectLunchActivityKaftan;
import com.angik.android.foodup_order.R;
import com.angik.android.foodup_order.SelectLunchActivity;
import com.angik.android.foodup_order.Shaowal.SelectLunchActivityShaowal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
@SuppressLint("SetTextI18n")
public class SuperActivity extends AppCompatActivity {

    private SharedPreferences sp_vendor;

    private Switch aSwitch;

    private String[] imageAddress = {"https://firebasestorage.googleapis.com/v0/b/food-86e25.appspot.com/o/Food%20Images%2FLentil-Soup_D-Kopcok.jpg?alt=media&token=47077ed7-0ce7-4785-ad73-92f21385fa21",
            "https://firebasestorage.googleapis.com/v0/b/food-86e25.appspot.com/o/Food%20Images%2Ffoods-for-pms-getty-Victoria-Stoeva-EyeEm.jpg?alt=media&token=681fd70d-e44c-4734-ac41-3986be411f2d",
            "https://firebasestorage.googleapis.com/v0/b/food-86e25.appspot.com/o/Food%20Images%2Fmoody-food_header_grafiken.jpg?alt=media&token=bf9c3b81-2cb8-4afa-aa69-66e4ae277475",
            "https://firebasestorage.googleapis.com/v0/b/food-86e25.appspot.com/o/Food%20Images%2F236688574.jpg?alt=media&token=9cf494a0-50b5-46c4-a3bb-43fa7cb1cca3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);

        sp_vendor = getSharedPreferences("vendor", MODE_PRIVATE);

        initializeViews();

        handleOnClicks();

        setOrderAmount();
    }

    private void initializeViews() {

        ImageView shaowalImage;
        ImageView kutubImage;
        ImageView dulavaiImage;
        ImageView kaftanImage;

        kaftanImage = findViewById(R.id.kaftanImage);
        shaowalImage = findViewById(R.id.shaowalImage);
        kutubImage = findViewById(R.id.kutubImage);
        dulavaiImage = findViewById(R.id.dulavaiImage);


        Picasso.get().load(imageAddress[0]).fit().centerCrop().into(shaowalImage);
        Picasso.get().load(imageAddress[1]).fit().centerCrop().into(kaftanImage);
        Picasso.get().load(imageAddress[2]).fit().centerCrop().into(dulavaiImage);
        Picasso.get().load(imageAddress[3]).fit().centerCrop().into(kutubImage);
    }

    private void handleOnClicks() {
        CardView card1, card2, card3, card4;

        card1 = findViewById(R.id.shaowalCard);
        card2 = findViewById(R.id.kaftanCard);
        card3 = findViewById(R.id.dulavaiCard);
        card4 = findViewById(R.id.kutubCard);

        aSwitch = findViewById(R.id.toggle);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_vendor.edit().putString("currentVendor", "Shaowal").apply();

                if (aSwitch.isChecked()) {
                    startActivity(new Intent(SuperActivity.this, SelectLunchActivityShaowal.class).putExtra("store", "Shaowal"));
                } else {
                    startActivity(new Intent(SuperActivity.this, SuperDetails.class).putExtra("store", "Shaowal"));
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_vendor.edit().putString("currentVendor", "Kaftan").apply();

                if (aSwitch.isChecked()) {
                    startActivity(new Intent(SuperActivity.this, SelectLunchActivityKaftan.class).putExtra("store", "Shaowal"));
                } else {
                    startActivity(new Intent(SuperActivity.this, SuperDetails.class).putExtra("store", "Kaftan"));
                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_vendor.edit().putString("currentVendor", "Khan").apply();

                if (aSwitch.isChecked()) {
                    startActivity(new Intent(SuperActivity.this, SelectLunchActivity.class).putExtra("store", "Kaftan"));
                } else {
                    startActivity(new Intent(SuperActivity.this, HomeActivity.class).putExtra("store", "Khan"));
                }
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_vendor.edit().putString("currentVendor", "Kutub").apply();

                if (aSwitch.isChecked()) {
                    startActivity(new Intent(SuperActivity.this, SelectLunchActivityFaisal.class).putExtra("store", "Khan"));
                } else {
                    startActivity(new Intent(SuperActivity.this, SuperDetails.class).putExtra("store", "Kutub"));
                }
            }
        });
    }

    private void setOrderAmount() {
        TextView orderAmountShaowal = findViewById(R.id.orderCountShaowal);
        dataFetch(orderAmountShaowal, "Order Shaowal");

        TextView orderAmountKaftan = findViewById(R.id.orderCountKaftan);
        dataFetch(orderAmountKaftan, "Order Kaftan");

        TextView orderAmountDulavai = findViewById(R.id.orderCountDulavai);
        dataFetch(orderAmountDulavai, "Building");

        TextView orderAmountKutub = findViewById(R.id.orderCountKutub);
        dataFetch(orderAmountKutub, "Order Kutub");
    }

    private void dataFetch(final TextView textView, String node) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(node).child("Pick Up From Store");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    animateTextView(textView, dataSnapshot.getChildrenCount() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void animateTextView(final TextView textView, long average) {
        ValueAnimator animator = new ValueAnimator();
        int i = (int) average;//Casting long value into the int type variable

        if (i == 0) {
            textView.setText("" + 0);
        } else {
            animator.setObjectValues(0, i);//Here the 2nd parameter is the range of counting
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    textView.setText(String.valueOf(animation.getAnimatedValue()));
                }
            });
            animator.setDuration(5000); //Duration of the anim, which is 2 seconds
            animator.start();
        }
    }
}
