package com.example.tamagotchiar.db;

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

    public boolean insert (String email,String password,String firstName,String petName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("firstName",firstName);
        contentValues.put("petName",petName);
        contentValues.put("hunger", 100);
        contentValues.put("happiness", 120);
        contentValues.put("food",3);
        contentValues.put("coins",90);
        contentValues.put("steps",0);
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
    public String getPetNameFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getString(3);
    }
    public int getHungerFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getInt(4);
    }
    public int getHappinessFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getInt(5);
    }
    public int getFoodFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getInt(6);
    }
    public int getCoinsFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getInt(7);
    }
    public int getStepsFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getInt(8);
    }
    public String getDateFromDatabase(String email){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from user where email=?",new String[]{email});
        cursor.moveToFirst();
        return cursor.getString(9);
    }

    public void updateHungerInDatabase(String email, Integer hunger){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hunger",hunger);
        database.update("user",contentValues,"email=?",new String[]{email});
    }
    public void updateHappinessInDatabase(String email, Integer happiness){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("happiness",happiness);
        database.update("user",contentValues,"email=?",new String[]{email});
    }
    public void updateFoodInDatabase(String email, Integer food){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("food",food);
        database.update("user",contentValues,"email=?",new String[]{email});
    }
    public void updateCoinsInDatabase(String email, Integer coins){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("coins",coins);
        database.update("user",contentValues,"email=?",new String[]{email});
    }
    public void updateDateInDatabase(String email, String lastModifiedTime){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lastModifiedTime",lastModifiedTime);
        database.update("user",contentValues,"email=?",new String[]{email});
    }
}
