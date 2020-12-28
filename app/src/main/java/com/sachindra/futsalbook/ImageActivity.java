package com.sachindra.futsalbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Dash;
import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.response.ImageUploadResponse;
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

public class ImageActivity extends AppCompatActivity {

    Button add;
    TextView choose, skip;
    ImageView image;
    String imageName = "";
    String imagePath = "";
    String toke = Connection.token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        add = findViewById(R.id.btneditimage);
        choose = findViewById(R.id.chooseimage);
        skip = findViewById(R.id.skip);
        image = findViewById(R.id.reg_image);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take Photo from Camera", "Choose from Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
                builder.setTitle("Upload Profile Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo from Camera")) {
                            cameraPermission();
                            loadCamera();
                        } else if (options[item].equals("Choose from Gallery")) {
                            galleryPermission();
                            openGallery();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
                updateImage();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoImage();
            }
        });
    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }

    private void galleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }

    private void loadCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, 0);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(("image/*"));
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getExtras() != null) {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                image.setImageBitmap(bitmap);
            }
        } else if (data.getData() != null) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }

            Uri uri = data.getData();
            image.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        }
    }

    private void saveImage() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",
                file.getName(), requestBody);

        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<ImageUploadResponse> responseBodyCall = usersAPI.uploadImage(body);
        //  Toast.makeText(this, ""+imageName, Toast.LENGTH_SHORT).show();

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageUploadResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "No insert error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.d("image Error", "No insert error " + e.getLocalizedMessage());
            imageName = "user.png";
            e.printStackTrace();
        }
    }

    private void updateImage() {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<ImageUploadResponse> updateCall = usersAPI.updateImage(toke, imageName);

        updateCall.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ImageActivity.this, "Error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                NoImage();
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {

            }
        });
    }


    private void NoImage() {
        Intent intent = new Intent(ImageActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
}
