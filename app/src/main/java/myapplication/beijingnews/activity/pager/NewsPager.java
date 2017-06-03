package myapplication.beijingnews.activity.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import myapplication.beijingnews.activity.DetailPager.InteractMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.NewsMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.PhotosMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.TopicMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.VoteMenuDetailPager;
import myapplication.beijingnews.activity.activity.MainActivity;
import myapplication.beijingnews.activity.base.BasePager;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.fragment.LeftMenuFragment;
import myapplication.beijingnews.activity.uitls.ConstantUtils;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/2.
 */

public class NewsPager extends BasePager{
    private List<NewsCenterBean.DataBean> datas;
    private  List<MenuDetailBasePager>  basePagers;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");
        ib_menu.setVisibility(View.VISIBLE);

        TextView textView = new TextView(context);
        textView.setText("新闻页面的内容");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = ConstantUtils.NEWSCENTER_PAGER_URL;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","请求数据失败=="+ e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","请求数据成功=="+ response);
                        processData(response);
                    }


                });
    }

    private void processData(String json) {
        NewsCenterBean newsCenterBean = new Gson().fromJson(json,NewsCenterBean.class);
        Log.e("TAG","解析成功了=="+newsCenterBean.getData().get(0).getChildren().get(0).getTitle());
        datas=newsCenterBean.getData();
        MainActivity mainActivity = (MainActivity) context;

        basePagers = new ArrayList<>();
        basePagers.add(new NewsMenuDetailPager(context));
        basePagers.add(new TopicMenuDetailPager(context));
        basePagers.add(new PhotosMenuDetailPager(context));
        basePagers.add(new InteractMenuDetailPager(context));
        basePagers.add(new VoteMenuDetailPager(context));

        LeftMenuFragment leftMenuFragment = mainActivity.getleftMenuFragment();
        leftMenuFragment.setData(datas);

    }

    public void switchPager(int prePosition) {
        MenuDetailBasePager basePager= basePagers.get(prePosition);
        View rootView = basePager.rootView;
        fl_content.removeAllViews();
        fl_content.addView(rootView);
        basePager.initData();
    }
}
