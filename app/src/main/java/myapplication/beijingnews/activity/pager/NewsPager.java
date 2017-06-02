package myapplication.beijingnews.activity.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import myapplication.beijingnews.activity.base.BasePager;

/**
 * Created by zhouzhou on 2017/6/2.
 */

public class NewsPager extends BasePager{
    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");

        TextView textView = new TextView(context);
        textView.setText("新闻页面的内容");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
    }

}
