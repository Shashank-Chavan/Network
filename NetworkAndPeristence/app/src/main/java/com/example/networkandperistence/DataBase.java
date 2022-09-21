package com.example.networkandperistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.temporal.TemporalAccessor;

public class DataBase extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "LOGIN_TABLE.db";
    public static final String DB_TABLE = "TABLE";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "pass_word";
    public DataBase(@Nullable Context context) {
        super(context,DB_NAME,null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         /*String create = "CREATE TABLE " + DB_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + "TEXT," + KEY_PASSWORD + "TEXT" + ")";
                sqLiteDatabase.execSQL(create);*/
        sqLiteDatabase.execSQL("create Table user(username TEXT  primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        //onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("drop Table if exists user");
    }

    public Boolean Register(params p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",p.getUsername());
        values.put("password",p.getPassword());
        long result = db.insert("user",null,values);
        if(result==-1)return false;
        else return true;

    }
    int Login(String username,String password){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //String select = "SELECT * FROM LOGIN_TABLE where user_name = ? and pass_word = ?";
        int flag = 1;

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from user where username = ? and password = ?", new String[] {username,password});
        /*if(cursor.getCount()>0){
            flag =1;
        }*/
      /*  if(cursor.moveToFirst())
        {
            do{
                params pa = new params();
                pa.setId(Integer.parseInt(cursor.getString(0)));
                pa.setUsername(cursor.getString(1));
                pa.setPassword(cursor.getString(2));
            }
            while (cursor.moveToNext());*/

        while (cursor.moveToNext()){
            String user = cursor.getString(1);
            String pass = cursor.getString(2);
            if(user.equals(username)&&pass.equals(password)){
                flag = 1;
            }
        }

        return flag;
}
}
