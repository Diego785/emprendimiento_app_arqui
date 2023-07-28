package com.example.testing.controllers.categories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.testing.controllers.customers.ShowActivity;
import com.example.testing.controllers.products.NewProductActivity;
import com.example.testing.controllers.products.ProductMainActivity;
import com.example.testing.models.Categories;
import com.example.testing.models.Positions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.CategoryViewHolder>{

    ArrayList<Categories> listCategory;
    private Context context;

    public ListCategoryAdapter(Context context, ArrayList<Categories> listCategories) {
        this.context = context;
        this.listCategory = listCategories;

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    @NonNull
    @Override
    public ListCategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, null , false);
        return new CategoryViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ListCategoryAdapter.CategoryViewHolder holder, int position) {
        holder.name.setText(listCategory.get(position).getName());
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.viewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ProductMainActivity.class);
                    intent.putExtra("ID", listCategory.get(getAdapterPosition()).getId());
                    intent.putExtra("name", listCategory.get(getAdapterPosition()).getName());
                    context.startActivity(intent);
                }
            });

        }

    }

}
