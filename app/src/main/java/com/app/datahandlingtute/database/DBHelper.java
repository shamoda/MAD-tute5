package com.app.datahandlingtute.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_ENTRIES = "CREATE TABLE "+UsersMaster.Users.TABLE_NAME+ " ("+
                UsersMaster.Users._ID+ " INTEGER PRIMARY KEY, "+
                UsersMaster.Users.COLUMN_NAME_USERNAME+ " TEXT,"+
                UsersMaster.Users.COLUMN_NAME_PASSWORD+ " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //==================================================================================

    public long addInfo(String uName, String pwd){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersMaster.Users.COLUMN_NAME_USERNAME, uName);
        contentValues.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, pwd);

        long newRowId = sqLiteDatabase.insert(UsersMaster.Users.TABLE_NAME, null, contentValues);
        return newRowId;
    }


    //==================================================================================

    public List readAllInfo(String req){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            usernames.add(username);
            passwords.add(password);
        }

        cursor.close();

        if (req.equals("user")){
            return usernames;
        }
        if (req.equals("password")){
            return passwords;
        }else
            return usernames;

    }

    //==========================================================================================

    public void deleteData(String username){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(UsersMaster.Users.TABLE_NAME, UsersMaster.Users.COLUMN_NAME_USERNAME +" =? ", new String[]{username});
    }


    //===========================================================================================

    public void updateUser(String username, String password){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        sqLiteDatabase.update(UsersMaster.Users.TABLE_NAME, contentValues, UsersMaster.Users.COLUMN_NAME_USERNAME+" =? ", new String[]{username});

    }





}
