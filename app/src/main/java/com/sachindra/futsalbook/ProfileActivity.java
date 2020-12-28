package com.sachindra.futsalbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.model.User;
import com.sachindra.futsalbook.response.ImageUploadResponse;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sachindra.futsalbook.Connection.Connection.token;

public class ProfileActivity extends AppCompatActivity {

    ImageView image;
    TextView imageEdit, password, name, email, username, joined, phone, proedit;
    String imagePath = Connection.imagePath;
    String toke;
    LinearLayout editpassword, userdetails, edituser;
    ConstraintLayout imagelayout;
    Button btnimage, btndetails, btnpassword, back;

    EditText namee, emaile, phonee, oldpass, newpass, conpass;

    String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        image = findViewById(R.id.imageView);
        imageEdit = findViewById(R.id.editImage);
        password = findViewById(R.id.profile_password);
        proedit = findViewById(R.id.profile_edit);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);
        username = findViewById(R.id.profile_username);
        joined = findViewById(R.id.profile_joined);

        btnimage = findViewById(R.id.update_image);
        btnpassword = findViewById(R.id.update_password);
        btndetails = findViewById(R.id.update_details);
        back = findViewById(R.id.password_back);

        imagelayout = findViewById(R.id.us_image);
        editpassword = findViewById(R.id.changePassword);
        edituser = findViewById(R.id.edit_details);
        userdetails = findViewById(R.id.user_details);

        namee = findViewById(R.id.update_name);
        emaile = findViewById(R.id.update_email);
        phonee = findViewById(R.id.update_phone);

        oldpass = findViewById(R.id.cur_password);
        newpass = findViewById(R.id.new_password);
        conpass = findViewById(R.id.confirm_new_password);

        if (Connection.token != "Bearer ") {
            toke = Connection.token;
            UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
            Call<User> userCall = usersAPI.getUser(toke);

            StrictModeClass.StrictMode();
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User data = response.body();

                    String path = imagePath + data.getImage();
                    Toast.makeText(ProfileActivity.this, ""+path, Toast.LENGTH_SHORT).show();
                    try {
                        URL url = new URL(path);
                        image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    joined.append(Html.fromHtml("<strong>" + data.getPhone() + "</strong>"));
                    name.append(Html.fromHtml("<strong>" + data.getName() + "</strong>"));
                    phone.append(Html.fromHtml("<strong>" + data.getPhone() + "</strong>"));
                    email.append(Html.fromHtml("<strong>" + data.getEmail() + "</strong>"));
                    username.append(Html.fromHtml("<strong>" + data.getUsername() + "</strong>"));

                    namee.setText(data.getName());
                    emaile.setText(data.getEmail());
                    phonee.setText(String.valueOf(data.getPhone()));

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ProfileActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
        }

        imageEdit.setText(Html.fromHtml("<u>Change DP</u>"));
        password.setText(Html.fromHtml("<u>Change Password</u>"));
        proedit.setText(Html.fromHtml("<u>Edit Details</u>"));

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpassword.setVisibility(View.VISIBLE);
                userdetails.setVisibility(View.GONE);
                imagelayout.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
            }
        });

        proedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edituser.setVisibility(View.VISIBLE);
                userdetails.setVisibility(View.GONE);
                imagelayout.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpassword.setVisibility(View.VISIBLE);
                userdetails.setVisibility(View.GONE);
                imagelayout.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
            }
        });

        btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upname = namee.getText().toString();
                String upemail = emaile.getText().toString();
                long upphone = Long.parseLong(phonee.getText().toString());

                if (upname.isEmpty()) {
                    namee.setError("Name is required");
                    return;
                }
                if (phonee.getText().toString().isEmpty()) {
                    phonee.setError("Confirmation Password is required");
                    return;
                }

                UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

                Call<RegisterResponse> registerCall = usersAPI.updateDetails(toke, upname, upemail, upphone);

                registerCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                        RegisterResponse registerResponse = response.body();
                        if (!response.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Unsuccessful. Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(ProfileActivity.this, "Your details updated successfully", Toast.LENGTH_LONG).show();

                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });


        btnpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = oldpass.getText().toString();
                String newp = newpass.getText().toString();
                String conp = conpass.getText().toString();

                if (pass.isEmpty()) {
                    oldpass.setError("Current Password is required");
                    return;
                }
                if (newp.isEmpty()) {
                    newpass.setError("New Password is required");
                    return;
                }
                if (conp.isEmpty()) {
                    conpass.setError("Confirmation Password is required");
                    return;
                }
                if (newp.length() < 6) {
                    oldpass.setError("Enter 6 characters for password");
                    return;
                }
                if (pass.equals(newp)) {
                    oldpass.setError("Passwords can not be same");
                    return;
                }
                if (!newp.equals(conp)) {
                    newpass.setError("Passwords do not match");
                    conpass.setError("Passwords do not match");
                    return;
                }

                UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

                Call<RegisterResponse> registerCall = usersAPI.updatePassword(toke, pass, newp);

                registerCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                        RegisterResponse registerResponse = response.body();
                        if (!response.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Unsuccessful. Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(ProfileActivity.this, "Password changed successfully", Toast.LENGTH_LONG).show();

                        editpassword.setVisibility(View.GONE);
                        edituser.setVisibility(View.GONE);
                        userdetails.setVisibility(View.VISIBLE);
                        imagelayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpassword.setVisibility(View.GONE);
                edituser.setVisibility(View.GONE);
                userdetails.setVisibility(View.VISIBLE);
                imagelayout.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
            }
        });
    }
}
