package com.example.my_time_trip;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText createID, createPW;
    private DBUser mDBUser;
    private Button mBtn_SignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        createID = findViewById(R.id.UserID);
        createPW = findViewById(R.id.UserPassword);
        mBtn_SignUp = findViewById(R.id.btn_signUp);

        mDBUser = new DBUser(this);
        SQLiteDatabase db = mDBUser.getWritableDatabase();

        mBtn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = createID.getText().toString();
                String userPW = createPW.getText().toString();

                mDBUser.RegisterUser(userID, userPW);
                db.close();

                Toast.makeText(SignUpActivity.this, "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
