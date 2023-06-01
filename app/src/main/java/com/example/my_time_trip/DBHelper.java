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
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (cno INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, writeDate TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        onCreate(db);
    }

    // SELECT
    public ArrayList<TodoItemVO> SelectAllTodo(){
        ArrayList<TodoItemVO> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase(); // 읽기
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null); // db 포인터

        if(cursor.getCount() != 0){// 데이터를 성공적으로 받아온 경우
            cursor.moveToFirst();
            int columnIndexCno = cursor.getColumnIndex("cno");
            int columnIndexTitle = cursor.getColumnIndex("title");
            int columnIndexContent = cursor.getColumnIndex("content");
            int columnIndexWriteDate = cursor.getColumnIndex("toDate");

            if (columnIndexCno != -1 && columnIndexTitle != -1 && columnIndexContent != -1 && columnIndexWriteDate != -1) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    int cno = cursor.getInt(columnIndexCno);
                    String title = cursor.getString(columnIndexTitle);
                    String content = cursor.getString(columnIndexContent);
                    String writeDate = cursor.getString(columnIndexWriteDate);

                    TodoItemVO todoItem = new TodoItemVO();
                    todoItem.setCno(cno);
                    todoItem.setTitle(title);
                    todoItem.setContent(content);
                    todoItem.setWriteDate(writeDate);

                    todoItems.add(todoItem);
                }
            }
        }
        cursor.close();
        return todoItems;
    }

    // INSERT
    public void InsertTodo(String _title, String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();  // 데이터를 db에 작성
        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES(" +
                "'" + _title + "', '" + _content + "','" + _writeDate + "');");
    }

    // UPDATE
    public void UpdateTodo(String _title, String _content, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET " +
                "title='" + _title +"', content='" + _content+ "', writeDate='" + _writeDate + "' WHERE writeDate='" + _beforeDate + "'");
    }

    // DELETE
    public void DeleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate='" + _beforeDate + "' ");
    }
}
