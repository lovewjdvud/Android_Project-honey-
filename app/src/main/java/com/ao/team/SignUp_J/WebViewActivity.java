package com.ao.team.SignUp_J;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ao.team.MainActivity;
import com.ao.team.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView browser;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        //    C:\Program Files\Apache Software Foundation\Tomcat 8.5_Tomcat\webapps\ROOT\honey
        //  file:///C:/Users/LG/Downloads/daum.html
        String ip = MainActivity.macIP;
        browser.loadUrl("http://192.168.35.128:8080/honey/assets/daum.html");

        // http://192.168.2.12:8080/honey/assets/daum.html
        // String macIP ="1092.168.9.76";
        //String id_confirm_urlAddr = "http://" + ip + ":8080/honey/assets/daum.html";  //아이디 중복체크

    }
}
