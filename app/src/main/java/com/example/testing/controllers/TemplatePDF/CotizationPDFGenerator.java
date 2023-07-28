package com.example.testing.controllers.TemplatePDF;

import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;

import com.example.testing.models.Cotizations;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.ArrayList;

public class CotizationPDFGenerator extends PDFGeneratorTemplate {

    Double total;
    ArrayList<Cotizations> cotizationsArrayList;
    public CotizationPDFGenerator(Context context, ActivityResultLauncher<String> requestPermissionLauncher, ArrayList<Cotizations> listCotizations, Double total) {
        super(context, requestPermissionLauncher);
        this.cotizationsArrayList = listCotizations;
        this.total = total;
    }

    @Override
    protected String getTitle() {
        return "Cotización de Productos en DHV Bussines\n\n\n";
    }

    @Override
    protected String getFileName() {
        return "cotization-using-template-method.pdf";
    }

    @Override
    protected int getColumnCount() {
        return 4;
    }

    @Override
    protected double getTotal() { return total; }
    @Override
    protected void addTableHeaders(PdfPTable table) {
        table.addCell("NOMBRE");
        table.addCell("PRECIO VENTA");
        table.addCell("CANTIDAD REQUERIDA");
        table.addCell("MUESTRA");
    }

    @Override
    protected void addTableRows(PdfPTable table) {
        for (int i = 0; i < cotizationsArrayList.size(); i++) {
            table.addCell(cotizationsArrayList.get(i).getName());
            table.addCell(Double.toString(cotizationsArrayList.get(i).getPrice()));
            table.addCell(Integer.toString(cotizationsArrayList.get(i).getQuantity()));


            Image image = null;
            try {
                image = Image.getInstance(cotizationsArrayList.get(i).getMuestra());
            } catch (BadElementException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            table.addCell(image);
        }



    }


}
