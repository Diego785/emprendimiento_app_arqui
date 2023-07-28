package com.example.testing.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.testing.models.PrototypePerson.Customers;

import java.util.ArrayList;

public class DbCustomer extends DbHelper {

    Context context;

    public DbCustomer(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertCustomer(String nombre, String telephone, String email) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", nombre);
            values.put("telephone", telephone);
            values.put("email", email);

            id = db.insert(TABLE_CUSTOMER, null, values);
            return id;
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    public Customers findByCustomerName(String name) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Customers customer = null;
        Cursor cursorCustomer;
        cursorCustomer = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER + " WHERE name = '" + name + "' LIMIT 1", null);
        if (cursorCustomer.moveToFirst()) {
            do {
                customer = new Customers();
                customer.setName(cursorCustomer.getString(1));
                customer.setTelephone(cursorCustomer.getString(2));

            } while (cursorCustomer.moveToNext());
        }
        cursorCustomer.close();
        return customer;
    }

    public ArrayList<Customers> showCustomers() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Customers> listCustomers = new ArrayList<>();
        Customers customer = null;
        Cursor cursorCustomer = null;

        cursorCustomer = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER, null);
        if (cursorCustomer.moveToFirst()) {
            do {
                customer = new Customers();
                customer.setId(cursorCustomer.getInt(0));
                customer.setName(cursorCustomer.getString(1));
                customer.setTelephone(cursorCustomer.getString(2));
                customer.setEmail(cursorCustomer.getString(3));
                listCustomers.add(customer);
            } while (cursorCustomer.moveToNext());
        }
        cursorCustomer.close();
        return listCustomers;
    }

    public Customers viewCustomers(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Customers customer = null;
        Cursor cursorCustomer;

        cursorCustomer = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorCustomer.moveToFirst()) {
            customer = new Customers();
            customer.setId(cursorCustomer.getInt(0));
            customer.setName(cursorCustomer.getString(1));
            customer.setTelephone(cursorCustomer.getString(2));
            customer.setEmail(cursorCustomer.getString(3));
        }
        cursorCustomer.close();
        return customer;
    }

    public boolean editCustomer(int id, String nombre, String telephone, String email) {
        boolean isCorrect = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try {

            db.execSQL("UPDATE " + TABLE_CUSTOMER + " SET name = '" + nombre + "', telephone = '" + telephone + "',email = '" + email + "' WHERE id = '" + id + "'");
            isCorrect = true;
        } catch (Exception e) {
            e.toString();
            isCorrect = false;
        }finally {
            db.close();
        }
        return isCorrect;
    }

    public boolean deleteCustomer(int id) {
        boolean isCorrect = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try {

            db.execSQL("DELETE FROM " + TABLE_CUSTOMER + " WHERE id = '" + id +"'");
            isCorrect = true;
        } catch (Exception e) {
            e.toString();
            isCorrect = false;
        }finally {
            db.close();
        }
        return isCorrect;
    }
}
