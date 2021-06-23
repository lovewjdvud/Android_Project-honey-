package com.ao.team.SignUp_J;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ao.team.Login_j.Id_find_fragment_j;
import com.ao.team.Login_j.Login_s;
import com.ao.team.MainActivity;
import com.ao.team.NetworkTask_J.NetworkTask_j;
import com.ao.team.R;
import com.ao.team.Signup_bean_j.Signup_Bean;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_j extends AppCompatActivity implements  Signup_Agree_fragment_j.OnApplySelectedListener {
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    ////        유효성        시작       ///

    // 이메일
    private String emailVakidation = "^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$"; // 이메일 유효성 검사
    // 비밀번호
   private String password_ex ="^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$";
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    ////                    유효성        끝       ///
    Intent intent;
    String cId, cPw, cName, cTelno, cEmail, cAddress1, cAddress2, cPostNum;
    ArrayList<Signup_Bean> id_confirm_result;

    EditText ed_signup_id_j, ed_signup_pw_j, ed_signup_pw_confirm_j, ed_signup_name_j, ed_signup_phone_j, ed_signup_emaile_j;
    TextView tv_signup_id_j, tv_signup_pw_confirm_j, tv_signup_phone_confirm_j, tv_signup_email_confirm_j,tv_agree_j;
    Button btn_sign_id_confirm_j, btn_sign_pw_confirm_j, btn_sign_end_j,btn_sign_id_confirm_j2;
    LinearLayout lt_signup_pw_j, lt_signup_pw_confirm_j, lt_signup_name_confirm_j;
    FrameLayout signup_framelayout;

    Signup_Agree_fragment_j signup_agree_fragment_j;
//    Intent intent2 = getIntent();
//    String macIP = intent2.getStringExtra("macIP");
    String macIP = MainActivity.macIP;
    String urlAddr = "http://" + macIP + ":8080/honey/honey_signup_j.jsp?";  //회원가입 완료
    String id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_signup_confirm_j.jsp?";  //아이디 중복체크

    //
    // 주소 검색 //
    EditText ed_signup_address1_j,ed_signup_address2_j,ed_signup_address_num_j;
    Button btn_sign_address_j;

    // http:// :8080/honey/honey_signup_confirm_j.jsp?
    //192.168.9.76 초심
    //192.168.35.204  우리집
    //  192.168.2.12 내 휴대폰



    //http://192.168.9.76:8080/test/studentInsertReturn.jsp?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_j);



        //// 레이아웃들 불러오기

        ed_signup_id_j = findViewById(R.id.ed_signup_id_j);
        ed_signup_pw_j = findViewById(R.id.ed_signup_pw_j);
        ed_signup_pw_confirm_j = findViewById(R.id.ed_signup_pw_confirm_j);
        ed_signup_name_j = findViewById(R.id.ed_signup_name_j);
        ed_signup_phone_j = findViewById(R.id.ed_signup_phone_j);
        ed_signup_emaile_j = findViewById(R.id.ed_signup_emaile_j);
        tv_agree_j = findViewById(R.id.tv_agree_j);
        tv_signup_id_j = findViewById(R.id.tv_signup_id_j);
        tv_signup_pw_confirm_j = findViewById(R.id.tv_signup_pw_confirm_j);  //비밀번호 텓스트뷰
        tv_signup_phone_confirm_j = findViewById(R.id.tv_signup_phone_confirm_j);
        tv_signup_email_confirm_j = findViewById(R.id.tv_signup_email_confirm_j);
        btn_sign_id_confirm_j = findViewById(R.id.btn_sign_id_confirm_j);
        btn_sign_pw_confirm_j = findViewById(R.id.btn_sign_pw_confirm_j);
        btn_sign_end_j = findViewById(R.id.btn_sign_end_j);

//        signup_framelayout= findViewById(R.id.signup_framelayout);

        lt_signup_pw_j = findViewById(R.id.lt_signup_pw_j);
        lt_signup_pw_confirm_j = findViewById(R.id.lt_signup_pw_confirm_j);
        lt_signup_name_confirm_j = findViewById(R.id.lt_signup_name_confirm_j);

//        signup_framelayout.setVisibility(View.INVISIBLE);
//
//        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
//        Signup_Agree_fragment_j signup_agree_fragment_j = new Signup_Agree_fragment_j();
//        transaction3.replace(R.id.ly_login_framelayout,signup_agree_fragment_j );
//        transaction3.commit();



        ///                     버튼 클릭              ////
        btn_sign_id_confirm_j.setOnClickListener(id_confirm_click); ////    id 중복 체크 버튼
//        btn_sign_end_j.setOnClickListener(onclick);  ////    회원가입 완료 버튼 누를시
        btn_sign_pw_confirm_j.setOnClickListener(pw_click);   // 비밀번호 일치 확인


        //               아이디 중복 확인 전까지 외에 항목들 레이아웃 숨기기               //
        tv_signup_pw_confirm_j.setVisibility(View.INVISIBLE);
        lt_signup_pw_j.setVisibility(View.INVISIBLE);
        lt_signup_pw_confirm_j.setVisibility(View.INVISIBLE);
        lt_signup_name_confirm_j.setVisibility(View.INVISIBLE);
        //               아이디 중복 확인 전까지 외에 항목들 레이아웃 숨기기  끝             //





         ///////////// 주소   시작//////////////////
        ed_signup_address1_j = findViewById(R.id.ed_signup_address1_j);
        ed_signup_address2_j = findViewById(R.id.ed_signup_address2_j);
        ed_signup_address_num_j =findViewById(R.id.ed_signup_address_num_j);
        btn_sign_address_j = findViewById(R.id.btn_sign_address_j);

        if (btn_sign_address_j != null) {
            btn_sign_address_j.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(SignUp_j.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }
        ///////////// 주소   끝//////////////////



        // 약관 동의 밑줄
        int a = 11;
        onCatagoryApplySelected(a); //약관을 클릭 안할시 기본값은

        SpannableString content = new SpannableString("약관 동의");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_agree_j.setText(content);
        tv_agree_j.setOnClickListener(clickagree);

        /////////////////       이메일 유효성 검사  ///////////////////////
        ed_signup_emaile_j.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String email = ed_signup_emaile_j.getText().toString().trim();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    tv_signup_email_confirm_j.setText("이메일 형식으로 입력해주세요");
                    tv_signup_email_confirm_j.setTextColor(Color.RED);
                } else {
                    tv_signup_email_confirm_j.setText("");
                    tv_signup_email_confirm_j.setTextColor(Color.BLACK);

                }

            }
        });




    } //onCreate

    /////////////////////////////////////////    회원가입 완료 버튼 누를시   ///////////////////////////////////////////////////////

    @Override

    public void onCatagoryApplySelected( int signup_agree_data) {
        btn_sign_end_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        ////    회원가입 완료 버튼 누를시


                String tv_phone_j = tv_signup_phone_confirm_j.getText().toString();
                String tv_email_j = tv_signup_email_confirm_j.getText().toString();


                String ed_name = ed_signup_name_j.getText().toString();
                String name_column = "cName";


                String ed_telno = ed_signup_phone_j.getText().toString();
                String telno_column = "cTelno";
                String ed_telno_result = connectGetData(ed_telno, telno_column); //중복체크한 후의 name 값
                Log.v("cTelno", "여기는 회원가입 버튼클릭" + ed_telno_result);
                id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_signup_confirm_j.jsp?";


                String ed_email = ed_signup_emaile_j.getText().toString();
                String email_column = "cEmail";
                String ed_email_result = connectGetData(ed_email, email_column); //중복체크한 후의 name 값
                Log.v("cEmail", "여기는 회원가입 버튼클릭" + ed_email_result);


//            id_confirm_urlAddr = id_confirm_urlAddr + "cId=" + column + "&clientid=" + clientid;
//
//            String id_confirm_result = connectGetData(value_signup, column); //중복체크한 후의 값


                if (ed_name.length() == 0) {
                    Toast.makeText(SignUp_j.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();


                } else if (ed_telno.length() == 0) {
                    Toast.makeText(SignUp_j.this, "전화번호를 입력해주세요!", Toast.LENGTH_SHORT).show();


                } else if (ed_email.length() == 0) {
                    Toast.makeText(SignUp_j.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (ed_telno.equals(ed_telno_result)) {

                    Toast.makeText(SignUp_j.this, "이미 사용중인 전화번호 입니다", Toast.LENGTH_SHORT).show();

                    tv_signup_phone_confirm_j.setText("이미 사용중인 번호 입니다 다른 번호를 입력하세요");
                    tv_signup_phone_confirm_j.setTextColor(Color.RED);


                } else if (ed_email.equals(ed_email_result)) {

                    Toast.makeText(SignUp_j.this, "이미 사용중인 이메일 입니다", Toast.LENGTH_SHORT).show();

                    tv_signup_email_confirm_j.setText("이미 사용중인 이메일 입니다 다른 이메일을 입력하세요");
                    tv_signup_email_confirm_j.setTextColor(Color.RED);
                    tv_signup_phone_confirm_j.setText("");

                } else if (signup_agree_data !=1 || signup_agree_data ==11 ) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SignUp_j.this);
                    dlg.setTitle("꿀 재료"); //제목
                    dlg.setMessage( "약관에 전체 동의를 해주셔야 합니다"); // 메시지
                    dlg.setIcon(R.drawable.honey_j); // 아이콘 설정
                    dlg.setPositiveButton("닫기",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            }
                    });

                    dlg.show();


                } else if (ed_email.length() != 0 && ed_telno.length() != 0 && ed_name.length() != 0) {           // 모든 항목 입력시


                    String dd = "sa";
                    /////////////////////////      회원가입 정보 DB넣는 과정                ////////////////////
                    cId = ed_signup_id_j.getText().toString();
                    cPw = ed_signup_pw_j.getText().toString();
                    cName = ed_signup_name_j.getText().toString();
                    cTelno = ed_signup_phone_j.getText().toString();
                    cEmail = ed_signup_emaile_j.getText().toString();
                    cAddress1 = ed_signup_address1_j.getText().toString();
                    cAddress2 = ed_signup_address2_j.getText().toString();
                    cPostNum = ed_signup_address_num_j.getText().toString();
                    // 동의 안할시 여기에 내보내기


                    urlAddr = urlAddr + "cId=" + cId + "&cPw=" + cPw + "&cName=" + cName + "&cTelno=" + cTelno + "&cEmail=" + cEmail + "&cAddress1=" + cAddress1+ "&cAddress2=" + cAddress2+ "&cPostNum=" + cPostNum;

                    Log.v("message", "urlAddr=" + urlAddr);

                    String result = connectInsertData();

                    Log.v("message", "result=" + result);


                    if (result.equals("1")) {


                        Toast.makeText(SignUp_j.this, cId + "가 입력되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp_j.this, Login_s.class);
                        startActivity(intent);
                        finish();

                    } else {// 입력 실패시
                        Toast.makeText(SignUp_j.this, "입력이 실패 하였습니다.", Toast.LENGTH_SHORT).show();

                    }

                }
                id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_signup_confirm_j.jsp?";
            }

    });
}//onCatagoryApplySelected

    private String connectInsertData() {

        String result = null;

        Log.v("message", "connectInsertData=" + urlAddr);
        try {
            NetworkTask_j networkTask = new NetworkTask_j(SignUp_j.this, urlAddr, "insert");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
        //잘끝났으면 1 아니면 에러
    }
    /////////////////////////////////////////    회원가입 완료 버튼 누를시  끝 ///////////////////////////////////////////////////////


    /////////////////////////////////////////     아이디 중복체크 버튼 시작 ///////////////////////////////////////////////////////
    View.OnClickListener id_confirm_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {





            String clientid = ed_signup_id_j.getText().toString(); //유저가 아이디 중복 체크할때 넣은 아이디
            String column = "cId";//컬럼 값
            String empty = "";  //아이디가 비었을때
            String empty2 = null;


            String id_confirm_result = connectGetData(clientid, column); //중복체크한 후의 값


            Log.v("message", "여기는 중복 IF문 들어가그 전입니다" + "중복체크후 돌아오는값" + id_confirm_result + "들어가기 전값 =" + clientid + "하하하=" + empty);
            Log.v("체크후", id_confirm_result);
            Log.v("들어가기전", clientid);
            Log.v("empty", empty);

            if (clientid.length() == 0) {
                Toast.makeText(SignUp_j.this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                tv_signup_id_j.setText("아이디를 입력해주세요");
                tv_signup_id_j.setTextColor(Color.RED);

            } else if (clientid.equals(id_confirm_result)) {
                Toast.makeText(SignUp_j.this, "이미 사용중인 아이디입니다 ", Toast.LENGTH_SHORT).show();
                tv_signup_id_j.setText("다른 아이디를 사용해주세요");
                tv_signup_id_j.setTextColor(Color.RED);

            } else if (clientid != id_confirm_result) {
                Toast.makeText(SignUp_j.this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                tv_signup_id_j.setText("사용 가능한 아이디 입니다");
                tv_signup_id_j.setTextColor(Color.BLACK);

                tv_signup_pw_confirm_j.setVisibility(View.VISIBLE);
                lt_signup_pw_j.setVisibility(View.VISIBLE);
                lt_signup_pw_confirm_j.setVisibility(View.VISIBLE);

            }
            id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_signup_confirm_j.jsp?";
        }

        ;
    };

    /////////////////////////////////////////     아이디 중복체크 버튼 끝///////////////////////////////////////////////////////


    @Override
    //수정되면 또 실행함 꼭 필요
    protected void onResume() {
        super.onResume();

    }

    private String connectGetData(String value_signup, String column) {
        id_confirm_urlAddr = id_confirm_urlAddr + "cId=" + column + "&clientid=" + value_signup;

        String id = "";

        Log.v("message", "여기는 아이디 중복체크하는  urlAddr 확인하는거=" + id_confirm_urlAddr);

        try {
            NetworkTask_j networkTask = new NetworkTask_j(SignUp_j.this, id_confirm_urlAddr, "select");
            Object obj = networkTask.execute().get();

            id_confirm_result = (ArrayList<Signup_Bean>) obj;
            id = id_confirm_result.get(0).getId();
            Log.v("message", "여기는 아이디 중복체크하는  urlAddr 확인하는거=" + id);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }


    /////////////////////////////////////////비밀번호 일치 확인///////////////////////////////////////////////////////
    View.OnClickListener pw_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


//            Boolean tt = Pattern.matches(pwPattern,password);


            String pw = ed_signup_pw_j.getText().toString();
            String pw_confirm = ed_signup_pw_confirm_j.getText().toString();

            String password = ed_signup_pw_j.getText().toString();




             if (pw.equals(pw_confirm)) {
                tv_signup_pw_confirm_j.setText("비밀번호가 일치합니다");
                tv_signup_pw_confirm_j.setTextColor(Color.BLACK);
                Toast.makeText(SignUp_j.this, "비밀번호가 일치 합니다", Toast.LENGTH_SHORT).show();
                lt_signup_name_confirm_j.setVisibility(View.VISIBLE); // 나머지 레이아웃들이 보여라


            } else if (pw != pw_confirm) {
                tv_signup_pw_confirm_j.setText("비밀번호가 일치하지 않습니다");
                tv_signup_pw_confirm_j.setTextColor(Color.RED);
                Toast.makeText(SignUp_j.this, "비밀번호가 일치하지 않습니다 확인해주세요!", Toast.LENGTH_SHORT).show();

            }


        }
    };
/////////////////////////////////////////비밀번호 일치 확인///////////////////////////////////////////////////////


    /////////////////       비밀번호 유효성 검사 시작  ///////////////////////


    // 비밀번호 검사
    public static boolean validatePassword (String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }


    /////////////////       비밀번호 유효성 검사 끝  ///////////////////////

       ////            약관 동의           //
    View.OnClickListener clickagree = new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               signup_agree_fragment_j = new Signup_Agree_fragment_j();
               signup_agree_fragment_j.show(getSupportFragmentManager(), signup_agree_fragment_j.getTag());

           }
       };


      ////////////////////////  주소 검색        ////////////////////////

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {

                        ed_signup_address1_j.setClickable(false);
                        ed_signup_address_num_j.setClickable(false);


//                        ed_signup_address1_j.setText(data);
                        ed_signup_address_num_j.setText(data.substring(0,5));  //우편번호
                        ed_signup_address1_j.setText(data.substring(7));   // 주소
                        ed_signup_address2_j.requestFocus();   // 포커스

                    }

                }
                break;
        }
    }

}  //MainActivity
