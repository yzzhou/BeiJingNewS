package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.MainActivity;
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
    private TabPageIndicator indicator;
    private ImageButton ib_next;
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
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        ib_next = (ImageButton) view.findViewById(R.id.ib_next);
        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager_news.setCurrentItem(viewPager_news.getCurrentItem()+1);
            }
        });
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //SlidingMenu可以滑动
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else{
                    //不可以滑动
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        indicator.setViewPager(viewPager_news);
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

        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position).getTitle();
        }
    }
}
