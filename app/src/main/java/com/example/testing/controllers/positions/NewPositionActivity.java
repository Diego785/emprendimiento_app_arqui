package com.example.testing.controllers.positions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.models.db.DbPosition;

import java.io.IOException;

public class NewPositionActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 1;

    EditText txtName, txtUrl;
    Bitmap bitmap;
    Button selectImageBtn, btnSave;
    ImageView myImageView;

    // --------------------------------------- PICK IMAGE ----------------------------------------- //


    // --------------------------------------------------------------------------------------------- //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_position);

        txtName = findViewById(R.id.txt_name);
        txtUrl = findViewById(R.id.txt_url);
        btnSave = findViewById(R.id.btn_save);

        myImageView = findViewById(R.id.img_fachada);
        selectImageBtn = findViewById(R.id.btn_open_gallery);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbPosition dbPosition = new DbPosition(NewPositionActivity.this);
                long id = dbPosition.insertPosition(txtName.getText().toString(), txtUrl.getText().toString(), bitmap);
                limpiar();
                viewData();
                if(id>0){
                    Toast.makeText(NewPositionActivity.this, "New position created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(NewPositionActivity.this, "Error at saving position!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
                myImageView.setImageURI(selectedImageUri);
            }
        }
    }
    private void limpiar(){
        txtName.setText("");
        txtUrl.setText("");
        bitmap = null;
    }

    private void viewData(){
        Intent intent = new Intent(this, PositionMainActivity.class);
        startActivity(intent);
    }
}