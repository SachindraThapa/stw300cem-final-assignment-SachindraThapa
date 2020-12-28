package com.sachindra.futsalbook.ui.booking;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.DashboardActivity;
import com.sachindra.futsalbook.ProfileActivity;
import com.sachindra.futsalbook.adapter.BookingAdapter;
import com.sachindra.futsalbook.adapter.PriceAdapter;
import com.sachindra.futsalbook.model.BookingModel;
import com.sachindra.futsalbook.model.PriceModel;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {

    EditText tvDate;
    Spinner tvTime;
    Button book;

    RecyclerView recyclerView;
    String toke, book_date, book_time;
    final String times[] = {"Select a time",
            "6AM-7AM", "7AM-8AM", "8AM-9AM", "9AM-10AM", "10AM-11AM",
            "11AM-12PM", "12PM-1PM", "1PM-2PM", "2PM-3PM", "3PM-4PM",
            "4PM-5PM", "5PM-6PM", "6PM-7PM", "7PM-8PM"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        tvDate = view.findViewById(R.id.bookingDate);
        tvTime = view.findViewById(R.id.bookingTime);

        book = view.findViewById(R.id.book_ground);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item,
                times
        );
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tvTime.setAdapter(arrayAdapter);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBooking();
            }
        });

        recyclerView = view.findViewById(R.id.ground_rercycle);

        viewBooking();
        return view;
    }

    private void loadDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener setdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tvDate.setText(sdf.format(calendar.getTime()));
            }
        };
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), setdate, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void saveBooking() {
        if (Connection.token.equals("Bearer ")) {
            Toast.makeText(getActivity(), "Invalid token.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tvTime.getSelectedItemPosition() > 1) {
            book_time = tvTime.getSelectedItem().toString();
        } else {
            Toast.makeText(getActivity(), "Please select time for booking.", Toast.LENGTH_SHORT).show();
        }

        if (tvDate.getText().toString().equals("Pick a date")) {
            Toast.makeText(getActivity(), "Pick a date of booking.", Toast.LENGTH_SHORT).show();
            return;
        }
        toke = Connection.token;
        book_date = tvDate.getText().toString();
        Toast.makeText(getActivity(), "" + book_date, Toast.LENGTH_SHORT).show();

        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

        Call<RegisterResponse> bookingcall = usersAPI.groundBooking(toke, book_date, book_time);

        bookingcall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful() && registerResponse.getStatus().equals("Success")) {
                    Toast.makeText(getActivity(), "Booking Done.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Booking Not Available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private void viewBooking() {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<List<BookingModel>> viewBooking = usersAPI.allBooking();

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
