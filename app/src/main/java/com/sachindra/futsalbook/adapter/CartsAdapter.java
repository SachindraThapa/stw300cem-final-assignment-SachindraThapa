package com.sachindra.futsalbook.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.API.ProductsAPI;
import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.model.CartModel;
import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.CartsViewHolder> {
    List<CartModel> carts;
    Context mContext;

    public CartsAdapter(List<CartModel> carts, Context mContext) {
        this.carts = carts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycarts, parent, false);
        return new CartsAdapter.CartsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartsViewHolder holder, final int position) {
        final CartModel cartModel = carts.get(position);

        holder.quantity.setText(cartModel.getQuantity() + " pcs.");

        if (cartModel.getDelivered() == "true") {
            holder.delivered.setText("Delivered");
        } else {
            holder.delivered.setText("Not Delivered.");

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (!cartModel.getProducts().getName().isEmpty()) {

            holder.name.setText(cartModel.getProducts().getName());
            holder.price.setText("Rs. " + cartModel.getProducts().getPrice());
            StrictModeClass.StrictMode();
            if (!cartModel.getProducts().getImage().isEmpty()) {
                final String imagePath = Connection.priductImage + cartModel.getProducts().getImage();

                try {
                    URL url = new URL(imagePath);
                    holder.image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = Connection.token;
                carts.remove(position);
                notifyDataSetChanged();
                delete(token, cartModel.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class CartsViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delete;
        TextView name, price, quantity, delivered;

        public CartsViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.mycart_image);
            delete = itemView.findViewById(R.id.cart_remove);
            name = itemView.findViewById(R.id.mycart_name);
            price = itemView.findViewById(R.id.mycart_price);
            quantity = itemView.findViewById(R.id.mycart_quantity);
            delivered = itemView.findViewById(R.id.delivered);
        }
    }

    void delete(String token, String id) {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

        Call<RegisterResponse> responseCall = usersAPI.deletecart(token, id);
        responseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(mContext, "cart deleted", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
