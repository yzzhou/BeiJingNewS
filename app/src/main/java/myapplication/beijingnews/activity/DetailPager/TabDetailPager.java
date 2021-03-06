package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.NewsDetailActivity;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.domain.TabDetailPagerBean;
import myapplication.beijingnews.activity.pager.HorizontalScrollViewPager;
import myapplication.beijingnewslibrary.uitls.CacheUitls;
import myapplication.beijingnewslibrary.uitls.ConstantUtils;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/5.
 */

public class TabDetailPager extends MenuDetailBasePager {
    public static final String READ_ID_ARRAY = "read_id_array";
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    //@Bind(R.id.viewpager)
    ViewPager viewpager;
    //@Bind(R.id.tv_title)
    TextView tvTitle;
    //@Bind(R.id.ll_point_group)
    LinearLayout llPointGroup;
    HorizontalScrollViewPager viewPager;
    //@Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.pull_refresh_list)
    PullToRefreshListView pull_refresh_list;


    private String url;
    private List<TabDetailPagerBean.DataBean.TopnewsBean> topnews;
    private List<TabDetailPagerBean.DataBean.NewsBean> newsBeanList;
    private MyListAdapter adapter;
    private int prePosition=0;
    private String moreUrl;
    private  boolean isloadingMore = false;



    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;

    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_tab_detail, null);
        ButterKnife.bind(this,view);

        //lv = (ListView) viewTopNews.findViewById(R.id.lv);
        lv = pull_refresh_list.getRefreshableView();

        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(context);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        pull_refresh_list.setOnPullEventListener(soundListener);

        View viewTopNews = View.inflate(context,R.layout.tab_detail_topenews,null);
        viewpager = (HorizontalScrollViewPager) viewTopNews.findViewById(R.id.viewpager);
        tvTitle = (TextView) viewTopNews.findViewById(R.id.tv_title);
        llPointGroup = (LinearLayout) viewTopNews.findViewById(R.id.ll_point_group);

        lv.addHeaderView(viewTopNews);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                llPointGroup.getChildAt(prePosition).setEnabled(false);
                llPointGroup.getChildAt(position).setEnabled(true);
                prePosition=position;
            }

            @Override
            public void onPageSelected(int position) {
                String title = topnews.get(position).getTitle();
                tvTitle.setText(title);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                    if(state ==ViewPager.SCROLL_STATE_DRAGGING){
                        handler.removeCallbacksAndMessages(null);
                    }else if(state==ViewPager.SCROLL_STATE_IDLE){
                        handler.removeCallbacksAndMessages(null);
                        handler.postDelayed(new MyRunnable(),3000);
                    }
            }
        });
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isloadingMore = false;
                getDataFromNet(url);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!TextUtils.isEmpty(moreUrl)) {

                    isloadingMore = true;
                    getDataFromNet(moreUrl);
                }else{
                    Toast.makeText(context, "没有更多数据了...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int realPosition = position- 2;
                TabDetailPagerBean.DataBean.NewsBean newsBean = newsBeanList.get(realPosition);
                String idArray = CacheUitls.getString(context, READ_ID_ARRAY);
                if(!idArray.contains(newsBean.getId()+"")){
                    idArray = idArray + newsBean.getId()+",";
                    //保存
                    //CacheUtils.putString(context,READ_ID_ARRAY,idArray);
                    CacheUitls.putString(context,READ_ID_ARRAY,idArray);
                    //适配器刷新
                    adapter.notifyDataSetChanged();
                }
                String url = ConstantUtils.BASE_URL + newsBean.getUrl();
                //跳转到Activity显示新闻详情内容
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = ConstantUtils.BASE_URL + childrenBean.getUrl();
        Log.e("TAG","url=="+url);
        //tvTitle.setText(childrenBean.getTitle());
        getDataFromNet(url);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "请求失败==" + e.getMessage());

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                        pull_refresh_list.onRefreshComplete();
                    }

        });
    }
    private InternalHandler handler;
    class InternalHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int items = (viewpager.getCurrentItem()+1)%topnews.size();
            viewpager.setCurrentItem(items);
            handler.postDelayed(new MyRunnable(),3000 );
    }
    }
    class MyRunnable implements  Runnable{

        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }
    private void processData(String response) {
        TabDetailPagerBean bean = new Gson().fromJson(response,TabDetailPagerBean.class);
//        topnews = bean.getData().getTopnews();
//        viewpager.setAdapter(new MyPagerAdapter());
//        tvTitle.setText(topnews.get(prePosition).getTitle());
//        llPointGroup.removeAllViews();
//        for(int i = 0;i<topnews.size();i++){
//            ImageView point = new ImageView(context);
//            point.setBackgroundResource(R.drawable.point_selector);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8,8);
//
//            point.setLayoutParams(params);
//            if(i==0){
//                point.setEnabled(true);
//            }else{
//                point.setEnabled(false);
//                params.leftMargin = 8;
        String more = bean.getData().getMore();
        if(!TextUtils.isEmpty(more)){
            moreUrl = ConstantUtils.BASE_URL+more;
        }

        if(!isloadingMore){
            //---------顶部--------------
            topnews = bean.getData().getTopnews();
            viewpager.setAdapter(new MyPagerAdapter());

            Log.e("TAG", "" + bean.getData().getNews().get(0).getTitle());
            tvTitle.setText(topnews.get(prePosition).getTitle());
            llPointGroup.removeAllViews();
            //添加指示点
            for (int i = 0; i < topnews.size(); i++) {
                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
                point.setLayoutParams(params);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                    params.leftMargin = 8;
                }
                //添加到线性布局
                llPointGroup.addView(point);
            }
            //------------ListView的---------------
            newsBeanList = bean.getData().getNews();
            adapter = new MyListAdapter();
            lv.setAdapter(adapter);
        }else{
            isloadingMore = false;
            newsBeanList.addAll(bean.getData().getNews());//把新的数据集合加入到原来集合中，而不是覆盖
            adapter.notifyDataSetChanged();//适配器刷新

        }
