package com.example.testing.controllers.categories;

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
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.controllers.TemplatePDF.CategoryProductPDFGenerator;
import com.example.testing.controllers.TemplatePDF.PDFGeneratorTemplate;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.deliveries.DeliveryMainActivity;
import com.example.testing.controllers.products.cotizations.ShowCotizationActivity;
import com.example.testing.models.db.DbCategory;
import com.example.testing.models.db.DbProduct;
import com.example.testing.models.Categories;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CategoryMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView listCategories;
    ArrayList<Categories> listArrayCategories;
    FloatingActionButton fabNew;
    FloatingActionButton fabPdf;
    ListCategoryAdapter adapter;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DbCategory dbCategory;
    DbProduct dbProduct;




   // ArrayList<Product> listProductsPDF = new ArrayList<>();

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isAceptado -> {
                if (isAceptado)
                    Toast.makeText(this, "PERMISOS CONCEDIDOS", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "PERMISOS CANCELADOS", Toast.LENGTH_SHORT).show();
            }

    );





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);

        //------------------------ DRAWER ------------------------- //

        drawerLayout = findViewById(R.id.drawer_layout_category);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //--------------------------------------------------------- //


        listCategories = findViewById(R.id.listCategories);
        fabNew = findViewById(R.id.fabNew);
        fabPdf = findViewById(R.id.fabPdf);
        listCategories.setLayoutManager(new LinearLayoutManager(this));

        dbProduct = new DbProduct(CategoryMainActivity.this);
        dbCategory = new DbCategory(CategoryMainActivity.this);


        listArrayCategories = new ArrayList<>();
        adapter = new ListCategoryAdapter(this, dbCategory.showCategories());
        listCategories.setAdapter(adapter);


        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryMainActivity.this, NewCategoryActivity.class);
                startActivity(intent);
            }
        });
        fabPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PDFGeneratorTemplate categoryProductPDFGenerator = new CategoryProductPDFGenerator(CategoryMainActivity.this, requestPermissionLauncher, dbCategory, dbProduct );
                categoryProductPDFGenerator.generatePDF(view);
            }
        });
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


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}