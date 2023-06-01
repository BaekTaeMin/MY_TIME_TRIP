package com.example.my_time_trip;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// DB 엑세스를 도와주는 class
public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ToDo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성될 때 호출
        // 데이터베이스 -> 테이블 -> 컬럼 -> 값

        //SQL Query 문을 삽입하는 부분
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (" +
                "cno INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
                "toDate TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        onCreate(db);
    }

    // SELECT
    public ArrayList<TodoItemVO> SelectAllTodo(){
        ArrayList<TodoItemVO> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase(); // 읽기
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY toDate DESC", null); // db 포인터

        if(cursor.getCount() != 0){// 데이터를 성공적으로 받아온 경우
            cursor.moveToFirst();
            while(cursor.moveToNext()){ // 다음으로 이동할 데이터가 있을 때까지 반복
                int cno = cursor.getInt(cursor.getColumnIndex("cno"));
                String title  = cursor.getString(cursor.getColumnIndex("title"));
                String content  = cursor.getString(cursor.getColumnIndex("content"));
                String toDate  = cursor.getString(cursor.getColumnIndex("toDate"));

                TodoItemVO todoItem = new TodoItemVO();
                todoItem.setCno(cno);
                todoItem.setTitle(title);
                todoItem.setContent(content);
                todoItem.setToDate(toDate);
            }
        }
        cursor.close();
        return todoItems;
    }

    public void SelectTodo(){

    }

    // INSERT
    public void InsertTodo(String _title, String _content, String _toDate){
        SQLiteDatabase db = getWritableDatabase();  // 데이터를 db에 작성
        db.execSQL("INSERT INTO TodoList (title, content, toDate) VALUES(" +
                "'" + _title + "', '" + _content + "','" + _toDate + "');");
    }

    // UPDATE
    public void UpdateTodo(String _title, String _content, String _toDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ToList SET " +
                "title='" + _title +"', content='" + _content+ "', _writeDate='" + _toDate + "'");
    }

    // DELETE
    public void DeleteTodo(String _cno){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE cno='" + _cno + "' ");
    }
}
