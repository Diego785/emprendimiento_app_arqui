package com.example.testing.controllers.positions;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.models.Positions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListPositionAdapter extends RecyclerView.Adapter<ListPositionAdapter.PositionViewHolder>{

    ArrayList<Positions> listPosition;
    ArrayList<Positions> listOriginalPosition;
    private Context context;

    public ListPositionAdapter(Context context, ArrayList<Positions> listPosition) {
        this.context = context;
        this.listPosition = listPosition;
        this.listOriginalPosition = new ArrayList<>();
        this.listOriginalPosition.addAll(listPosition);

    }

    @NonNull
    @Override
    public ListPositionAdapter.PositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_position, null , false);
        return new PositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPositionAdapter.PositionViewHolder holder, int position) {
        holder.name.setText(listPosition.get(position).getName());
        holder.url.setText(listPosition.get(position).getUrl());
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(listPosition.get(position).getPhoto(), 0, listPosition.get(position).getPhoto().length ));
    }

    @Override
    public int getItemCount() {
        return listPosition.size();
    }


    public void filter(String txtSearch){
        int length = txtSearch.length();
        if(length== 0){
            listPosition.clear();
            listPosition.addAll(listOriginalPosition);
        }else{
            List<Positions> collection = listPosition.stream()
                    .filter(i -> i.getName().toLowerCase().contains(txtSearch.toLowerCase()))
                    .collect(Collectors.toList());
            listPosition.clear();
            listPosition.addAll(collection);
        }
        notifyDataSetChanged();
    }
    private void showPreview(byte[] imageBytes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.preview_dialog, null);
        builder.setView(dialogView);

        // Obtener ImageView y establecer la imagen en el diálogo de vista previa
        ImageView previewImage = dialogView.findViewById(R.id.preview_image);
        previewImage.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));

        // Mostrar el diálogo
        builder.create().show();
    }
    public class PositionViewHolder extends RecyclerView.ViewHolder {

        TextView name, url;
        ImageView image;
        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.viewName);
            url = itemView.findViewById(R.id.viewUrl);
            image = itemView.findViewById(R.id.imageView);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Llamar método para mostrar vista previa
                    showPreview(listPosition.get(getAdapterPosition()).getPhoto());
                }
            });


        }

    }

}
