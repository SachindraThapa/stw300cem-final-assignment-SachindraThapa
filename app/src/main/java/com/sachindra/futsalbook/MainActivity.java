package com.sachindra.futsalbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.sachindra.futsalbook.ui.home.HomeFragment;
import com.sachindra.futsalbook.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, register, products, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.main_login);
        register = findViewById(R.id.main_register);
        products = findViewById(R.id.main_products);
        about = findViewById(R.id.main_about);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        products.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.main_login:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            case R.id.main_register:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                return;
            case R.id.main_products:

//             this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
//              this.getSupportActionBar().setTitle("Product List");
                return;

            case R.id.main_about:
                intent = new Intent(MainActivity.this, SearchPlaces.class);
                startActivity(intent);
                return;
        }
    }
}
