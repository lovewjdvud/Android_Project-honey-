package com.ao.team.Login_j;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ao.team.MainActivity;
import com.ao.team.NetworkTask_J.NetworkTask_j;
import com.ao.team.R;
import com.ao.team.Signup_bean_j.Signup_Bean;

import java.util.ArrayList;

public class Pw_find_frgment_j extends Fragment {
    LinearLayout linearLayout_login,li_pw_find_j;
    View view, loginView;
    TextView tv_id_find_j;
    Button btn_find_pw;
    EditText ed_find_pw_name, ed_find_pw_phone, ed_find_pw_id;

    ArrayList<Signup_Bean> find_id_result;

    // 아이디 찾기 위한 주소
    String macIP = MainActivity.macIP;
    String id_find_urlAddr = "http://" + macIP + ":8080/honey/honey_pw_find_j.jsp?";  //아이디 중복체크

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {

        //Login 엑티비티 소환중
        linearLayout_login = (LinearLayout)getActivity().findViewById(R.id.login_linearlayout);


        // 소환중
        view = inflater.inflate(R.layout.pw_find_fragment,container,false);
        li_pw_find_j = view.findViewById(R.id.li_pw_find_j);

        ed_find_pw_name = view.findViewById(R.id.ed_pw_find_name_j);
        ed_find_pw_phone= view.findViewById(R.id.ed_pw_find_phone_j);
        ed_find_pw_id  = view.findViewById(R.id.ed_pw_find_id_j);

        btn_find_pw = view.findViewById(R.id.btn_find_pw);


        btn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String find_name = ed_find_pw_name.getText().toString();
                String find_phone = ed_find_pw_phone.getText().toString();
                String find_id = ed_find_pw_id.getText().toString();


                if (find_name.length() ==0) {
                    Toast.makeText(getActivity(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (find_id.length() ==0) {
                    Toast.makeText(getActivity(), "아이디을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else if (find_phone.length() ==0) {
                    Toast.makeText(getActivity(), "전화번호을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else if (ed_find_pw_name.length() != 0 && find_phone.length() != 0 && find_id.length() != 0) {


                    Log.v("message", "여기는 비밀번호 찾기 검사로들어가기 전값 이름=" + find_name + "/  전화번호=" + find_phone + "/  아이디=" + find_id);


                    result = connectGetData(find_name, find_phone, find_id);

                    Log.v("message", "여기는 비밀번호 찾기 검사후의 결과 값이야=" + result);


                }
            }
        }); //setonclick

        //////////////////////    로그인 끝  ////////////////////////

        return view;
    }
    private String connectGetData(String find_name, String find_phone ,String find_id ) {


        id_find_urlAddr = id_find_urlAddr + "cId=" + find_id;

        String result_name = "";
        String result_phone = "";
        String result_id = "";
        String Result_pw = "";
        String result = "";
        String test = "sads";

        Log.v("message", "여기는 비밀번호 찾기  urlAddr 확인하는거=" + id_find_urlAddr);

        try {
            NetworkTask_j networkTask = new NetworkTask_j(getActivity(), id_find_urlAddr, "pw_find");
            Object obj = networkTask.execute().get();

            find_id_result = (ArrayList<Signup_Bean>) obj;

            result_name = find_id_result.get(0).getName();
            result_phone = find_id_result.get(0).getPhone();
            result_id = find_id_result.get(0).getId();
            Result_pw = find_id_result.get(0).getPw();

            Log.v("message", "여기는 비밀번호 찾기 유저가 입력한 곳  name=" + find_name + "/  phone=" + find_phone+ "/  id=" + find_id);
            Log.v("message", "여기는 비밀번호 찾기 값이 돌아오는 곳 name=" + result_name + "/  phone=" + result_phone+ "/  id=" + result_id+ "/  pw=" + Result_pw);



            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

            // 버튼 클릭시 다이얼로그 띄우기기

            if (find_name.equals(result_name) && find_phone.equals(result_phone) && find_id.equals(result_id)  &&Result_pw.length() !=0){
                result= "고객님의 비밀번호는 = '"+Result_pw+"' 입니다";

                /////// 입력 정보가 맞으면 다이얼로그 띄우기
                dlg.setTitle("꿀 재료"); //제목
                dlg.setMessage( result); // 메시지
                dlg.setIcon(R.drawable.honey_j); // 아이콘 설정



//                버튼 클릭시 동작
                dlg.setPositiveButton("로그인 화면으로 이동합니다.",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //로그인 화면으로 이동
                        li_pw_find_j.setVisibility(View.INVISIBLE);
                        linearLayout_login.setVisibility(View.VISIBLE);

                    }
                });

                dlg.show();


            }else {
                result ="입력하신 정보를 다시 확인해주세요!";

                /////// 입력 정보가 틀리면 다이얼로그 띄우기
                dlg.setTitle("꿀 재료"); //제목
                dlg.setMessage( result); // 메시지
                dlg.setIcon(R.drawable.honey_j); // 아이콘 설정

//                버튼 클릭시 동작
                dlg.setPositiveButton("닫기",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(getActivity(),"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();

            }


        } catch (Exception e) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
            result ="입력하신 정보를 다시 확인해주세요!";

            /////// 입력 정보가 틀리면 다이얼로그 띄우기
            dlg.setTitle("꿀 재료"); //제목
            dlg.setMessage( result); // 메시지
            dlg.setIcon(R.drawable.honey_j); // 아이콘 설정
            dlg.setPositiveButton("닫기",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지

                }
            });
            dlg.show();

                    e.printStackTrace();
        }
        id_find_urlAddr = "http://" + macIP + ":8080/honey/honey_pw_find_j.jsp?";  //아이디 중복체크
        return result;

    }

}
