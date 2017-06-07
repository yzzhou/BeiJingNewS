package myapplication.androidandjs;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JavaAndJSActivity extends Activity {
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    /**
     * 加载网页或者说H5页面
     */
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js);

        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        initWebView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    private void login(){
        String number = etNumber.getText().toString().trim();
        String passWord = etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(number)||TextUtils.isEmpty(passWord)){
            Toast.makeText(JavaAndJSActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            login(number);
        }
    }
    private void login(String number) {
        webView.loadUrl("javascript:javaCallJs(" + "'" + number + "'" + ")");
        setContentView(webView);
    }

    private void initWebView() {
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new Java(),"android");
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");

    }

    private class Java {
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJSActivity.this, "调用了", Toast.LENGTH_SHORT).show();
        }
    }
}
