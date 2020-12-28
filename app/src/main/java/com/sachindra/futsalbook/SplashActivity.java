package com.sachindra.futsalbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.sachindra.futsalbook.interfaces.LoginInterface;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;

public class SplashActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        },3000);
    }

    private void checkUser(){
        if(isNetworkAvailable()){
            SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            String password = sharedPreferences.getString("password","");

            LoginInterface loginInterface = new LoginInterface();
            StrictModeClass.StrictMode();
            if(loginInterface.loginData(username,password)){
                Intent intent = new Intent(SplashActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            builder = new AlertDialog.Builder( SplashActivity.this );
            builder.setMessage( "please check your network ?" )
                    .setCancelable( false )
                    .setPositiveButton( "Check again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SplashActivity.this,SplashActivity.class);
                            startActivity(intent);
                        }
                    } );
            AlertDialog alert = builder.create();
            alert.setTitle( "Error 500 " );
            alert.show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}