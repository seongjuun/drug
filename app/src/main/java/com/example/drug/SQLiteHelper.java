package com.example.drug;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "drug.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Drug (name TEXT PRIMARY KEY, date DATE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // DB 업그레이드 로직
    }

    public ArrayList<String> getDateDrugNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Drug", null);
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }
    public ArrayList<String> getDrugNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Drug", null);
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }
    public void insertDrug(String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Drug (name, date) VALUES('" + name + "', '" + date + "');");
    }
}
