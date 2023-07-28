package com.example.testing.controllers.TemplatePDF;

import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;

import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.db.DbProduct;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.ArrayList;

public class ProductPDFGenerator extends PDFGeneratorTemplate{
    //ArrayList<Product> listProductsPDF;
    DbProduct dbProduct;
    Integer id;
    String name;

    public ProductPDFGenerator(Context context, ActivityResultLauncher<String> requestPermissionLauncher, int id, String name, DbProduct dbProduct) {
        super(context, requestPermissionLauncher);
        //this.listProductsPDF = listProductsPDF;
        //this.dbCategory = dbCategory;
        this.id = id;
        this.name = name;
        this.dbProduct = dbProduct;
    }

    @Override
    protected String getTitle() {
        return "Catálogo de la Categoría: " + name + "\n\n\n";
    }

    @Override
    protected String getFileName() {
        return "products-" + name+  "-using-template-method" +  ".pdf";
    }

    @Override
    protected int getColumnCount() {
        return 4;
    }

    @Override
    protected double getTotal() {
        return -1;
    }

    @Override
    protected void addTableHeaders(PdfPTable table) {
        table.addCell("NOMBRE");
        table.addCell("PRECIO UNITARIO");
        table.addCell("STOCK");
        table.addCell("MUESTRA");
    }

    @Override
    protected void addTableRows(PdfPTable table) {
        ArrayList<Product> listProductsPDF = new ArrayList<>();
        listProductsPDF = dbProduct.showThoseProducts(id); // Lista para el PDF
        for (int i = 0; i < listProductsPDF.size(); i++) {
            if(listProductsPDF.size()>0){
                table.addCell(listProductsPDF.get(i).getName());
                table.addCell(Double.toString(listProductsPDF.get(i).getPrice()));
                table.addCell(Integer.toString(listProductsPDF.get(i).getQuantity()));
                Image image = null;
                try {
                    image = Image.getInstance(listProductsPDF.get(i).getPhoto());
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                table.addCell(image);
            }
        }
    }
}
