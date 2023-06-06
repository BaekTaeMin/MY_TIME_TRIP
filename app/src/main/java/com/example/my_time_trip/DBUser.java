package com.example.my_time_trip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBUser extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Todo.db";



    private SQLiteDatabase db;

    public DBUser(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User (userNO INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT NOT NULL, userPW TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) { onCreate(db); }

    // Select
    public boolean SelectUser(String id, String pw) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE userID = ? AND userPW = ?", new String[]{id,pw});
        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return isValid;
    }
    
    // Insert
    public void RegisterUser(String _userID, String _userPW){
        SQLiteDatabase db = getWritableDatabase();  // 데이터를 db에 작성
        ContentValues values = new ContentValues();
        values.put("userID", _userID);
        values.put("userPW", _userPW);
        long newRowId = db.insert("User", null, values);

        db.close();

        if (newRowId == -1) {
            // 삽입 실패
            Log.e("LoginActivity", "Failed to insert data into User table");
        } else {
            // 삽입 성공
            Log.d("LoginActivity", "Data inserted successfully into User table");
        }
    }
}
