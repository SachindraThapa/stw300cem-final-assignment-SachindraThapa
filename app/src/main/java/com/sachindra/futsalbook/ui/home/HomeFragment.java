package com.sachindra.futsalbook.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.API.ProductsAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.adapter.ProductAdapter;
import com.sachindra.futsalbook.model.Category;
import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.prod_recycler);

        ProductsAPI productsAPI = Connection.getInstance().create(ProductsAPI.class);
        Call<List<Product>> productCall = productsAPI.allProducts();

        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Product> productList = response.body();

                ProductAdapter productAdapter = new ProductAdapter(productList,getContext());

                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return (position%3==0? 2: 1);
//            }
//        });
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText( getContext(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
            }
        });
        return root;
    }
}