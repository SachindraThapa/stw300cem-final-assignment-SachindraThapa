package com.sachindra.futsalbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.futsalbook.model.PriceModel;
import com.sachindra.futsalbook.R;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    List<PriceModel> price;
    Context mContext;

    public PriceAdapter(List<PriceModel> price, Context mContext) {
        this.price = price;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tablelayout, parent, false);
        return new PriceAdapter.PriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        final PriceModel prices = price.get(position);

        holder.price.setText("Rs. " +prices.getPrice());
        holder.type.setText(prices.getDaytype());
        holder.part.setText(prices.getDaypart());
    }

    @Override
    public int getItemCount() {
        return price.size();
    }

    public class PriceViewHolder extends RecyclerView.ViewHolder {
        TextView type, part, price;
        public PriceViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.row1);
            part = itemView.findViewById(R.id.row2);
            price = itemView.findViewById(R.id.row3);
        }
    }
}
