package com.sachindra.futsalbook;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.model.User;
import com.sachindra.futsalbook.response.ImageUploadResponse;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sachindra.futsalbook.Connection.Connection.token;

public class RegisterActivity extends AppCompatActivity {
    private EditText name, phone, username, email, pwd, confirm;
    private Button save;
    String userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.reg_name);
        username = findViewById(R.id.reg_username);
        phone = findViewById(R.id.reg_phone);
        email = findViewById(R.id.reg_email);

        pwd = findViewById(R.id.reg_password);
        confirm = findViewById(R.id.reg_con_password);
        save = findViewById(R.id.reg_submit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userphone=(phone.getText().toString());
                validation();
            }
        });
    }

    public void Clear() {
        name.setText("");
        username.setText("");
        email.setText("");
        phone.setText("");
        pwd.setText("");
        confirm.setText("");
    }

    private void validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Name is required.");
            return;
        } else if (username.getText().toString().isEmpty()) {
            username.setError("Username is required.");
            return;
        } else if (username.getText().toString().length() < 6) {
            username.setError("At least 6 characters for username");
            return;
        } else if (email.getText().toString().isEmpty()) {
            email.setError("Email is required.");
            return;
        }
        else if (phone.getText().toString().isEmpty()) {
            phone.setError("Phone number is required.");
            return;
        } else if ((phone.getText().toString()).length() != 10) {
            phone.setError("Invalid phone number");
            return;
        } else if (pwd.getText().toString().isEmpty()) {
            pwd.setError("Password is required.");
            return;
        } else if (confirm.getText().toString().isEmpty()) {
            confirm.setError("Password must be confirmed.");
            return;
        } else if (!pwd.getText().toString().equals(confirm.getText().toString())) {
            confirm.setError("Password do not match");
            pwd.setError("Password do not match");
            return;
        } else {
//            saveImageOnly();
            saveData();
        }
    }

    private void saveData() {
        String fname = name.getText().toString();
        String fusername = username.getText().toString();
        String femail = email.getText().toString();
        long fphone = Long.parseLong(phone.getText().toString());
        String fpassword = pwd.getText().toString();

        User users = new User(fname, fusername, femail, fpassword, "", fphone);
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

        Call<RegisterResponse> registerCall = usersAPI.registerUser(users);

        registerCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Unsuccessful. Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                token = token + registerResponse.getToken();
                Intent intent = new Intent(RegisterActivity.this, ImageActivity.class);
                intent.putExtra("token", token);
                Clear();
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }
}