package com.ao.team.NetworkTask_J;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ao.team.SignUp_J.SignUp_j;
import com.ao.team.Signup_bean_j.Signup_Bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_j extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Signup_Bean> client;

    // Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;


    public NetworkTask_j(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.client = client;
        this.client = new ArrayList<Signup_Bean>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Get.....");
        progressDialog.show();
        Log.v("progressDialog", "여기는 progressDialog");
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;


        try{
            URL url = new URL(mAddr);
            Log.v("mAddr", "mAddr="+mAddr);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strLine = bufferedReader.readLine();
                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }
                if (where.equals("select")) {
                    parserSelect(stringBuffer.toString());
                    //리턴값없고
                } else if (where.equals("login")){
                    parserlogin(stringBuffer.toString());
                } else if (where.equals("id_find")){
                    parserid_find(stringBuffer.toString());
                } else if (where.equals("pw_find")){
                    parserpw_find(stringBuffer.toString());
                } else {
                    result = parserAction(stringBuffer.toString());
                    //리턴값있어
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (where.equals("select")) {
            return client;
        }else if(where.equals("login")) {
            return client;
        }else if(where.equals("id_find")) {
            return client;
        }else if(where.equals("pw_find")) {
            return client;
        }else {
            return result;
        }
    }
    private String parserAction(String str){
        String returnValue = null;
        try {
            Log.v("str", "안녕여기는 회원가입  JSON에서 불러오는 과정이야="+str);


            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }



    private void parserSelect(String str){
        try{

            Log.v("parserSelect", "안녕여기는 아이디 중복체크  JSON에서 불러오는 과정이야="+mAddr);
            Log.v("str", "안녕여기는 아이디 중복체크  JSON에서 불러오는 과정이야="+str);

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("client_info"));
            client.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);


                String cId = jsonObject1.getString("cId");

                Log.v("parserSelect", "안녕여기는 아이디 중복체크  JSON에서 불러오는 과정이야 cid =  " + cId);

                Signup_Bean member = new Signup_Bean(cId);
                client.add(member);
            }
            Log.v("client", "안녕여기는 아이디 중복체크  JSON에서 불러오는 과정이야 client =  "+client);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void parserlogin(String str){
        try{

            Log.v("parserSelect", "안녕여기는 로그인  JSON에서 불러오는 과정이야="+mAddr);
            Log.v("str", "안녕여기는 로그인  JSON에서 읽어온값이야="+str);

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("login_info"));
            client.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);


                String cId = jsonObject1.getString("cId");
                String cPw = jsonObject1.getString("cPw");

                Log.v("parserSelect", "안녕여기는 로그인  JSON에서 불러오는 과정이야 cid =  " + cId +"  비밀번호는 =" + cPw);

                Signup_Bean members = new Signup_Bean(cId, cPw);
                client.add(members);
            }
            Log.v("client", "안녕여기는 로그인  JSON에서 불러오는 과정이야 client =  "+client);


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void parserid_find(String str){
        try{

            Log.v("parserSelect", "안녕여기는 아이디 찾기  JSON에서 불러오는 과정이야="+mAddr);
            Log.v("str", "안녕여기는 아이디  JSON에서 불러오는 과정이야="+str);

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("id_find_info"));
            client.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String cId = jsonObject1.getString("cId");
                String cName = jsonObject1.getString("cName");
                String cTelno = jsonObject1.getString("cTelno");


                Log.v("parserSelect", "안녕여기는 아이디 찾기  JSON에서 불러오는 과정이야 cid =  " + cId);

                Signup_Bean member = new Signup_Bean(cId,cName,cTelno);
                client.add(member);
            }
            Log.v("client", "안녕여기는 아이디 아이디 찾기  JSON에서 불러오는 과정이야 client =  "+client);


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void parserpw_find(String str){
        try{

            Log.v("parserSelect", "안녕여기는 비밀번호 찾기  JSON에서 불러오는 과정이야="+mAddr);
            Log.v("str", "안녕여기는 비밀번호  JSON에서 불러오는 과정이야="+str);

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("pw_find_info"));
            client.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);



                String cId = jsonObject1.getString("cId");
                String cPw = jsonObject1.getString("cPw");
                String cName = jsonObject1.getString("cName");
                String cTelno = jsonObject1.getString("cTelno");


                Log.v("parserSelect", "안녕여기는 비밀번호 찾기  JSON에서 불러오는 과정이야 cid =  " + cPw);

                Signup_Bean member = new Signup_Bean(cId,cPw,cName,cTelno);
                client.add(member);
            }
            Log.v("client", "안녕여기는 비밀번호  찾기  JSON에서 불러오는 과정이야 client =  "+client);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

} // class 끝 부분



