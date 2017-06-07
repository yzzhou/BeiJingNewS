package myapplication.androidandjs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJavaAndJs;
    private Button btnJsCallJava;
    private Button btnJsCallPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inirView();
    }

    private void inirView() {
        btnJavaAndJs = (Button)findViewById(R.id.btn_java_and_js);
        btnJsCallJava = (Button)findViewById(R.id.btn_js_call_java);
        btnJsCallPhone = (Button)findViewById(R.id.btn_js_call_phone);
        btnJavaAndJs.setOnClickListener(this);
        btnJsCallJava.setOnClickListener(this);
        btnJsCallPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnJavaAndJs){
            Intent intent = new Intent(MainActivity.this,JavaAndJSActivity.class);
            startActivity(intent);

        }else if(v==btnJsCallJava){
            Intent intent = new Intent(MainActivity.this,JsCallJavaVideoActivity.class);
            startActivity(intent);
        }else if(v==btnJsCallPhone){
            Intent intent = new Intent(MainActivity.this,JsCallJavaCallPhoneActivity.class);
            startActivity(intent);
        }
    }
}
