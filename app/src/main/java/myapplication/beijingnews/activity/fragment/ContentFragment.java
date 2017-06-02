package myapplication.beijingnews.activity.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.base.BaseFragment;
import myapplication.beijingnews.activity.base.BasePager;
import myapplication.beijingnews.activity.pager.HomePager;
import myapplication.beijingnews.activity.pager.NewsPager;
import myapplication.beijingnews.activity.pager.SettingPager;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class ContentFragment extends BaseFragment {

    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BasePager> pagers;

    @Override
    public View initVeiw() {
//        TextView textView = new TextView(activity);
//        textView.setText("我是正文的布局");
//        textView.setTextSize(20);
//        textView.setTextColor(Color.BLACK);
        View view = View.inflate(context, R.layout.content_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new NewsPager(context));
        pagers.add(new SettingPager(context));
        vp.setAdapter(new MyAdapter());
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        vp.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        vp.setCurrentItem(1,false);
                        break;
                    case R.id.rb_setting:
                        vp.setCurrentItem(2,false);
                        break;
                }
            }
        });
        rgMain.check(R.id.rb_home);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);
            View rootView = basePager.rootView;
            basePager.initData();
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
