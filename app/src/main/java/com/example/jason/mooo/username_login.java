package com.example.jason.mooo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by jason on 16/08/15.
 */

public class username_login extends SQLiteOpenHelper implements Serializable{

    public static final String TABLE_USERS = "users";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_FIRST_NAME = "firstname";
    public static final String COL_SURNAME = "surname";
    public static final String COL_PASSWORD = "password";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    private static final String DATABASE_NAME = "user_login.db";
    private static final int DATABASE_VERSION = 1;

    public username_login(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FIRST_NAME + " TEXT, "
                + COL_SURNAME + " TEXT, "
                + COL_PASSWORD + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_USERNAME + " TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
        onCreate(db);
    }

    public int update(String table_name, long id, ContentValues values){
        String selection = COL_ID + " = ?";
        String[] selectionargs = {String.valueOf(id)};
        return getWritableDatabase().update(table_name, values, selection, selectionargs);
    }

    public int delete(String table_name, long id){
        String selection = COL_ID + " = ?";
        String[] selectionargs = {String.valueOf(id)};
        return getWritableDatabase().delete(table_name, selection, selectionargs);
    }

    public long insert(String table_name, ContentValues values){
        return getWritableDatabase().insert(table_name, null, values);
    }


    public Cursor queryLogin(String table_name, String ordered_by){
        //String[] projection = {COL_USERNAME, COL_PASSWORD};
        SQLiteDatabase datab = this.getWritableDatabase();
        String select_query = "SELECT * FROM " + TABLE_USERS;
        Cursor c = datab.rawQuery(select_query, null);
        return c;
    }
}
