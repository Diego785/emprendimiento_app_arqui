package com.example.testing.controllers.deliveries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.testing.R;
import com.example.testing.controllers.categories.CategoryMainActivity;
import com.example.testing.controllers.customers.ListCustomersAdapter;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.customers.NewActivity;
import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.PrototypePerson.Deliveries;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.db.DbDelivery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DeliveryMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

    SearchView txtSearch;
    RecyclerView listDeliveries;
    ArrayList<Deliveries> listArrayDeliveries;
    FloatingActionButton fabNew;
    ListDeliveriesAdapter adapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);


        //------------------------ DRAWER ------------------------- //

        drawerLayout = findViewById(R.id.drawer_layout_delivery);
        navigationView = findViewById(R.id.nav_view_delivery);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //--------------------------------------------------------- //



        txtSearch = findViewById(R.id.textSearch);
        listDeliveries = findViewById(R.id.listDeliveries);
        fabNew = findViewById(R.id.fabNew);
        listDeliveries.setLayoutManager(new LinearLayoutManager(this));

        DbDelivery dbDelivery = new DbDelivery(DeliveryMainActivity.this);
        listArrayDeliveries = new ArrayList<>();


        if(dbDelivery.showDeliveries() == null){

            Intent intent = new Intent(DeliveryMainActivity.this, NewDeliveryActivity.class);
            startActivity(intent);

        }else{
            adapter = new ListDeliveriesAdapter(dbDelivery.showDeliveries());

            listDeliveries.setAdapter(adapter);
            fabNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DeliveryMainActivity.this, NewDeliveryActivity.class);
                    startActivity(intent);
                }
            });

            txtSearch.setOnQueryTextListener(this);
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