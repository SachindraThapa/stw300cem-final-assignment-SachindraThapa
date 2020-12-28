package com.sachindra.futsalbook;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.adapter.BookingAdapter;
import com.sachindra.futsalbook.model.BookingModel;
import com.sachindra.futsalbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooking extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_booking, container, false);
        recyclerView = view.findViewById(R.id.myground_rercycle);

        viewBooking();
        return view;
    }

    private void viewBooking() {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        String toke = Connection.token;
        Call<List<BookingModel>> viewBooking = usersAPI.myBooking(toke);

        viewBooking.enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<BookingModel> booking = response.body();

                BookingAdapter bookingAdapter = new BookingAdapter(booking,getContext());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setAdapter(bookingAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {

            }
        });

    }
}
