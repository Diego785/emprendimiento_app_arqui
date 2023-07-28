package com.example.testing.controllers.products;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.db.DbProduct;
import com.example.testing.models.DecoratorProduct.DiscountDecorator;
import com.example.testing.models.DecoratorProduct.OriginalDecorator;
import com.example.testing.models.DecoratorProduct.ProductInterfaceDecorator;

import java.io.IOException;

public class NewProductActivity extends AppCompatActivity {

    DbProduct dbProduct;


    private static final int SELECT_IMAGE = 1;

    EditText txtName, txtPrice, txtQuantity;
    Bitmap bitmap;
    Button selectImageBtn, btnSave, btnOriginalProduct, btnDiscountedProduct;
    boolean discounted = false, original = false;
    Spinner spinner;
    String selectedDiscount = "Sin descuento";


    ImageView myImageView;
    int category_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        txtName = findViewById(R.id.txt_name);
        txtPrice = findViewById(R.id.txt_price);
        txtQuantity = findViewById(R.id.txt_quantity);
        btnSave = findViewById(R.id.btn_save);
        btnOriginalProduct = findViewById(R.id.btn_save_original_product);
        btnDiscountedProduct = findViewById(R.id.btn_save_discounted_product);

        myImageView = findViewById(R.id.img_fachada);
        selectImageBtn = findViewById(R.id.btn_open_gallery);

        spinner = findViewById(R.id.spinner);



        Intent extras = getIntent();

        category_id = extras.getIntExtra("ID", 0);





        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });
        btnOriginalProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //APLICAR LA LÓGICA DEL ORIGINAL AQUÍ
                if(original == false) {
                    // Crear un objeto Product con el precio ingresado
                    ProductInterfaceDecorator product = new Product();
                    String name = txtName.getText().toString();
                    product.setName(name);

                    ProductInterfaceDecorator originalProduct = new OriginalDecorator(product);
                    txtName.setText(String.valueOf(originalProduct.getName()));

                    original = true;
                    Toast.makeText(NewProductActivity.this, "¡Producto definidio como Original!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(NewProductActivity.this, "¡El producto ya ha sido definido como original anteriormente!", Toast.LENGTH_LONG).show();

                }


                }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDiscount = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(NewProductActivity.this, selectedDiscount, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDiscount = "Sin descuento";

            }
        });
        btnDiscountedProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(discounted == false && !selectedDiscount.equals("Sin descuento")){
                    // Obtener el precio ingresado en el EditText
                    double price = Double.parseDouble(txtPrice.getText().toString());
                    String name = txtName.getText().toString();

                    // Crear un objeto Product con el precio ingresado
                    ProductInterfaceDecorator product = new Product();
                    product.setPrice(price);
                    product.setName(name);

                    double discount = 0;

                    // Aplicar el decorador de descuento
                    if(selectedDiscount.equals("5%")){
                        discount = 5;
                    } else if (selectedDiscount.equals("10%")) {
                        discount = 10;
                    } else if (selectedDiscount.equals("25%")) {
                        discount = 25;
                    } else if (selectedDiscount.equals("50%")) {
                        discount = 50;
                    }
                    ProductInterfaceDecorator discountedProduct = new DiscountDecorator(product, discount);

                    // Obtener el precio con descuento
                    double discountedPrice = discountedProduct.getPrice();

                    // Actualizar el precio en el EditText
                    txtPrice.setText(String.valueOf(discountedPrice));
                    txtName.setText(String.valueOf(discountedProduct.getName()));

                    discounted = true;

                    Toast.makeText(NewProductActivity.this, "Descuento aplicado del " + selectedDiscount, Toast.LENGTH_LONG).show();
                } else if (discounted == true) {
                    Toast.makeText(NewProductActivity.this, "¡El Descuento ya fue aplicado!", Toast.LENGTH_LONG).show();
                } else if (selectedDiscount.equals("Sin descuento")) {
                    Toast.makeText(NewProductActivity.this, "¡Seleccione un porcentaje de descuento!", Toast.LENGTH_LONG).show();
                }


            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbProduct = new DbProduct(NewProductActivity.this);
                long id = dbProduct.insertProduct(txtName.getText().toString(), bitmap, Double.parseDouble(txtPrice.getText().toString()), Integer.parseInt(txtQuantity.getText().toString()), category_id);
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(NewProductActivity.this, "New product created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(NewProductActivity.this, "Error at saving product!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
                myImageView.setImageURI(selectedImageUri);
            }
        }
    }
    private void limpiar(){
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        bitmap = null;
    }
    private void viewData(){
        Intent intent = new Intent(this, ProductMainActivity.class);
        intent.putExtra("ID", category_id);
        startActivity(intent);
    }
}