//        newsBeen= bean.getData().getNews();
//        adapter = new MyListAdapter();
//        lv.setAdapter(adapter);
        if(handler==null){
            handler = new InternalHandler();
        }
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new MyRunnable(),3000);
    }
     class MyListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return newsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView ==null){
                convertView = View.inflate(context,R.layout.item_tab_detail,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            TabDetailPagerBean.DataBean.NewsBean newsBean = newsBeanList.get(position);
            viewHolder.tvDesc.setText(newsBean.getTitle());
            viewHolder.tvTime.setText(newsBean.getPubdate());
            String imageUrl = ConstantUtils.BASE_URL+newsBean.getListimage();
            Glide.with(context)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.pic_item_list_default)
                                        .error(R.drawable.pic_item_list_default)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(viewHolder.ivIcon);
            String idArray  = CacheUitls.getString(context,READ_ID_ARRAY);
            if(idArray.contains(newsBean.getId()+"")){
                //灰色
                viewHolder.tvDesc.setTextColor(Color.GRAY);
            }else{
                //黑色
                viewHolder.tvDesc.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }
    static class ViewHolder {
                    @Bind(R.id.iv_icon)
                    ImageView ivIcon;
                    @Bind(R.id.tv_desc)
                    TextView tvDesc;
                    @Bind(R.id.tv_time)
                    TextView tvTime;

                            ViewHolder(View view) {
                                ButterKnife.bind(this, view);
                            }
    }
    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.pic_item_list_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = ConstantUtils.BASE_URL +topnews.get(position).getTopimage();
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.pic_item_list_default)
                    .error(R.drawable.pic_item_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            container.addView(imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);

                            break;
                        case MotionEvent.ACTION_UP:
                            handler.postDelayed(new MyRunnable(),3000);
                            break;
                    }
                    return true;
                }
            });
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }


}
