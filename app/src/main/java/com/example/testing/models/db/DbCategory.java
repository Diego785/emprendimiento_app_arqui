package com.example.testing.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.testing.models.Categories;

import java.util.ArrayList;

public class DbCategory extends DbHelper {
    Context context;

    public DbCategory(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public ArrayList<Categories> showCategories() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Categories> listCategories = new ArrayList<>();
        Categories categories = null;

        Cursor cursorCategory = null;


        cursorCategory = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);
        if (cursorCategory.moveToFirst()) {
            do {
                categories = new Categories();
                categories.setId(cursorCategory.getInt(0));
                categories.setName(cursorCategory.getString(1));
                listCategories.add(categories);
            } while (cursorCategory.moveToNext());
        }
        cursorCategory.close();
        return listCategories;
    }

    public String findCategory(int id){
        String name = "";
        DbHelper dbHelperPosition = new DbHelper(context);
        SQLiteDatabase db = dbHelperPosition.getWritableDatabase();

        Categories category = null;

        Cursor cursorCategory = null;


        cursorCategory = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE id = " + id, null);
        if (cursorCategory.moveToFirst()) {
            do {
                category = new Categories();
                name = cursorCategory.getString(1);


            } while (cursorCategory.moveToNext());
        }
        cursorCategory.close();
        return name;
    }

    public long insertCategory(String nombre) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", nombre);


            id = db.insert(TABLE_CATEGORY, null, values);
            return id;
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }



}
