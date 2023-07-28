package com.example.testing.models.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME  = "dhv-business.db";
    public static final String TABLE_CUSTOMER  = "t_customers";
    public static final String TABLE_POSITIONS  = "t_positions";

    public static final String TABLE_CATEGORY  = "t_categories";
    public static final String TABLE_PRODUCT  = "t_products";
    public static final String TABLE_DELIVERY  = "t_deliveries";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CUSTOMER + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "telephone TEXT NOT NULL," +
                "email TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_POSITIONS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "url TEXT NOT NULL," +
                "photo BLOB, " +
                "client_id INTEGER, "+
                "FOREIGN KEY(client_id) REFERENCES t_customers(id))");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORY + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCT + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "photo BLOB, " +
                "price DOUBLE NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "category_id INTEGER, "+
                "FOREIGN KEY(category_id) REFERENCES t_categories(id))");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DELIVERY + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "telephone TEXT NOT NULL," +
                "edad INTEGER," +
                "sexo STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if (tableExists(sqLiteDatabase, TABLE_CUSTOMER))
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        if (tableExists(sqLiteDatabase, TABLE_POSITIONS))
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITIONS);
        if (tableExists(sqLiteDatabase, TABLE_CATEGORY))
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        if (tableExists(sqLiteDatabase, TABLE_PRODUCT))
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        if (tableExists(sqLiteDatabase, TABLE_DELIVERY))
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);

        onCreate(sqLiteDatabase);


    }
    private boolean tableExists(SQLiteDatabase db, String tableName) {
        String query = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tableName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
