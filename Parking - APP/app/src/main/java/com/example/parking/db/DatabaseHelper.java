package com.example.parking.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper (Context context)
    {
        super(context,"UsersDb.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, password text,firstName text,petName text, hunger int, happiness int, food int,coins int, steps int,lastModifiedTime text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public boolean insert (String email,String password,String firstName,String lastName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("firstName",firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("lastModifiedTime",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        long ins = database.insert("user",null,contentValues);
        return ins != -1;
    }

    public Boolean isMailAvailable(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        return cursor.getCount() <= 0;
    }

    public Boolean isEmailAndPasswordMatch(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=? and password=?",new String[]{email,password});
        return cursor.getCount() > 0;
    }

    public String getFirstNameFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getString(2);
    }
    public String getLastNameFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getString(3);
    }
    public String getDateFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getString(9);
    }



}
