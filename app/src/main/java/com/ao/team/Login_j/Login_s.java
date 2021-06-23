package com.ao.team.Login_j;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ao.team.MainActivity;
import com.ao.team.NetworkTask_J.NetworkTask_j;
import com.ao.team.SignUp_J.SignUp_j;
import com.ao.team.R;
import com.ao.team.SignUp_J.Signup_Agree_fragment_j;
import com.ao.team.Signup_bean_j.Signup_Bean;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;

public class Login_s extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
        /// 구글 로그인을 위한 객채 시작  //


     public static String client_id;  // 로그인시 값 변환
    private SignInButton btn_google_j; //구글 로그인 버튼
    private FirebaseAuth auth_j; // 파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient_j; // 구글 API 클라이언트 객체
    private  static final int REO_SIGN_GOOGLE = 100; //구글 로그인 결과 코드
    LinearLayout linearLayout;


//
//    Intent intent = getIntent();
//    String macIP = intent.getStringExtra("macIP");

    String macIP = MainActivity.macIP;
    String id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_login_confirm_j.jsp?";  //아이디 중복체크

    ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////
  private ISessionCallback mSeesingCallback_j;
  LinearLayout layout_j;


    ///////////////////////// 아이디 및 비밀번호 찾기  ////////////////////////////

    TextView id_find_j;
    TextView pw_find_j;
    TextView login_join_j;
    ///////////////////////// 로그인 ////////////////////////////
    ArrayList<Signup_Bean> idpw_confirm_result;
    EditText ed_login_id_j, ed_login_pw_j ;
    Button btn_login_j;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        ///////////////////////// 카카오  ////////////////////////////


        layout_j = findViewById(R.id.kaka);


        com.kakao.usermgmt.LoginButton kakao_btn ;
        kakao_btn = findViewById(R.id.kakao_login_btn);
        kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login_s.this, "클릭했우", Toast.LENGTH_SHORT).show();

       

                mSeesingCallback_j = new ISessionCallback() {
            @Override
            public void onSessionOpened() {

                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback()
                {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인 실패시
                        Toast.makeText(Login_s.this, "로그인 시도 중 오류가 발생했습니다다", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                        //세션이 닫힘
                        Toast.makeText(Login_s.this, "세션이 닫혔습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //로그인 성공시 result로 정보를 가져옴
                    public void onSuccess(MeV2Response result ) {

                        //로그인 성공
                        Intent intent = new Intent(Login_s.this, ResultActivity.class);
                        intent.putExtra("Name", result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("profileImg",result.getKakaoAccount().getProfile().getProfileImageUrl());
                        intent.putExtra("email", result.getKakaoAccount().getEmail());

                        startActivity(intent);
                        Toast.makeText(Login_s.this, " 환영합니다", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(Login_s.this, "세션이 닫혔습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

            }
        };

            }
        });

        Session.getCurrentSession().addCallback(mSeesingCallback_j);
        Session.getCurrentSession().checkAndImplicitOpen();

        ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////




         ///////////////////////// 아이디 및 비밀번호 찾기 위한 리스너 시잗   ////////////////////////////

        id_find_j = findViewById(R.id.login_id_find);
        pw_find_j = findViewById(R.id.login_pw_find);

        id_find_j.setOnTouchListener(click);
        pw_find_j.setOnTouchListener(click);
        ///////////////////////// 아이디 및 비밀번호 찾기 위한  끝  ////////////////////////////



        ///////////////////////// 회원가입  텍스트뷰 불러오기  ////////////////////////////
        login_join_j=findViewById(R.id.login_join);
        login_join_j.setOnTouchListener(joinclick);



        ///////////////////////// 카카오 로그인을 위한 객채  ////////////////////////////


       ////////////////////////////////////// ////////////// 구글 로그인 연동 과정  시작    //////////////////////////////////////////////////////////

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient_j = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth_j = FirebaseAuth.getInstance();  //파이어베이스 인증 객체 초기화

        btn_google_j =findViewById(R.id.login_btn_google);
        btn_google_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient_j);
                startActivityForResult(intent, REO_SIGN_GOOGLE);
            }
        });



           /////////////////        login 부분     ///////////////
        btn_login_j = findViewById(R.id.login_btn_justlogin);
        btn_login_j.setOnClickListener(login_click);
        ed_login_id_j=findViewById(R.id.login_edit_id);
        ed_login_pw_j =findViewById(R.id.login_edit_pw);


















    }///////////       ///////        ;ONCREATE           ///////////        ONCREATE        ///////        ONCREATE




    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) { //구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REO_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {  //인증결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount();//account 라는 데이터는 구글 로그인 정보를 담고 있습니다(닉네임 , 프로필 ,사진 주소등등)
                resultLogin(account); //로그인 결과 값 출력 수행하라는 메소드

            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth_j.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        String google = "google";
                        if (task.isSuccessful()){  //로그인 성공하면
                            Toast.makeText(Login_s.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                              intent.putExtra("division",google);
                              intent.putExtra("Name" , account.getDisplayName());
                              intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl()));  //String.valueof 특정 자료형을 string 형태로 변환
                              startActivity(intent);
                        }else{   //로그인 실패하면
                            Toast.makeText(Login_s.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    ////////////////////////////////////// ////////////// 구글 로그인 연동 과정 마지막 부분      //////////////////////////////////////////////////////////

    
    

    ////////////////////////////////////// //////////////    아이디 및 비밀번호를 찾기 위한 리스너 시작  //////////////////////////////////////////////////////////

    View.OnTouchListener click = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
           switch (v.getId()){
               case R.id.login_id_find :
                   Toast.makeText(Login_s.this, "id를 찾으시겠습니까?", Toast.LENGTH_SHORT).show();

                   linearLayout= findViewById(R.id.login_linearlayout);

                   linearLayout.setVisibility(View.INVISIBLE);
                   FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                   Id_find_fragment_j id_find_fragment_j = new Id_find_fragment_j();
                   transaction.replace(R.id.ly_login_framelayout, id_find_fragment_j);
                   transaction.commit();



               break;
               case R.id.login_pw_find :

                   linearLayout= findViewById(R.id.login_linearlayout);
                   linearLayout.setVisibility(View.INVISIBLE);
                   FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                   Pw_find_frgment_j pw_find_fragment_j = new Pw_find_frgment_j();
                   transaction2.replace(R.id.ly_login_framelayout, pw_find_fragment_j);
                   transaction2.commit();

                   break;
           }
            return true;
        }
    };

    ////////////////////////////////////// //////////////    아이디 및 비밀번호를 찾기 위한 리스너 끝  //////////////////////////////////////////////////////////


    ////////////////////////////////////// //////////////    회원가입을 위한 클릭리스너 시작 //////////////////////////////////////////////////////////
     View.OnTouchListener joinclick = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            linearLayout= findViewById(R.id.login_linearlayout);
            linearLayout.setVisibility(View.INVISIBLE);

