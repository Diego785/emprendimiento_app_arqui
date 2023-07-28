package com.example.testing.controllers.products.cotizations;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.models.DecoratorProduct.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListProductCotizationAdapter extends RecyclerView.Adapter<ListProductCotizationAdapter.ProductViewHolder> {


    ArrayList<Product> listProduct;
    ArrayList<Product> listOriginalProduct;


    private Context context;

    String name;


    public ListProductCotizationAdapter(Context context, ArrayList<Product> listProduct, String name) {
        this.listProduct = listProduct;
        this.context = context;
        this.name = name;
        this.listOriginalProduct = new ArrayList<>();
        listOriginalProduct.addAll(listProduct);

    }

    @NonNull
    @Override
    public ListProductCotizationAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, null , false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductCotizationAdapter.ProductViewHolder holder, int product) {
        holder.name.setText(listProduct.get(product).getName());
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(listProduct.get(product).getPhoto(), 0, listProduct.get(product).getPhoto().length ));
        holder.price.setText(Double.toString(listProduct.get(product).getPrice()));
        holder.quantity.setText(Integer.toString(listProduct.get(product).getQuantity()));


    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public void filter(String txtSearch){
        int length = txtSearch.length();
        if(length== 0){
            listProduct.clear();
            listProduct.addAll(listOriginalProduct);
        }else{
            List<Product> collection = listProduct.stream()
                    .filter(i -> i.getName().toLowerCase().contains(txtSearch.toLowerCase()))
                    .collect(Collectors.toList());
            listProduct.clear();
            listProduct.addAll(collection);
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
    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, quantity;
        ImageView image;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.viewName);
            image = itemView.findViewById(R.id.imageView);
            price = itemView.findViewById(R.id.viewPrice);
            quantity = itemView.findViewById(R.id.viewQuantity);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Llamar método para mostrar vista previa
                    showPreview(listProduct.get(getAdapterPosition()).getPhoto());
                }
            });

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalVars.cotizationsArrayList.add(new Cotizations("HOLA MUNDO", 5.5, 10));

                }
            });*/


        }

    }

}
