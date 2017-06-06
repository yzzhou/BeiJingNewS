package myapplication.beijingnews.activity.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapplication.beijingnews.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ib_menu)
    ImageButton ibMenu;
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.ib_textsize)
    ImageButton ibTextsize;
    @Bind(R.id.ib_share)
    ImageButton ibShare;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.activity_news_detail)
    LinearLayout activityNewsDetail;
    private int tempSize = 2;
    private int realSize = tempSize;
    private WebSettings settings;
    private Uri url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        setView();


        url =getIntent().getData();
        settings= webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
       webview.setWebViewClient(new WebViewClient(){
           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               progressbar.setVisibility(View.GONE);
           }
       });
        webview.loadUrl(url.toString());

    }

    private void setView() {
        tvTitle.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.ib_back, R.id.ib_textsize, R.id.ib_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                //Toast.makeText(NewsDetailActivity.this, "设置文字大小", Toast.LENGTH_SHORT).show();
               showChangeTextSizeDialog();
                break;
            case R.id.ib_share:
                Toast.makeText(NewsDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showChangeTextSizeDialog() {
        String [] items = {"超大字体","大字体","正常字体","小字体","超小字体",} ;
        new AlertDialog.Builder(this)
                    .setTitle("设置字体大小")
                    .setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tempSize = which;
                        }
                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                    realSize = tempSize;
                            changeTextSize(realSize);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0:
                settings.setTextZoom(200);
                break;
            case 1:
                settings.setTextZoom(150);
                break;
            case 2:
                settings.setTextZoom(100);
                break;
            case 3:
                settings.setTextZoom(75);
                break;
            case 4:
                settings.setTextZoom(50);
                break;
        }
    }
}
