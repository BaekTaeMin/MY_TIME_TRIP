package com.example.my_time_trip;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText mUserID, mUserPW;
    private DBUser mDBUser;
    private Button mBtn_login;
    private Button mBtn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        mUserID = findViewById(R.id.UserID);
        mUserPW = findViewById(R.id.UserPassword);
        mBtn_login = findViewById(R.id.btn_login);
        mBtn_signUp = findViewById(R.id.btn_signUp);

        mDBUser = new DBUser(this);
        SQLiteDatabase db = mDBUser.getWritableDatabase();

        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insertId = mUserID.getText().toString();
                String insertPw = mUserPW.getText().toString();
                if (mDBUser.SelectUser(insertId, insertPw)) { //회원가입한 아이디 있는지 확인
                    Intent intent = new Intent(LoginActivity.this, CaptchaActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //회원가입 버튼 눌렀을 때
        mBtn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //페이지 이동
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
