package com.example.testing.controllers.customers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.models.db.DbCustomer;

public class NewActivity extends AppCompatActivity {

    EditText txtName, txtTelephone, txtEmail;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        txtName = findViewById(R.id.txt_name);
        txtTelephone = findViewById(R.id.txt_telephone);
        txtEmail = findViewById(R.id.txt_email);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbCustomer dbCustomer = new DbCustomer(NewActivity.this);
                long id = dbCustomer.insertCustomer(txtName.getText().toString(), txtTelephone.getText().toString(), txtEmail.getText().toString());
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(NewActivity.this, "New register created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(NewActivity.this, "Error at saving register!", Toast.LENGTH_LONG).show();

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
}
