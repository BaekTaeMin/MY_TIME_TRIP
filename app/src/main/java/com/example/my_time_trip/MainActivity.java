package com.example.my_time_trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv_todo;
    private FloatingActionButton mBtn_write;
    private ArrayList<TodoItemVO> mTodoItems;
    private DBHelper mDBHelper; // m은 멤버라는 의미 = 전역변수
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {
        // 초기화 작업
        mDBHelper = new DBHelper(this);
        mRv_todo = findViewById(R.id.rv_todo);
        mBtn_write = findViewById(R.id.btn_write);
        mTodoItems = new ArrayList<>();

        // load recent DB
        loadRecentDB();

        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialog는 팝업창
                Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit);    // dialog_edit이 연결되어 findViewById로 선택 가능
                EditText et_title = dialog.findViewById(R.id.et_title);
                EditText et_content = dialog.findViewById(R.id.et_content);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Insert DB
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(), currentTime);

                        // Insert UI
                        TodoItemVO item = new TodoItemVO();
                        item.setTitle(et_title.getText().toString());
                        item.setContent(et_content.getText().toString());
                        item.setWriteDate(currentTime);

                        mAdapter.addItem(item);

                        // 최신 데이터가 올라갈 때 마다 최신 포지션인 0위 위치로 포커싱 되도록 함
                        mRv_todo.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "할일 목록이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }

    private void loadRecentDB() {
        // 저장되어있던 DB를 가져온다.
        mTodoItems = mDBHelper.SelectAllTodo();
        if(mAdapter == null){   // DB가 없을 경우 다시 만들기
            mAdapter = new CustomAdapter(mTodoItems, this); // mainActivity 쪽의 Context 정보를 넘긴다.
            mRv_todo.setHasFixedSize(true);
            mRv_todo.setAdapter(mAdapter);
        }
    }
}