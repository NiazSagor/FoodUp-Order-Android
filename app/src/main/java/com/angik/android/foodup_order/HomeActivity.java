package com.angik.android.foodup_order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.angik.android.foodup_order.FaisalUncle.SelectLunchActivityFaisal;
import com.angik.android.foodup_order.Kaftan.SelectLunchActivityKaftan;
import com.angik.android.foodup_order.Shaowal.SelectLunchActivityShaowal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

@SuppressWarnings("ALL")
public class HomeActivity extends AppCompatActivity {

    private Switch deliveryToggle;

    private TextView checkOrder;
    private TextView breakfastItems;
    private TextView lunchItems;
    private TextView quantity;
    private TextView storeName;
    private TextView logoutText;

    private CardView checkOrderCard;
    private CardView breakfastItemsCard;
    private CardView lunchItemsCard;
    private CardView quantityCard;

    private LinearLayout logoutButton;

    private SharedPreferences sp_vendor;
    private String vendorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp_vendor = getSharedPreferences("vendor", MODE_PRIVATE);
        vendorName = sp_vendor.getString("currentVendor", null);

        initializeViews();

        setBackground();
    }

    private void initializeViews() {
        deliveryToggle = findViewById(R.id.toggle);

        if (vendorName.equals("Khan")) {
            deliveryToggle.setVisibility(View.VISIBLE);
        } else {
            deliveryToggle.setVisibility(View.GONE);
        }

        checkOrder = findViewById(R.id.checkOrders);
        breakfastItems = findViewById(R.id.breakfastItems);
        lunchItems = findViewById(R.id.lunchItems);
        quantity = findViewById(R.id.quantity);
        storeName = findViewById(R.id.storeName);
        logoutText = findViewById(R.id.logoutText);

        storeName.setText(vendorName);

        changeFont(checkOrder);
        changeFont(breakfastItems);
        changeFont(lunchItems);
        changeFont(quantity);
        changeFont(storeName);
        changeFont(logoutText);

        checkOrderCard = findViewById(R.id.checkOrdersCard);
        breakfastItemsCard = findViewById(R.id.breakfastItemsCard);
        lunchItemsCard = findViewById(R.id.lunchItemsCard);
        quantityCard = findViewById(R.id.quantityCard);

        checkOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_vendor.edit().clear().apply();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                Toast.makeText(HomeActivity.this, "You're logged out!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /*
        breakfastItemsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (vendorName) {
                    case "Faisal":
                        intent = new Intent(HomeActivity.this, SelectActivityFaisal.class);
                        startActivity(intent);
                        break;
                    case "Khan":
                        intent = new Intent(HomeActivity.this, SelectActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Hola", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        lunchItemsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (vendorName) {
                    case "Kutub":
                        intent = new Intent(HomeActivity.this, SelectLunchActivityFaisal.class);
                        startActivity(intent);
                        break;
                    case "Khan":
                        intent = new Intent(HomeActivity.this, SelectLunchActivity.class);
                        startActivity(intent);
                        break;
                    case "Shaowal":
                        intent = new Intent(HomeActivity.this, SelectLunchActivityShaowal.class);
                        startActivity(intent);
                        break;
                    case "Kaftan":
                        intent = new Intent(HomeActivity.this, SelectLunchActivityKaftan.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Hola", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quantityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QuantityActivityNew.class);
                startActivity(intent);
            }
        });

        deliveryToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDeliveryForKhan(b);
            }
        });
    }

    private void setBackground() {
        ImageView bg = findViewById(R.id.bg);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/food-86e25.appspot.com/o/Food%20Images%2Fbg.jpg?alt=media&token=c9f7265f-3ea0-42bb-adad-ee75cbdf3462")
                .fit()
                .centerCrop()
                .into(bg);
    }


    private void changeFont(TextView textView) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bontserrat_bold.otf");
        textView.setTypeface(custom_font);
    }

    private void setDeliveryForKhan(boolean b) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Delivery").child("2");
        if (b) {
            databaseReference.setValue("Delivery Available");
        } else {
            databaseReference.setValue("Pre-Book Only");
        }
    }
}
