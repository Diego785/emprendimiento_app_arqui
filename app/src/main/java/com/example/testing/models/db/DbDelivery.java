package com.example.testing.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.testing.models.PrototypePerson.Customers;
import com.example.testing.models.PrototypePerson.Deliveries;

import java.util.ArrayList;

public class DbDelivery extends DbHelper {
    Context context;
    public DbDelivery(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertDelivery(String nombre, String telephone, int edad, String sexo) {
        long id = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", nombre);
            values.put("telephone", telephone);
            values.put("edad", edad);
            values.put("sexo", sexo);

            id = db.insert(TABLE_DELIVERY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Deliveries> showDeliveries() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Deliveries> listDeliveries = new ArrayList<>();
        Cursor cursorDelivery = null;
        try {
            cursorDelivery = db.rawQuery("SELECT * FROM " + TABLE_DELIVERY, null);
            if (cursorDelivery.moveToFirst()) {
                do {
                    Deliveries delivery = new Deliveries();
                    delivery.setId(cursorDelivery.getInt(0));
                    delivery.setName(cursorDelivery.getString(1));
                    delivery.setTelephone(cursorDelivery.getString(2));
                    delivery.setEdad(cursorDelivery.getInt(3));
                    delivery.setSexo(cursorDelivery.getString(4));

                    listDeliveries.add(delivery);
                } while (cursorDelivery.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursorDelivery != null) {
                cursorDelivery.close();
            }
            db.close();
        }

        return listDeliveries;
    }

    public Deliveries viewDelivery(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Deliveries deliveries = null;
        Cursor cursorDelivery;

        cursorDelivery = db.rawQuery("SELECT * FROM " + TABLE_DELIVERY + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorDelivery.moveToFirst()) {
            deliveries = new Deliveries();
            deliveries.setId(cursorDelivery.getInt(0));
            deliveries.setName(cursorDelivery.getString(1));
            deliveries.setTelephone(cursorDelivery.getString(2));
            deliveries.setEdad(cursorDelivery.getInt(3));
            deliveries.setSexo(cursorDelivery.getString(4));
        }
        cursorDelivery.close();
        return deliveries;
    }

    public boolean deleteDelivery(int id) {
        boolean isCorrect = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try {

            db.execSQL("DELETE FROM " + TABLE_DELIVERY + " WHERE id = '" + id +"'");
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
