package com.example.testing.controllers.deliveries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.controllers.customers.MainActivity;
import com.example.testing.controllers.customers.NewActivity;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.db.DbDelivery;

public class NewDeliveryActivity extends AppCompatActivity {

    EditText txtName, txtTelephone, txtEdad, txtSexo;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        txtName = findViewById(R.id.txt_name);
        txtTelephone = findViewById(R.id.txt_telephone);
        txtEdad = findViewById(R.id.txt_edad);
        txtSexo = findViewById(R.id.txt_sexo);
        btnSave = findViewById(R.id.btn_save_delivery);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbDelivery dbDelivery = new DbDelivery(NewDeliveryActivity.this);
                long id = dbDelivery.insertDelivery(txtName.getText().toString(), txtTelephone.getText().toString(), Integer.parseInt(txtEdad.getText().toString()), txtSexo.getText().toString());

                if(id>0){
                    limpiar();
                    viewData();
                    Toast.makeText(NewDeliveryActivity.this, "New register created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(NewDeliveryActivity.this, "Error at saving register!", Toast.LENGTH_LONG).show();

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