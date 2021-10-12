package com.example.projectsupercars;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(vehicleName TEXT primary key, numberPlate TEXT, mileage TEXT)");
    }

    @Override    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertvehicledata(String vehicleName, String numberPlate, String mileage) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vehicleName", vehicleName);
        contentValues.put("numberPlate", numberPlate);
        contentValues.put("mileage", mileage);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean deletevehicledata(String vehicleName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where vehicleName = ?", new String[]{vehicleName});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "vehicleName=?", new String[]{vehicleName});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public Cursor getvehicledata () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;

    }
}
