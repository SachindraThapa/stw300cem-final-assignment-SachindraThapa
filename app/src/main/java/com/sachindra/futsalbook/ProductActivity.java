package com.sachindra.futsalbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sachindra.futsalbook.API.ProductsAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.channel.NotifyChannel;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    ImageView img;
    TextView p_name, p_price, p_desc, p_type, p_brand, p_stock;
    RatingBar p_rating;
    Button cart;
    String slug, name;
    EditText quantity;
    int qty, price;
    NotificationManagerCompat notificationManagerCompat;


    private SensorManager mSensorManager;
    private Sensor mProximity;
    SensorEventListener sel;
    private static final int SENSOR_SENSITIVITY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        img = findViewById(R.id.single_image);
        p_name = findViewById(R.id.single_name);
        p_price = findViewById(R.id.single_price);
        p_desc = findViewById(R.id.single_desc);
        p_type = findViewById(R.id.single_type);
        p_rating = findViewById(R.id.single_rating);
        p_brand = findViewById(R.id.single_brand);
        p_stock = findViewById(R.id.single_stock);
        cart = findViewById(R.id.btn_cart);

        quantity = findViewById(R.id.quantity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            slug = bundle.getString("slug");
            String images = bundle.getString("image");
            price = bundle.getInt("price");
            String brand = bundle.getString("brand");
            int rating = bundle.getInt("rating");
            String stock = bundle.getString("stock");
            String desc = bundle.getString("description");
            String category = bundle.getString("category");
            int discount = bundle.getInt("description");

            try {
                URL url = new URL(images);
                img.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            p_name.setText(name);
            p_desc.setText(desc);
            p_brand.setText(brand);
            if (discount > 0) {
                int cutPrice = price;
                int discountPrice = price - (price * discount / 100);
                p_price.setText(Html.fromHtml("<span style='text-decoration: line-through'>£ " + cutPrice + "</span>" + " £ " + discountPrice));
            } else {
                p_price.setText("£ " + price);
            }
            if (stock == "true") {
                p_stock.setText("Available");
            } else {
                p_stock.setText("Out of Stock");
            }

            p_rating.setRating(rating);

            notificationManagerCompat = NotificationManagerCompat.from(this);
            NotifyChannel channel = new NotifyChannel(this);
            channel.CreateChannel();

            mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
            mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            sel = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                        if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                            cart.setEnabled(false);
                        } else {
                            cart.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
            if (mProximity != null) {
                mSensorManager.registerListener(sel, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(this, "NO SENSOR DETECTED", Toast.LENGTH_SHORT).show();
            }
        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookproducts();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sel);
    }

    private void bookproducts() {
        String token = Connection.token;
        if (quantity.getText().toString().isEmpty()) {
            quantity.setError("Quantity is required.");
            return;
        }
        qty = Integer.parseInt(quantity.getText().toString());
        ProductsAPI productsAPI = Connection.getInstance().create(ProductsAPI.class);

        Call<RegisterResponse> addBooking = productsAPI.addBooking(token, slug, qty);

        addBooking.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful() && registerResponse.getStatus().equals("Success")) {
                    Toast.makeText(ProductActivity.this, "Booking Done.", Toast.LENGTH_LONG).show();

                    int amount = qty * price;
                    DisplayNotification(name, amount);

                    Intent intent = new Intent(ProductActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    public void DisplayNotification(String noti, int amt) {
        @SuppressLint("ResourceAsColor")
        Notification notification = new NotificationCompat.Builder(this, NotifyChannel.login)
                .setSmallIcon(R.drawable.ic_user)
                .setContentTitle("Notification 1")
                .setContentText("" + noti + " worth Rs. " + amt + " is added to cart.")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }
}