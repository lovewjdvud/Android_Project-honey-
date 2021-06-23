package com.ao.team.Login_j;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;

import com.ao.team.NetworkTask_J.NetworkTask_j;
import com.ao.team.R;
import com.ao.team.Signup_bean_j.Signup_Bean;

import java.util.ArrayList;

public class Id_find_fragment_j extends Fragment {
    LinearLayout linearLayout_login,li_id_find_j;
    View view, loginView;
    TextView tv_id_find_j;
    Button btn_find_id;
    EditText ed_find_id_name, ed_find_id_phone;

    ArrayList<Signup_Bean> find_id_result;


    // 아이디 찾기 위한 주소
    String macIP = "MainActivity.macIP";
    String id_find_urlAddr = "http://" + macIP + ":8080/honey/honey_id_find_j.jsp?";  //아이디 중복체크


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {


        //             엑티비티의 위젯들 가져오는 방법                         //
        linearLayout_login = (LinearLayout)getActivity().findViewById(R.id.login_linearlayout);

//             프레그먼트 소환하는 방법                         //
       view = inflater.inflate(R.layout.id_find_fragment,container,false);
//        tv_id_find_j = view.findViewById(R.id.tv_id_find_j);
        btn_find_id = view.findViewById(R.id.btn_find_id);
        ed_find_id_name =view.findViewById(R.id.ed_id_find_name_j);          // 이름
        ed_find_id_phone=view.findViewById(R.id.ed_id_find_phone_j);      // 전화 번호
        li_id_find_j = view.findViewById(R.id.li_id_find_j);




        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String result="";
               String find_name =  ed_find_id_name.getText().toString();
               String find_phone = ed_find_id_phone.getText().toString();

                if (find_name.length() ==0) {
                    Toast.makeText(getActivity(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else if (find_phone.length() ==0) {
                    Toast.makeText(getActivity(), "전화번호을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else if (find_name.length() != 0 && find_phone.length() != 0) {


                    Log.v("message", "여기는 아이디 찾기 검사로들어가기 전값 이름=" + find_name + "/  전화번호=" + find_phone);


                    result = connectGetData(find_name, find_phone);

                    Log.v("message", "여기는 아이디 찾기 검사후의 결과 값이야=" + result);
                }

           }
        }); //setonclick

            //////////////////////    로그인 끝  ////////////////////////

        return view;
        }
  private String connectGetData(String find_name, String find_phone ) {


        id_find_urlAddr = id_find_urlAddr + "cTelno=" + find_phone;

        String result_name = "";
        String result_phone = "";
        String Result_id = "ss";
        String result = "";
        String test = "sads";

        Log.v("message", "여기는 아이디 찾기  urlAddr 확인하는거=" + id_find_urlAddr);

        try {
            NetworkTask_j networkTask = new NetworkTask_j(getActivity(), id_find_urlAddr, "id_find");
            Object obj = networkTask.execute().get();

            find_id_result = (ArrayList<Signup_Bean>) obj;

            result_name = find_id_result.get(0).getName();
            result_phone = find_id_result.get(0).getPhone();
            Result_id = find_id_result.get(0).getId();
            Log.v("message", "여기는 아이디 찾기 유저가 입력한 곳  name=" + find_name + "/  phone=" + find_phone+ "/  id=" + Result_id);
            Log.v("message", "여기는 아이디 찾기 값이 돌아오는 곳 name=" + result_name + "/  phone=" + result_phone+ "/  id=" + Result_id);



            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

            // 버튼 클릭시 다이얼로그 띄우기기

            if (find_name.equals(result_name) && find_phone.equals(result_phone) && Result_id !=""){
                result= "고객님의 아이디는 = '"+Result_id+"' 입니다";

                /////// 입력 정보가 맞으면 다이얼로그 띄우기
                dlg.setTitle("꿀 재료"); //제목
                dlg.setMessage( result); // 메시지
                dlg.setIcon(R.drawable.honey_j); // 아이콘 설정



//                버튼 클릭시 동작
                dlg.setPositiveButton("로그인 화면으로 이동합니다.",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //로그인 화면으로 이동
                        li_id_find_j.setVisibility(View.INVISIBLE);
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
        id_find_urlAddr = "http://" + macIP + ":8080/honey/honey_id_find_j.jsp?";  //아이디 중복체크
        return result;

    }

}
