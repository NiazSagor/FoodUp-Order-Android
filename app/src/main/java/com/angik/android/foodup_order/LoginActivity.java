package com.angik.android.foodup_order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angik.android.foodup_order.Super.SuperActivity;

import java.util.Objects;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private Button login;

    private SharedPreferences sp_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp_vendor = getSharedPreferences("vendor", MODE_PRIVATE);

        if (sp_vendor.getBoolean("login", false)) {
            startActivity(new Intent(this, SuperActivity.class));
            finish();
            return;
        }

        if (sp_vendor.getBoolean("storeLogin", false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Quantity");

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            //actionBar.setDisplayHomeAsUpEnabled(true); adds back arrow
            Objects.requireNonNull(actionBar).setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        editText = findViewById(R.id.editText);
        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVendor(editText.getText().toString());
            }
        });
    }

    private void checkVendor(String password) {
        if (password.equals("")) {
            Toast.makeText(this, "Please enter your identification code", Toast.LENGTH_SHORT).show();
        } else if (password.equals("Khan")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            sp_vendor.edit().putString("currentVendor", "Khan").apply();
            sp_vendor.edit().putBoolean("storeLogin", true).apply();
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            finish();
        } else if (password.equals("Kutub")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            sp_vendor.edit().putString("currentVendor", "Kutub").apply();
            sp_vendor.edit().putBoolean("storeLogin", true).apply();
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            finish();
        } else if (password.equals("Shaowal")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            sp_vendor.edit().putString("currentVendor", "Shaowal").apply();
            sp_vendor.edit().putBoolean("storeLogin", true).apply();
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            finish();
        } else if (password.equals("Kaftan")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            sp_vendor.edit().putString("currentVendor", "Kaftan").apply();
            sp_vendor.edit().putBoolean("storeLogin", true).apply();
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            finish();
        } else if (password.equals("SuperApp")) {
            Intent intent = new Intent(LoginActivity.this, SuperActivity.class);
            startActivity(intent);
            sp_vendor.edit().putBoolean("login", true).apply();
        } else {
            Toast.makeText(this, "Identification code is wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
