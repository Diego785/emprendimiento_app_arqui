package com.example.testing.controllers.categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.models.db.DbCategory;

public class NewCategoryActivity extends AppCompatActivity {

    EditText txtName;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        txtName = findViewById(R.id.txt_name);

        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbCategory dbCategory = new DbCategory(NewCategoryActivity.this);
                long id = dbCategory.insertCategory(txtName.getText().toString());
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(NewCategoryActivity.this, "New register created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(NewCategoryActivity.this, "Error at saving register!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    private void limpiar(){
        txtName.setText("");
    }
    private void viewData(){
        Intent intent = new Intent(this, CategoryMainActivity.class);
        startActivity(intent);
    }
}