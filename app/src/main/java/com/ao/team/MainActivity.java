package com.ao.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ao.team.Login_j.Login_s;
import com.ao.team.SignUp_J.SignUp_j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    Button btn;

    public static String macIP ="192.168.35.128";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHashKey();
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Log.v("start", "여기는 MainActivity");

             ///   로구인으로 보내기
                Intent intent = null;
                intent = new Intent(MainActivity.this, Login_s.class);
                intent.putExtra("macIP",macIP);
                startActivity(intent);
//                ///   로구인으로 보내기
//                Intent  intent2= null;
//                 intent2 = new Intent(MainActivity.this, SignUp_j.class);
//                intent2.putExtra("macIP",macIP);
//                startActivity(intent2);

           }
        });

    }

     ///////////////////// 해시 키 얻어오기 위한 메소드  //////////
     private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", android.util.Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
           ///////////////////// 해시 키 얻어오기 위한 메소드  //////////

}