package com.example.taxfilemanagementapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Customer> data;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView phoneView;
        public TextView cityView;
        public TextView statusView;

        public ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.item_name);
            phoneView = view.findViewById(R.id.item_phone);
            cityView = view.findViewById(R.id.item_city);
            statusView = view.findViewById(R.id.item_status);
        }
    }


    public RecyclerAdapter(List<Customer> data, Context context) {
        this.data = data;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer customer = data.get(position);
        holder.nameView.setText(customer.name);
        holder.phoneView.setText(customer.phone);
        holder.cityView.setText(customer.address.city);
        holder.statusView.setText(customer.status);

        switch (customer.status) {
            case "AWAITED":
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFE0"));
                break;
            case "FAILEDTOREACH":
                holder.itemView.setBackgroundColor(Color.parseColor("#FFCCCB"));
                break;
            case "ONBOARDED":
                holder.itemView.setBackgroundColor(Color.parseColor("#90EE90"));
                break;
            case "INPROCESS":
                holder.itemView.setBackgroundColor(Color.parseColor("#32CD32"));
                break;
            case "COMPLETED":
                holder.itemView.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case "DENIED":
                holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
            default:
                holder.itemView.setBackgroundColor(Color.WHITE);
                break;
        }

        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(context, CustomerDetailActivity.class);
            intent.putExtra("username", customer.userName);
            intent.putExtra("position", position);
            ((AdminHomeActivity) context).startActivityForResult(intent, 1);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    public Customer getCustomerAtPosition(int pos){
        return data.get(pos);
    }

    public void removeCustomerAtPosition(int pos){
        data.remove(pos);
    }

    public void updateCustomerAtPosition(int pos, Customer customer){
        data.set(pos, customer);
        notifyItemChanged(pos);
    }
}