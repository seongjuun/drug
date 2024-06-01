package com.example.drug;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper { // SQLiteOpenHelper 클래스 상속

    private static final String DATABASE_NAME = "drug.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Drug (name TEXT PRIMARY KEY, date DATE);");    // Drug 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // DB 업그레이드 로직
    }

    public ArrayList<String> getDateDrugNames(String date) {    // date 이하의 날짜에 대한 약물 이름을 반환
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Drug WHERE date <= ?", new String[]{date});
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }
    public ArrayList<String> getDrugNames() {   // 모든 약물 이름을 반환
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Drug", null);
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }
    public void insertDrug(String name, String date) {  // 약물 추가
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Drug (name, date) VALUES('" + name + "', '" + date + "');");
    }
}
