package com.example.testing.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.testing.models.Positions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DbPosition extends DbHelper {

    Context context;

    public DbPosition(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Positions> showPositions() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Positions> listPositions = new ArrayList<>();
        Positions position = null;

        Cursor cursorPosition = null;


        cursorPosition = db.rawQuery("SELECT * FROM " + TABLE_POSITIONS, null);
        if (cursorPosition.moveToFirst()) {
            do {
                position = new Positions();
                position.setId(cursorPosition.getInt(0));
                position.setName(cursorPosition.getString(1));
                position.setUrl(cursorPosition.getString(2));
                position.setPhoto(cursorPosition.getBlob(3));
                listPositions.add(position);
            } while (cursorPosition.moveToNext());
        }
        cursorPosition.close();
        return listPositions;
    }



    public long insertPosition(String name, String url, Bitmap photo) {
        long id = 0;
        try {
            DbHelper dbHelperPosition = new DbHelper(context);
            SQLiteDatabase db = dbHelperPosition.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("url", url);
            values.put("photo", getBytes(photo));
            values.put("client_id", 1);

            id = db.insert(TABLE_POSITIONS, null, values);
            return id;
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
