package myapplication.beijingnews.activity.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.base.BaseFragment;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initVeiw() {
//        TextView textView = new TextView(activity);
//        textView.setText("我是正文的布局");
//        textView.setTextSize(20);
//        textView.setTextColor(Color.BLACK);
        View view = View.inflate(activity, R.layout.content_fragment,null);
        return view;
    }

}
