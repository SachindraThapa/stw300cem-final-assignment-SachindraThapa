package com.sachindra.futsalbook;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sachindra.futsalbook.API.ProductsAPI;
import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.adapter.CartsAdapter;
import com.sachindra.futsalbook.adapter.ProductAdapter;
import com.sachindra.futsalbook.model.CartModel;
import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyItems extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_items, container, false);

        recyclerView = root.findViewById(R.id.cart_recycler);

        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<List<CartModel>> productCall = usersAPI.myCarts(Connection.token);

        productCall.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CartModel> cartList = response.body();

                CartsAdapter cartsAdapter = new CartsAdapter(cartList,getContext());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setAdapter(cartsAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText( getContext(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
            }
        });
        return root;
    }

}
