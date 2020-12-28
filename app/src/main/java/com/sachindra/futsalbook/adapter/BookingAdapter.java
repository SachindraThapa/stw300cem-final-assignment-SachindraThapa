package com.sachindra.futsalbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.model.BookingModel;
import com.sachindra.futsalbook.model.PriceModel;
import com.sachindra.futsalbook.response.RegisterResponse;
import com.sachindra.futsalbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder>
{
    List<BookingModel> booking;
    Context mContext;

    public BookingAdapter(List<BookingModel> booking, Context mContext) {
        this.booking = booking;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tablelayout, parent, false);
        return new BookingAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, final int position) {
        final BookingModel bookingModel = booking.get(position);

        holder.date.setText(bookingModel.getDate());
        holder.time.setText(bookingModel.getTime());
        holder.username.setText(bookingModel.getUsername());
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booking.remove(position);
                Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                deleteBooking(Connection.token,bookingModel.getId());

            }
        });
    }


    @Override
    public int getItemCount() {
        return booking.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, username;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.row1);
            time = itemView.findViewById(R.id.row2);
            username = itemView.findViewById(R.id.row3);

        }
    }
    private void deleteBooking(String token, String id) {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> responseCall = usersAPI.deletebooking(token, id);
        responseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext, "error"+response.code(), Toast.LENGTH_SHORT).show();
                    return;}
                Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });


    }

}
