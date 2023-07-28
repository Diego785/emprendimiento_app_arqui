package com.example.testing.controllers.TemplatePDF;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.db.BuilderProduct.ProductInterfaceBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;





public abstract class PDFGeneratorTemplate {
    protected Context context;

    protected ActivityResultLauncher<String> requestPermissionLauncher;

    private boolean registrationInProgress = false;

    public PDFGeneratorTemplate(Context context, ActivityResultLauncher<String> requestPermissionLauncher) {
        this.context = context;
        this.requestPermissionLauncher = requestPermissionLauncher;
    }


    public void generatePDF(View view) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            showToast("PERMISOS CONCEDIDOS");
            createPDF();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                (ComponentActivity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showPermissionRationale(view);
        } else {
            registrationInProgress = true;
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void showPermissionRationale(View view) {
        Snackbar.make(view, "ESTE PERMISO ES NECESARIO PARA CREAR EL ARCHIVO", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", v -> {
                    registrationInProgress = true;
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                })
                .show();
    }

    //------------------------------------------------------------ MÉTODO PLANTILLA ----------------------------------------------------//
    private void createPDF() {
        try {
            String folder = "/misPdfs";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getAbsolutePath() + folder;
            File dir = new File(path);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    showToast("CARPETA CREADA");
                }
            }

            File archivo = new File(dir, getFileName());
            FileOutputStream fos = new FileOutputStream(archivo);
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, fos);

            document.open();

            Paragraph title = new Paragraph(getTitle(), FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
            document.add(title);

            PdfPTable table = new PdfPTable(getColumnCount());
            addTableHeaders(table);
            addTableRows(table);
            document.add(table);


            if(getTotal() != -1){
                Paragraph budget = new Paragraph("\n\n\n Total: " + getTotal(), FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
                document.add(budget);
            }

            document.close();
            showToast("PDF creado");
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            showToast("Error al crear PDF");
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------//

    protected abstract String getTitle();

    protected abstract String getFileName();

    protected abstract int getColumnCount();

    protected abstract void addTableHeaders(PdfPTable table);

    protected abstract void addTableRows(PdfPTable table);

    protected abstract double getTotal();
    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