//            FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
//            Signup_Agree_fragment_j signup_agree_fragment_j = new Signup_Agree_fragment_j();
//
//          transaction3.replace(R.id.ly_login_framelayout,signup_agree_fragment_j );
//          transaction3.commit();

            Intent intent = new Intent(Login_s.this, SignUp_j.class);
            startActivity(intent);




            return true;
        }
    };

    ////////////////////////////////////// //////////////    회원가입을 위한 클릭리스너 끝  //////////////////////////////////////////////////////////





    ////////////////////////////////////// //////////////    로그인 버튼  //////////////////////////////////////////////////////////



     View.OnClickListener login_click = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           String login_id =  ed_login_id_j.getText().toString();
           String login_pw = ed_login_pw_j.getText().toString();


         int result =  connectGetData(login_id,login_pw); // 로그인 성공
             Log.v("message", "여기는= connectGetData의 결과 값이야 result = " + result);

             if (result == 1){
                 Toast.makeText(Login_s.this, "로그인 성공! id = " +client_id, Toast.LENGTH_SHORT).show();


             }else if (result==0){

                 Toast.makeText(Login_s.this, "로그인에 실패 했습니다 아이디와 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
             }


             id_confirm_urlAddr = "http://" + macIP + ":8080/honey/honey_login_confirm_j.jsp?";
         }


     };



        private int connectGetData(String client_write_id, String client_write_pw) {
            id_confirm_urlAddr = id_confirm_urlAddr + "cId=" + client_write_id + "&cPw=" + client_write_pw;

            String id = "";
            String pw = "";
            int result = 0;



            try {
                NetworkTask_j networkTask = new NetworkTask_j(Login_s.this, id_confirm_urlAddr, "login");
                Object obj = networkTask.execute().get();

                idpw_confirm_result = (ArrayList<Signup_Bean>) obj;

                id = idpw_confirm_result.get(0).getId();
                pw = idpw_confirm_result.get(0).getPw();

                Log.v("message", "여기는 로그인 값이 돌아오는 곳 id=" + id + "/  pw=" + pw);

                if (id.equals(client_write_id) && pw.equals(client_write_pw)) {
                    Intent intent= new Intent(Login_s.this,ResultActivity.class);   // 로그인 성공시 저기로 보낸다
                    intent.putExtra("cId", id);
                    client_id = id;
                    startActivity(intent);
                    result = 1;

                } else {
                    Log.v("message", "여기는 로그인 값이 돌아오는 곳 id=" + id + "pw=" + pw);
                    result = 0;
                }
                Log.v("message", "여기는 아이디 중복체크하는  urlAddr 확인하는거=" + id);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;

            //////////////////////    로그인 끝  ////////////////////////


        }

} //MAIN