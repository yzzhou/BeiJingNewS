package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import myapplication.beijingnews.activity.base.MenuDetailBasePager;

/**
 * Created by zhouzhou on 2017/6/3.
 */

public class TopicMenuDetailPager extends MenuDetailBasePager {
    private TextView textView;

    public TopicMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("专题详情页面的内容");
    }
}