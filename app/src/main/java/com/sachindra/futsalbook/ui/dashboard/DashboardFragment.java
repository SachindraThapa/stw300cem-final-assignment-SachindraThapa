package com.sachindra.futsalbook.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.adapter.PriceAdapter;
import com.sachindra.futsalbook.model.PriceModel;
import com.sachindra.futsalbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.table_rercycle);
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<List<PriceModel>> pricecall = usersAPI.getPrice();

        pricecall.enqueue(new Callback<List<PriceModel>>() {
            @Override
            public void onResponse(Call<List<PriceModel>> call, Response<List<PriceModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<PriceModel> priceList = response.body();

                PriceAdapter bookingAdapter = new PriceAdapter(priceList,getContext());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setAdapter(bookingAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<PriceModel>> call, Throwable t) {

            }
        });
        return root;
    }
}