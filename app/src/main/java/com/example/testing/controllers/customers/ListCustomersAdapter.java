package com.example.testing.controllers.customers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.models.PrototypePerson.Customers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCustomersAdapter extends RecyclerView.Adapter<ListCustomersAdapter.CustomerViewHolder> {

    ArrayList<Customers> listCustomers;
    ArrayList<Customers> listOriginal;

    public ListCustomersAdapter(ArrayList<Customers> listCustomers){
        this.listCustomers = listCustomers;
        listOriginal = new ArrayList<>();
        listOriginal.addAll(listCustomers);
    }

    @NonNull
    @Override
    public ListCustomersAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, null , false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCustomersAdapter.CustomerViewHolder holder, int position) {
        holder.viewName.setText(listCustomers.get(position).getName());
        holder.viewTelephone.setText(listCustomers.get(position).getTelephone());
        holder.viewEmail.setText(listCustomers.get(position).getEmail());
    }

    public void filter(String txtSearch){
        int length = txtSearch.length();
        if(length== 0){
            listCustomers.clear();
            listCustomers.addAll(listOriginal);
        }else{
            List<Customers> collection = listCustomers.stream()
                    .filter(i -> i.getName().toLowerCase().contains(txtSearch.toLowerCase()))
                    .collect(Collectors.toList());
            listCustomers.clear();
            listCustomers.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView viewName, viewTelephone, viewEmail;
        public CustomerViewHolder(@NonNull View itemView){
            super(itemView);

            viewName = itemView.findViewById(R.id.viewName);
            viewTelephone = itemView.findViewById(R.id.viewTelephone);
            viewEmail = itemView.findViewById(R.id.viewEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ShowActivity.class);
                    intent.putExtra("ID", listCustomers.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
