package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;

/**
 * Created by zhouzhou on 2017/6/3.
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {
    private NewsMenuDetailPagerAdapter adapter;
    private TextView textView;
    public final  List<NewsCenterBean.DataBean.ChildrenBean> datas;
    private ViewPager viewPager_news;
    private List<TabDetailPager> tabDetailPagers;
    public NewsMenuDetailPager(Context context, List<NewsCenterBean.DataBean.ChildrenBean> children) {
        super(context);
        this.datas = children;
    }

    @Override
    public View initView() {
//        textView = new TextView(context);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);
        View view = View.inflate(context, R.layout.pager_news_menu_datail,null);
        viewPager_news = (ViewPager) view.findViewById(R.id.viewpager_news);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //textView.setText("新闻详情页面的内容");
        tabDetailPagers  =new ArrayList<>();
        for(int i = 0; i < datas.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(context,datas.get(i)));
        }

        viewPager_news.setAdapter( new NewsMenuDetailPagerAdapter());
    }

    class NewsMenuDetailPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            container.addView(rootView);
            tabDetailPager.initData();
            return rootView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return tabDetailPagers ==null ? 0 : tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
