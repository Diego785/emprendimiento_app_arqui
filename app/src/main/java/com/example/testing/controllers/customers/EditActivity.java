package com.example.testing.controllers.customers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.R;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.PrototypePerson.Customers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {


    EditText txtName, txtTelephone, txtEmail;
    Button btnSave;
    boolean isCorrect = false;
    FloatingActionButton fabEdit, fabDelete;


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
        fabEdit.setVisibility(View.INVISIBLE);
        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setVisibility(View.INVISIBLE);


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

        final DbCustomer dbCustomer = new DbCustomer(EditActivity.this);
        customer = dbCustomer.viewCustomers(id);

        if (customer != null) {
            txtName.setText(customer.getName());
            txtTelephone.setText(customer.getTelephone());
            txtEmail.setText(customer.getEmail());

        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtName.getText().toString().equals("") && !txtTelephone.getText().toString().equals("") && !txtEmail.getText().toString().equals("")) {
                    isCorrect = dbCustomer.editCustomer(id, txtName.getText().toString(), txtTelephone.getText().toString(), txtEmail.getText().toString());

                    if (isCorrect) {
                        Toast.makeText(EditActivity.this, "REGISTER MODIFIED SUCCESSFULLY!", Toast.LENGTH_LONG);
                        viewData();
                    } else {
                        Toast.makeText(EditActivity.this, "REGISTER MODIFIED SUCCESSFULLY!", Toast.LENGTH_LONG);
                    }

                } else {
                    Toast.makeText(EditActivity.this, "COMPLETE THE REQUIRED FIELDS!", Toast.LENGTH_LONG);

                }
            }
        });
    }

    private void viewRegister(){
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
    private void viewData(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
