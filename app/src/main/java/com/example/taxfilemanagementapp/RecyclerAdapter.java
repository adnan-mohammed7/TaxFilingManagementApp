package com.example.taxfilemanagementapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final List<Customer> data;
    private final CustomClickHandler customClickHandler;

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


    public RecyclerAdapter(List<Customer> data, CustomClickHandler handler) {
        this.data = data;
        this.customClickHandler = handler;
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
        holder.nameView.setText(String.format("Name: %s", customer.name));
        holder.phoneView.setText(String.format("Phone: %s", customer.phone));
        holder.cityView.setText(String.format("City: %s", customer.address.city));
        holder.statusView.setText(String.format("Status: %s", customer.status));

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
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
        }

        holder.itemView.setOnClickListener(e->{
            if(customClickHandler != null){
                customClickHandler.itemClickHandler(position, customer);
            }
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

    public interface CustomClickHandler{
        void itemClickHandler(int pos, Customer customer);
    }
}