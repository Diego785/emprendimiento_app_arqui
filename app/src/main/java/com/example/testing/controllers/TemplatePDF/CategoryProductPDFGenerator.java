package com.example.testing.controllers.TemplatePDF;

import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;

import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.db.DbCategory;
import com.example.testing.models.db.DbProduct;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryProductPDFGenerator extends PDFGeneratorTemplate{
    //ArrayList<Product> listProductsPDF;
    DbCategory dbCategory;
    DbProduct dbProduct;

    public CategoryProductPDFGenerator(Context context, ActivityResultLauncher<String> requestPermissionLauncher, DbCategory dbCategory, DbProduct dbProduct) {
        super(context, requestPermissionLauncher);
        //this.listProductsPDF = listProductsPDF;
        this.dbCategory = dbCategory;
        this.dbProduct = dbProduct;
    }

    @Override
    protected String getTitle() {
        return "Catálogo de DHV Business\n\n\n";
    }

    @Override
    protected String getFileName() {
        return "all-products-using-template-method.pdf.pdf";
    }

    @Override
    protected int getColumnCount() {
        return 5;
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
        table.addCell("CATEGORÍA");
        table.addCell("MUESTRA");
    }

    @Override
    protected void addTableRows(PdfPTable table) {
        ArrayList<Product> listProductsPDF = new ArrayList<>();
        listProductsPDF = dbProduct.showProducts();
        for (int i = 0; i < listProductsPDF.size(); i++) {
            if(listProductsPDF.size()>0){
                table.addCell(listProductsPDF.get(i).getName());
                table.addCell(Double.toString(listProductsPDF.get(i).getPrice()));
                table.addCell(Integer.toString(listProductsPDF.get(i).getQuantity()));
                table.addCell(dbCategory.findCategory(listProductsPDF.get(i).getCategory_id()));
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
