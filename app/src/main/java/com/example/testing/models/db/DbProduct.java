package com.example.testing.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.testing.models.DecoratorProduct.Product;
import com.example.testing.models.db.BuilderProduct.ProductInterfaceBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DbProduct extends DbHelper {

    Context context;
    ProductInterfaceBuilder productBuilder;

    public DbProduct(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Product> showProducts() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> listProducts = new ArrayList<>();
        Product product = null;

        Cursor cursorProduct = null;


        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);
        if (cursorProduct.moveToFirst()) {
            do {
                product = new Product();
                product.setId(cursorProduct.getInt(0));
                product.setName(cursorProduct.getString(1));
                product.setPhoto(cursorProduct.getBlob(2));
                product.setPrice(cursorProduct.getDouble(3));
                product.setQuantity(cursorProduct.getInt(4));
                product.setCategory_id(cursorProduct.getInt(5));

                listProducts.add(product);
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
        return listProducts;
    }

    public ArrayList<Product> showThoseProducts(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> listProducts = new ArrayList<>();
        Product product = null;

        Cursor cursorProduct = null;


        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE category_id = " + id, null);
        if (cursorProduct.moveToFirst()) {
            do {
                product = new Product();
                product.setId(cursorProduct.getInt(0));
                product.setName(cursorProduct.getString(1));
                product.setPhoto(cursorProduct.getBlob(2));
                product.setPrice(cursorProduct.getDouble(3));
                product.setQuantity(cursorProduct.getInt(4));
                product.setCategory_id(cursorProduct.getInt(5));

                listProducts.add(product);
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
        return listProducts;
    }

    public ArrayList<ProductInterfaceBuilder> showThoseProductsBuilder(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ProductInterfaceBuilder> listProducts = new ArrayList<>();
        ProductInterfaceBuilder productBuilder;

        Cursor cursorProduct = null;


        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE category_id = " + id, null);
        if (cursorProduct.moveToFirst()) {
            do {
                productBuilder = new Product();
                ProductInterfaceBuilder product = productBuilder
                        .buildId(cursorProduct.getInt(0))
                        .buildName(cursorProduct.getString(1))
                        .buildPhoto(cursorProduct.getBlob(2))
                        .buildPrice(cursorProduct.getDouble(3))
                        .buildQuantity(cursorProduct.getInt(4))
                        .buildCategory_id(cursorProduct.getInt(5))
                        .build();

                listProducts.add(product);
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
        return listProducts;
    }

    public Product findByProductName(String name) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Product product = null;
        Cursor cursorProduct;
        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE name = '" + name + "' LIMIT 1", null);
        if (cursorProduct.moveToFirst()) {
            do {
                product = new Product();
                product.setName(cursorProduct.getString(1));
                product.setPhoto(cursorProduct.getBlob(2));
                product.setPrice(cursorProduct.getDouble(3));
                product.setQuantity(cursorProduct.getInt(4));

            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
        return product;
    }


    public ProductInterfaceBuilder viewProduct(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorProduct;
        productBuilder = null;
        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE id = '" + id + "' LIMIT 1", null);
        if (cursorProduct.moveToFirst()) {
            do {
                productBuilder = new Product();
                productBuilder.buildName(cursorProduct.getString(1))
                        .buildPhoto(cursorProduct.getBlob(2))
                        .buildPrice(cursorProduct.getDouble(3))
                        .buildQuantity(cursorProduct.getInt(4))
                        .build();


            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
        return productBuilder;
    }


    public long insertProduct(String name, Bitmap photo, Double price, Integer quantity, Integer category_id) {
        long id = 0;

        try {
            DbHelper dbHelperPosition = new DbHelper(context);
            SQLiteDatabase db = dbHelperPosition.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("photo", getBytes(photo));
            values.put("price", price);
            values.put("quantity", quantity);
            values.put("category_id", category_id);

            id = db.insert(TABLE_PRODUCT, null, values);
            return id;
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }


    public boolean deleteProduct(int id) {
        boolean isCorrect = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("DELETE FROM " + TABLE_PRODUCT + " WHERE id = '" + id + "'");
            isCorrect = true;
        } catch (Exception e) {
            e.toString();
            isCorrect = false;
        } finally {
            db.close();
        }
        return isCorrect;
    }


    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}
