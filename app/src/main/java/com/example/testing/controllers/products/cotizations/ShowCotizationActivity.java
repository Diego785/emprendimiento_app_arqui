package com.example.testing.controllers.products.cotizations;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.testing.BuildConfig;
import com.example.testing.R;
import com.example.testing.controllers.TemplatePDF.CotizationPDFGenerator;
import com.example.testing.controllers.TemplatePDF.PDFGeneratorTemplate;
import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.db.DbCustomer;
import com.example.testing.models.db.DbProduct;
import com.example.testing.controllers.products.ListProductAdapter;
import com.example.testing.models.Cotizations;
import com.example.testing.models.DecoratorProduct.Product;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;


import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ShowCotizationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    Double price;
    Double total = 0.0;
    Integer quantity;
    byte[] muestra;

    EditText txtPrice;
    EditText txtQuantity;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItemsCustomers;
    ArrayList<Product> listAllProducts;


    Button add;
    Button generate;
    Button borrar;

    Product product;
    Integer customerId;


    ListProductAdapter adapter;



    //-------------------------- GENERAR COTIZACION -----------------------------------//
    ArrayList<Cotizations> listCotizations = new ArrayList<Cotizations>();


    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isAceptado -> {
                if (isAceptado)
                    Toast.makeText(this, "PERMISOS CONCEDIDOS", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "PERMISOS CANCELADOS", Toast.LENGTH_SHORT).show();
            }

    );

    ArrayList<Cotizations> myArray = new ArrayList<>();

    private void verifyPermissions(View view, ArrayList<Cotizations> cotizationsArrayList) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISOS CONCEDIDOS", Toast.LENGTH_SHORT).show();
            createPDF(cotizationsArrayList);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )) {
            Snackbar.make(view, "ESTE PERMISO ES NECESARIO PARA CREAR EL ARCHIVO", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            });
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void createPDF(ArrayList<Cotizations> cotizationsArrayList) {
        try {

            //String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + folder;
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cotization.pdf";
            File dir = new File(path);
            if (!dir.exists()) {
                try {
                    System.out.println(dir.toString());
                    dir.createNewFile();
                    System.out.println(dir.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //File archivo = new File(dir, "cotization.pdf");
            FileOutputStream fos = new FileOutputStream(dir);
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, fos);

            document.open();

            Paragraph title = new Paragraph("Cotización de Productos en DHV Bussines\n\n\n", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
            document.add(title);

            PdfPTable table = new PdfPTable(4);
            table.addCell("NOMBRE");
            table.addCell("PRECIO VENTA");
            table.addCell("CANTIDAD REQUERIDA");
            table.addCell("MUESTRA");


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
            document.add(table);


            Paragraph budget = new Paragraph("\n\n\n Total: " + total, FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
            document.add(budget);

            document.close();


            String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cotization.pdf";
            File file2 = new File(path2);

            if (file2.exists()) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/pdf");

                Uri documento = Uri.fromFile(file2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    documento = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file2);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                DbCustomer dbCustomer = new DbCustomer(ShowCotizationActivity.this);
                Customers customer = dbCustomer.viewCustomers(customerId);
                intent.putExtra(Intent.EXTRA_STREAM, documento);
                intent.putExtra("jid", "591" + customer.getTelephone() + "@s.whatsapp.net");
                intent.putExtra(Intent.EXTRA_TEXT, "Este es nuestro Catalogo");
                intent.setPackage("com.whatsapp.w4b");

                startActivity(Intent.createChooser(intent, "Enviar PDF a través de:"));

            } else {
                Toast.makeText(this, "Documento no encontrado en: " + dir.toString(), Toast.LENGTH_SHORT).show();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private void enviarMensajeWhatsAppBusiness(String numeroTelefono, String mensaje) {
        try {
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + numeroTelefono + "&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp.w4b"); // Paquete de WhatsApp Business
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            } else {
                Toast.makeText(this, "WhatsApp Business no está instalado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //----------------------------------------------------------------------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.testing.R.layout.activity_show_cotization);
        //GlobalVars.cotizationsArrayList = new ArrayList<>();

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        txtPrice = findViewById(R.id.txt_price);
        txtQuantity = findViewById(R.id.txt_quantity);
        add = findViewById(R.id.btn_añadir);
        generate = findViewById(R.id.btn_generar);
        borrar = findViewById(R.id.btn_delete);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras == null) {
                customerId = Integer.parseInt(null);
            } else {
                customerId = extras.getInt("ID");
            }
        } else {
            customerId = (int) savedInstanceState.getSerializable("ID");
        }


        DbProduct dbProduct = new DbProduct(ShowCotizationActivity.this);
        listAllProducts = dbProduct.showProducts();


        String[] items = new String[listAllProducts.size()];

        for (int i = 0; i < listAllProducts.size(); i++) {
            items[i] = listAllProducts.get(i).getName();
        }


        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_cotization_product, items);
        autoCompleteTxt.setAdapter(adapterItems);


        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
                product = dbProduct.findByProductName(item);
                txtPrice.setText(Double.toString(product.getPrice()));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // METHOD FIND
                try {
                    price = Double.parseDouble(txtPrice.getText().toString());
                    quantity = Integer.parseInt(txtQuantity.getText().toString());
                    total = total + price * quantity;
                    total = Math.round(total * Math.pow(10, 2)) / Math.pow(10, 2);
                    listCotizations.add(new Cotizations(product.getName(), price, quantity, product.getPhoto()));
                    Toast.makeText(getApplicationContext(), "Producto " + String.valueOf(listCotizations.get(0).getMuestra()) + " agregado correctamente!", Toast.LENGTH_SHORT).show();
                    clean();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR: " + e, Toast.LENGTH_SHORT).show();

                }
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifyPermissions(view);
                //Toast.makeText(getApplicationContext(), String.valueOf(listCotizations.size()), Toast.LENGTH_SHORT).show();
                //verifyPermissions(view, listCotizations);
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowCotizationActivity.this);
                builder.setMessage("¿Cómo desea generar la cotización?")
                        .setPositiveButton("Generar PDF", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PDFGeneratorTemplate categoryProductPDFGenerator = new CotizationPDFGenerator(ShowCotizationActivity.this, requestPermissionLauncher,  listCotizations, total );
                                categoryProductPDFGenerator.generatePDF(view);


                                String folder = "/misPdfs";
                                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                        .getAbsolutePath() + folder + "/cotization-using-template-method.pdf";
                                //String path2 = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/cotization-using-template-method.pdf";
                                File file2 = new File(path);

                                if(file2.exists()){
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("application/pdf");

                                    Uri documento = Uri.fromFile(file2);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                        documento = FileProvider.getUriForFile(ShowCotizationActivity.this, BuildConfig.APPLICATION_ID + ".provider", file2);
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    }
                                    DbCustomer dbCustomer = new DbCustomer(ShowCotizationActivity.this);
                                    Customers customer = dbCustomer.viewCustomers(customerId);
                                    intent.putExtra(Intent.EXTRA_STREAM, documento);
                                    intent.putExtra("jid", "591" +customer.getTelephone()+"@s.whatsapp.net");
                                    intent.putExtra(Intent.EXTRA_TEXT, "Este es nuestro Catalogo");
                                    intent.setPackage("com.whatsapp.w4b");

                                    startActivity(Intent.createChooser(intent, "Enviar PDF a través de:")); // se queda, la función de strategy abarca todo lo de arriba

                                } else {
                                    Toast.makeText(ShowCotizationActivity.this, "Documento no encontrado en: " + file2.toString(), Toast.LENGTH_SHORT).show();
                                }
                                /*DbCustomer dbCustomer = new DbCustomer(ShowCotizationActivity.this);
                                ContextoCotization context = new ContextoCotization();
                                context.setStrategy(new CotizationPDF());
                                context.selectCotization();*/


                            }
                        })
                        .setNegativeButton("Generar Texto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(ShowActivity.this, "Customer not deleted!", Toast.LENGTH_LONG);
                                DbCustomer dbCustomer = new DbCustomer(ShowCotizationActivity.this);
                                Customers customer = dbCustomer.viewCustomers(customerId);
                                String telefono = "591" + customer.getTelephone();
                                String mensaje = "Hola " + customer.getName() + ", esta es tu cotización: \n" +
                                        "\n PRODUCTO -> PRECIO -> CANTDIDAD \n";

                                for (int j = 0; j < listCotizations.size(); j++) {
                                    mensaje = mensaje + listCotizations.get(j).getName() + " -> " + listCotizations.get(j).getPrice() + " -> " + listCotizations.get(j).getQuantity() + "\n";
                                }
                                mensaje = mensaje + "\n" +
                                        "TOTAL = " + total;
                                enviarMensajeWhatsAppBusiness(telefono, mensaje);


                            }
                        }).show();
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCotizations = new ArrayList<>();
                clean();
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }

    public void clean() {
        autoCompleteTxt.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

}