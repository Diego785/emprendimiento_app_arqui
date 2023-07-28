package com.example.testing.controllers.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.testing.R;
import com.example.testing.controllers.categories.CategoryMainActivity;
import com.example.testing.controllers.customers.EditActivity;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.customers.ShowActivity;
import com.example.testing.controllers.deliveries.DeliveryMainActivity;
import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.db.BuilderProduct.ProductInterfaceBuilder;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.db.DbProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class ShowProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText txtName, txtPrice, txtQuantity;
    ImageView imgProduct;
    FloatingActionButton fabEdit, fabDelete;
    ProductInterfaceBuilder product;
    int id, category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);


        txtName = findViewById(R.id.txt_name);
        imgProduct = findViewById(R.id.img_product);
        txtPrice = findViewById(R.id.txt_price);
        txtQuantity = findViewById(R.id.txt_quantity);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras == null) {
                id = 0;
                category_id = 0;
            } else {
                id = extras.getInt("ID");
                category_id = extras.getInt("category");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
            category_id = (int) savedInstanceState.getSerializable("category");
        }

        DbProduct dbProduct = new DbProduct(ShowProductActivity.this);
        product = dbProduct.viewProduct(id);


        if (product != null) {
            txtName.setText(product.buildGetName());
            imgProduct.setImageBitmap(byteArrayToBitmap(product.buildGetPhoto()));
            txtPrice.setText(String.valueOf(product.buildeGetPrice()));
            txtQuantity.setText(String.valueOf(product.buildGetQuantity()));
            txtName.setInputType(InputType.TYPE_NULL);
            txtQuantity.setInputType(InputType.TYPE_NULL);
            txtPrice.setInputType(InputType.TYPE_NULL);
        }


        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowProductActivity.this);
                builder.setMessage("¿Desea eliminar este producto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (dbProduct.deleteProduct(id)) {
                                    limpiar();
                                    viewData();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

    }

    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void viewData() {
        Intent intent = new Intent(this, ProductMainActivity.class);
        intent.putExtra("ID", category_id);
        startActivity(intent);
    }

    private void limpiar() {
        txtName.setText("");
        imgProduct.setImageBitmap(null);
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_clients:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_products:
                Intent intent1 = new Intent(this, CategoryMainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.nav_deliveries:
                Intent intent3 = new Intent(this, DeliveryMainActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}