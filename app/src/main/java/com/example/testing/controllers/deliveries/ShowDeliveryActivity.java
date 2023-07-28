package com.example.testing.controllers.deliveries;

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
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.customers.ShowActivity;
import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.PrototypePerson.Deliveries;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.db.DbDelivery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowDeliveryActivity extends AppCompatActivity {

    EditText txtName, txtTelephone, txtEdad, txtSexo;
    FloatingActionButton fabDelete, fabClone;

    Deliveries delivery;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_delivery);
        txtName = findViewById(R.id.txt_name);
        txtTelephone = findViewById(R.id.txt_telephone);
        txtEdad = findViewById(R.id.txt_edad);
        txtSexo = findViewById(R.id.txt_sexo);
        txtTelephone = findViewById(R.id.txt_telephone);
        fabDelete = findViewById(R.id.fabDelete);
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

        DbDelivery dbDelivery = new DbDelivery(ShowDeliveryActivity.this);
        delivery = dbDelivery.viewDelivery(id);

        if (delivery != null) {
            txtName.setText(delivery.getName());
            txtTelephone.setText(delivery.getTelephone());
            txtEdad.setText(String.valueOf(delivery.getEdad()));
            txtSexo.setText(delivery.getSexo());
            txtName.setInputType(InputType.TYPE_NULL);
            txtTelephone.setInputType(InputType.TYPE_NULL);
            txtEdad.setInputType(InputType.TYPE_NULL);
            txtSexo.setInputType(InputType.TYPE_NULL);
        }
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDeliveryActivity.this);
                builder.setMessage("¿Desea eliminar este cliente?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (dbDelivery.deleteDelivery(id)) {
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

        fabClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbDelivery dbDelivery = new DbDelivery(ShowDeliveryActivity.this);
                Deliveries clonedDelivery = (Deliveries) delivery.clone();
                long id = dbDelivery.insertDelivery(clonedDelivery.getName(), clonedDelivery.getTelephone(), clonedDelivery.getEdad(), clonedDelivery.getSexo());
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(ShowDeliveryActivity.this, "Customer prototype cloned successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ShowDeliveryActivity.this, "Error at cloning customer!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void limpiar(){
        txtName.setText("");
        txtTelephone.setText("");
        txtEdad.setText("");
        txtSexo.setText("");
    }
    private void viewData(){
        Intent intent = new Intent(this, DeliveryMainActivity.class);
        startActivity(intent);
    }
}