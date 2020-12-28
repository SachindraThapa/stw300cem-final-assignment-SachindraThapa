package com.sachindra.futsalbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sachindra.futsalbook.channel.NotifyChannel;
import com.sachindra.futsalbook.interfaces.LoginInterface;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button login,register;
    TextView txtlogin;
    CheckBox remember;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_submit);

        remember = findViewById(R.id.remember);
        register = findViewById(R.id.login_register);
        txtlogin = findViewById(R.id.txtlogin);

        txtlogin.setSingleLine();

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        NotifyChannel channel=new NotifyChannel(this);
        channel.CreateChannel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit:
                loginInfo();
                return;

            case R.id.login_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                return;
        }
    }

    private void loginInfo(){
        String un,pwd;
        if (!TextUtils.isEmpty(username.getText().toString())) {
            un = username.getText().toString();
            if (!TextUtils.isEmpty(password.getText().toString())) {
                pwd = password.getText().toString();
            } else {
                password.setError("enter your password");
                return;
            }
        } else {
            username.setError("enter your username");
            return;
        }

        LoginInterface loginInterface = new LoginInterface();
        StrictModeClass.StrictMode();

        if(loginInterface.loginData(un,pwd)){
            if(remember.isChecked()){
                SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username",un);
                editor.putString("password",pwd);
                editor.apply();
                Toast.makeText(this, "Password Saved", Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            DisplayNotification(un);
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
//            intent.putExtra("username",un);

            username.setText("");
            password.setText("");
            finish();
        }
        else {
            Toast.makeText(this, "Login Failed. Username or password incorrect.", Toast.LENGTH_SHORT).show();
        }
    }

    public void DisplayNotification(String noti){
        @SuppressLint("ResourceAsColor")
        Notification notification = new NotificationCompat.Builder(this, NotifyChannel.login)
                .setSmallIcon(R.drawable.ic_user)
                .setContentTitle("Notification 1")
                .setContentText("Welcome "+noti)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1,notification);
    }
}
