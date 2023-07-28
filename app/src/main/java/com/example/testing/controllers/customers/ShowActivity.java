package com.example.testing.controllers.customers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.controllers.positions.PositionMainActivity;
import com.example.testing.controllers.products.ShowProductActivity;
import com.example.testing.controllers.products.cotizations.ShowCotizationActivity;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.PrototypePerson.Customers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowActivity extends AppCompatActivity {

    EditText txtName, txtTelephone, txtEmail;
    Button btnSave;
    FloatingActionButton fabEdit, fabDelete, fabPosition, fabCotization, fabClone;

    Customers customer;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        txtName = findViewById(R.id.txt_name);
        txtTelephone = findViewById(R.id.txt_telephone);
        txtEmail = findViewById(R.id.txt_email);
        btnSave = findViewById(R.id.btn_save);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);
        fabPosition = findViewById(R.id.fabPosition);
        fabCotization = findViewById(R.id.fabCotization);
        fabClone = findViewById(R.id.fabClone);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbCustomer dbCustomer = new DbCustomer(ShowActivity.this);
        customer = dbCustomer.viewCustomers(id);

        if (customer != null) {
            txtName.setText(customer.getName());
            txtTelephone.setText(customer.getTelephone());
            txtEmail.setText(customer.getEmail());
            btnSave.setVisibility(View.INVISIBLE);
            txtName.setInputType(InputType.TYPE_NULL);
            txtTelephone.setInputType(InputType.TYPE_NULL);
            txtEmail.setInputType(InputType.TYPE_NULL);
        }


       // Toast.makeText(ShowActivity.this, "Soy la clase prototype clonada" + clonedCustomer.getName(), Toast.LENGTH_LONG).show();




        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this, EditActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);
                builder.setMessage("¿Desea eliminar este cliente?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (dbCustomer.deleteCustomer(id)) {
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
        fabPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this, PositionMainActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabCotization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this, ShowCotizationActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbCustomer dbCustomer = new DbCustomer(ShowActivity.this);
                Customers clonedCustomer = (Customers) customer.clone();
                long id = dbCustomer.insertCustomer(clonedCustomer.getName(), clonedCustomer.getTelephone(), clonedCustomer.getEmail());
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(ShowActivity.this, "Customer prototype cloned successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ShowActivity.this, "Error at cloning customer!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void limpiar(){
        txtName.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
    }
    private void viewData(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void list() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}