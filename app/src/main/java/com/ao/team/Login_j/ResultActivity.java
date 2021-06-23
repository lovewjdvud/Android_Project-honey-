package com.ao.team.Login_j;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ao.team.R;
import com.ao.team.SignUp_J.SignUp_j;


public class ResultActivity extends AppCompatActivity {

    private TextView tv_result1_j, tv_result2_j;
    private ImageView imageView_j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        Intent intent = getIntent();

       //결과값 받아오기
        String cId = intent.getStringExtra("cId");
        String nickName = intent.getStringExtra("Name"); //닉네임 전달받음
        String email = intent.getStringExtra("email"); //닉네임 전달받음

        Login_s.client_id = email;


        tv_result1_j = findViewById(R.id.result_text1);
        tv_result2_j = findViewById(R.id.result_text2);

        tv_result1_j.setText(email);  //닉네임 셋팅
        tv_result2_j.setText(cId);  //닉네임 셋팅


        //구글 정보 받아오기


//        Glide.with(this).load(photoUrl).into(imageView_S);     //이미지 셋팅


        //FirebaseAuth.getInstance().signOut(); 구글 로그아웃 시키기기




    }

}