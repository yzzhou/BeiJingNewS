package myapplication.beijingnews.activity.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import myapplication.beijingnews.activity.base.BaseFragment;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class LeftMenuFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initVeiw() {
        textView = new TextView(activity);
        textView.setText("我是左侧菜单的布局");
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
