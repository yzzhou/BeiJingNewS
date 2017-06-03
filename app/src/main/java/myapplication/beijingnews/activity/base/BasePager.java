package myapplication.beijingnews.activity.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.MainActivity;

/**
 * Created by zhouzhou on 2017/6/2.
 */

public class BasePager {
    public  Context context;
    public View rootView;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_content;
    public  BasePager(final Context context){
        this.context = context;
        rootView = View.inflate(context, R.layout.base_pager,null);
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) rootView.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity mainActivity = (MainActivity) context;
                ((MainActivity) context).getSlidingMenu().toggle();
            }
        });

    }
    //绑定数据
    public void initData(){

    }
}
