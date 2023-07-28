package com.example.testing.controllers.products;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.controllers.TemplatePDF.PDFGeneratorTemplate;
import com.example.testing.controllers.TemplatePDF.ProductPDFGenerator;
import com.example.testing.controllers.categories.CategoryMainActivity;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.deliveries.DeliveryMainActivity;
import com.example.testing.controllers.products.cotizations.ShowCotizationActivity;
import com.example.testing.models.db.DbProduct;
import com.example.testing.models.DecoratorProduct.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;

public class ProductMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {


    SearchView txtSearch;
    RecyclerView listProducts;
    ArrayList<Product> listArrayProducts;
    FloatingActionButton fabNew;
    FloatingActionButton fabPdf;
    ListProductAdapter adapter;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    DbProduct dbProduct;

    int id;
    String name;

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isAceptado -> {
                if (isAceptado)
                    Toast.makeText(this, "PERMISOS CONCEDIDOS", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "PERMISOS CANCELADOS", Toast.LENGTH_SHORT).show();
            }
    );






    //----------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);





        //------------------------ DRAWER ------------------------- //

        drawerLayout = findViewById(R.id.drawer_layout_products);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //--------------------------------------------------------- //


        txtSearch = findViewById(R.id.textSearch);
        listProducts = findViewById(R.id.listProducts);
        fabNew = findViewById(R.id.fabNew);
        fabPdf = findViewById(R.id.fabPdf);
        listProducts.setLayoutManager(new LinearLayoutManager(this));


        Intent extras = getIntent();


        id = extras.getIntExtra("ID", 0);
        name = extras.getStringExtra("name");


        listArrayProducts = new ArrayList<>();
        dbProduct = new DbProduct(ProductMainActivity.this);
        adapter = new ListProductAdapter(this, dbProduct.showThoseProducts(id), name, id);
        listProducts.setAdapter(adapter);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductMainActivity.this, NewProductActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        //-------------------------- GENERAR COTIZACION -----------------------------------//
        fabPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PDFGeneratorTemplate categoryProductPDFGenerator = new ProductPDFGenerator(ProductMainActivity.this, requestPermissionLauncher, id, name, dbProduct );
                categoryProductPDFGenerator.generatePDF(view);

            }
        });
        //---------------------------------------------------------------------------------//

        txtSearch.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }


}