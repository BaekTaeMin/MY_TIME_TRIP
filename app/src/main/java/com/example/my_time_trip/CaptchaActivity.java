package com.example.my_time_trip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CaptchaActivity extends AppCompatActivity {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private TextView mCaptcha_View;
    private EditText mCaptcha_Write;
    private Button mBtn_OK;

    //알파벳 및 숫자 랜덤 생성
    public static String generateRandomString() {
        int length = 7;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length()); //난수 생성
            char randomChar = CHARACTERS.charAt(randomIndex); //문자열에서 난수번째 문자를 가져오기
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.captcha);
        mCaptcha_View = findViewById(R.id.captchaView);
        mCaptcha_Write = findViewById(R.id.captchaWrite);
        mBtn_OK = findViewById(R.id.btn_OK);
        mCaptcha_View.setText(generateRandomString());

        String text = mCaptcha_View.getText().toString();

        mBtn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chkText = mCaptcha_Write.getText().toString();
                if(text.equals(chkText)) { //표시된 문자열과 입력한 문자열이 같을 시
                    Intent intent = new Intent(CaptchaActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CaptchaActivity.this, "일치하지 않습니다..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
