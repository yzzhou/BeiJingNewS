package myapplication.androidandjs;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JsCallJavaCallPhoneActivity extends Activity {
    private WebView webView;
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);

        webView = (WebView)findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient());


        webView.addJavascriptInterface(new Java(),"Android");
        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");

    }

     class Java {
         @JavascriptInterface
         public void showcontacts(){
             String json = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"}]";
             webView.loadUrl("javascript:show('" + json + "')");
         }
         @JavascriptInterface
         public void call(String phone){
             Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phone));
             if (ActivityCompat.checkSelfPermission(JsCallJavaCallPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                 return;
             }
             startActivity(intent);
         }
    }
}
