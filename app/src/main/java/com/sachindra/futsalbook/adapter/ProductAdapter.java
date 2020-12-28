package com.sachindra.futsalbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.ProductActivity;
import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.strictMode.StrictModeClass;
import com.sachindra.futsalbook.R;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> products;
    Context mContext;
    String shortDesc;

    int cutPrice;
    int discountPrice;

    public ProductAdapter(List<Product> products, Context mContext) {
        this.products = products;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Product prod = products.get(position);
        // List<Category> categories=
        String name =prod.getCategory_name().getName();
        Toast.makeText(mContext, " "+name, Toast.LENGTH_SHORT).show();

        holder.name.setText(prod.getName());
        // Toast.makeText(mContext, " "+prod.getCategoryName().getContents(), Toast.LENGTH_SHORT).show();
//        holder.type.setText(prod.getCategoryName().getContents());
        holder.type.setText(name);
//       prod.getCategory();

        if (prod.getDiscount() > 0){
            cutPrice = prod.getPrice();
            discountPrice = prod.getPrice() - (prod.getPrice()*prod.getDiscount()/100);
            holder.price.setText(Html.fromHtml("<span style='text-decoration: line-through'>£ "+cutPrice+"</span>"+ " £ "+discountPrice));
        }
        else {
            holder.price.setText("£ "+prod.getPrice());
        }

//        holder.price.setText("£ " + cutPrice);

        if (prod.getDescription().length() > 50) {
            shortDesc = prod.getDescription().substring(0, 50) + "...";
        } else {
            shortDesc = prod.getDescription();
        }

        holder.desc.setText(shortDesc);
        holder.brand.setText(prod.getBrand());

        if (prod.getStock() == "true") {
            holder.stock.setText("Available");
        } else{
            holder.stock.setText("Out of Stock");
        }

        holder.rating.setRating(prod.getRating());

        StrictModeClass.StrictMode();
        final String imagePath = Connection.priductImage + prod.getImage();

        try {
            URL url = new URL(imagePath);
            holder.prod_image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("image", imagePath);
                intent.putExtra("name", prod.getName());
                intent.putExtra("slug", prod.getSlug());
                intent.putExtra("price", prod.getPrice());
                intent.putExtra("brand", prod.getBrand());
                intent.putExtra("rating", prod.getRating());
                intent.putExtra("stock", prod.getStock());
                intent.putExtra("description", prod.getDescription());
                // intent.putExtra("category", prod.getCategory());
                intent.putExtra("discount", prod.getDiscount());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView prod_image;
        TextView name, price, desc, type, brand, stock;
        RatingBar rating;
        Button details;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_image = itemView.findViewById(R.id.prod_image);
            name = itemView.findViewById(R.id.prod_name);
            price = itemView.findViewById(R.id.prod_price);
            desc = itemView.findViewById(R.id.prod_desc);
            type = itemView.findViewById(R.id.prod_type);
            rating = itemView.findViewById(R.id.prod_rating);
            brand = itemView.findViewById(R.id.prod_brand);
            stock = itemView.findViewById(R.id.prod_stock);
            details = itemView.findViewById(R.id.btn_details);
        }
    }
}
