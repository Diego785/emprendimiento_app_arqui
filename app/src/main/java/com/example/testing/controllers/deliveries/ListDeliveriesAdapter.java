package com.example.testing.controllers.deliveries;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.controllers.customers.ShowActivity;
import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.PrototypePerson.Deliveries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListDeliveriesAdapter extends RecyclerView.Adapter<ListDeliveriesAdapter.DeliveryViewHolder> {

    ArrayList<Deliveries> listDeliveries;
    ArrayList<Deliveries> listOriginal;

    public ListDeliveriesAdapter(ArrayList<Deliveries> listDeliveries){
        this.listDeliveries = listDeliveries;
        listOriginal = new ArrayList<>();
        listOriginal.addAll(listDeliveries);
    }

    @NonNull
    @Override
    public ListDeliveriesAdapter.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_delivery, null , false);
        return new ListDeliveriesAdapter.DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDeliveriesAdapter.DeliveryViewHolder holder, int position) {
        holder.viewName.setText(listDeliveries.get(position).getName());
        holder.viewTelephone.setText(listDeliveries.get(position).getTelephone());
        holder.viewEdad.setText(Integer.toString(listDeliveries.get(position).getEdad()));
        holder.viewSexo.setText(listDeliveries.get(position).getSexo());
    }

    public void filter(String txtSearch){
        int length = txtSearch.length();
        if(length== 0){
            listDeliveries.clear();
            listDeliveries.addAll(listOriginal);
        }else{
            List<Deliveries> collection = listDeliveries.stream()
                    .filter(i -> i.getName().toLowerCase().contains(txtSearch.toLowerCase()))
                    .collect(Collectors.toList());
            listDeliveries.clear();
            listDeliveries.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listDeliveries.size();
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder {

        TextView viewName, viewTelephone, viewEdad, viewSexo;
        public DeliveryViewHolder(@NonNull View itemView){
            super(itemView);

            viewName = itemView.findViewById(R.id.viewName);
            viewTelephone = itemView.findViewById(R.id.viewTelephone);
            viewEdad = itemView.findViewById(R.id.viewEdad);
            viewSexo = itemView.findViewById(R.id.viewSexo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ShowDeliveryActivity.class);
                    intent.putExtra("ID", listDeliveries.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}

