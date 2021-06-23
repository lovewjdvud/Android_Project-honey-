package com.ao.team.SignUp_J;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ao.team.Login_j.Id_find_fragment_j;
import com.ao.team.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class Signup_Agree_fragment_j extends BottomSheetDialogFragment {

    LinearLayout signup_linearLayout;
    View view;
    Button btn_signup_agree_j;
    LinearLayout signup_agree_j;
    SignUp_j signUp_j;
    CheckBox check_all_j, check_1_j, check_2_j,check_3_j;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {


//        signup_linearLayout   = (LinearLayout)getActivity().findViewById(R.id.signup_Linearlayout);
//
        // 레이아웃 부르는 과정





        view = inflater.inflate(R.layout.signup_agreee_fragment_j,container,false);

       //체크 박스 불러오기
        check_all_j =view.findViewById(R.id.check_signup_agree_all_j);
        check_1_j =view.findViewById(R.id.check_signup_agree_1_j);
        check_2_j =view.findViewById(R.id.check_signup_agree_2_j);
        check_3_j =view.findViewById(R.id.check_signup_agree_3_j);



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        check_all_j.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

              if (isChecked==true) {
                  check_1_j.setChecked(true);
                  check_2_j.setChecked(true);
                  check_3_j.setChecked(true);

                  int sign_agree_data = 1;

                  ((OnApplySelectedListener)activity).onCatagoryApplySelected(sign_agree_data);
                  Toast.makeText( getActivity(), "약관에 동의 하셨습니다", Toast.LENGTH_SHORT).show();
              } else if(isChecked==false){
//
//                  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                  Id_find_fragment_j id_find_fragment_j = new Id_find_fragment_j();
//                  transaction.replace(R.id.ly_login_framelayout, id_find_fragment_j);
//                  transaction.commit();
//                  


                  check_1_j.setChecked(false);
                  check_2_j.setChecked(false);
                  check_3_j.setChecked(false);

                  int sign_agree_data = 0;
                  ((OnApplySelectedListener)activity).onCatagoryApplySelected(sign_agree_data);

                  Toast.makeText( getActivity(), "약관에 전체 동의하셔야 진행이 가능합니다", Toast.LENGTH_SHORT).show();
                }else {
                  int sign_agree_data = 0;
                  ((OnApplySelectedListener)activity).onCatagoryApplySelected(sign_agree_data);


              }
            }
        });




        // 약관 동의 버튼
        btn_signup_agree_j =view.findViewById(R.id.btn_signup_agree_j);
        btn_signup_agree_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (check_1_j.isChecked()==false||check_2_j.isChecked()==false||check_3_j.isChecked()==false){
                    int sign_agree_data = 0;
                }
                dismiss();

                        }
        });

    }
   // 값을 Signup_j엑티비티로 데이터를 보내는 과정
    public interface OnApplySelectedListener {
        public void onCatagoryApplySelected(int test);

    }

    private Activity activity;



    @Override
   // 엑티비티 위치 안내
    public void onAttach(Context context) {

        super.onAttach(context);

        if(context instanceof Activity) {

            // 사용될 activity 에 context 정보 가져오는 부분

            this.activity = (Activity)context;

        }

    }










}

