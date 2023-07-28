package com.example.testing.controllers.positions;

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

import com.example.testing.R;
import com.example.testing.controllers.categories.CategoryMainActivity;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.deliveries.DeliveryMainActivity;
import com.example.testing.controllers.products.cotizations.ShowCotizationActivity;
import com.example.testing.models.db.DbPosition;
import com.example.testing.models.Positions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class PositionMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    SearchView txtSearch;
    RecyclerView listPositions;
    ArrayList<Positions> listArrayPositions;
    FloatingActionButton fabNew;
    ListPositionAdapter adapter;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_main);


        //------------------------ DRAWER ------------------------- //

        drawerLayout = findViewById(R.id.drawer_layout_position);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //--------------------------------------------------------- //

        txtSearch = findViewById(R.id.textSearch);
        listPositions = findViewById(R.id.listPositions);
        fabNew = findViewById(R.id.fabNew);
        listPositions.setLayoutManager(new LinearLayoutManager(this));

        DbPosition dbPosition = new DbPosition(PositionMainActivity.this);
        listArrayPositions = new ArrayList<>();

        adapter = new ListPositionAdapter(this, dbPosition.showPositions());
        listPositions.setAdapter(adapter);


        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PositionMainActivity.this, NewPositionActivity.class);
                startActivity(intent);
            }
        });
        txtSearch.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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