package myapplication.beijingnews.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.uitls.CacheUitls;
import myapplication.beijingnews.activity.uitls.DensityUtil;

public class GuideActivity extends AppCompatActivity {


    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Bind(R.id.ll_group_point)
    LinearLayout llGroupPoint;
    @Bind(R.id.activity_guide)
    RelativeLayout activityGuide;
    @Bind(R.id.iv_red_point)
    ImageView ivRedPoint;
    @Bind(R.id.btn_start_main)
    Button btnStartMain;
    private ArrayList<ImageView> imageViews;
    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private int leftMargin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        viewpager.setAdapter(new MyPagerAdapter());
        viewpager.addOnPageChangeListener(new MyOnPagerChanfeListener());

        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }

    private void initData() {

        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            imageViews.add(imageView);

            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,10), DensityUtil.dip2px(this,10));
            point.setLayoutParams(params);

            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(GuideActivity.this,10);
            }

            llGroupPoint.addView(point);
        }

    }



    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            leftMargin = llGroupPoint.getChildAt(1).getLeft() - llGroupPoint.getChildAt(0).getLeft();

        }
    }

    class MyOnPagerChanfeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //int maginLeft = (int) (leftMagin * positionOffset);
            //maginLeft = position * leftMagin + (int) (leftMagin * positionOffset);
            float left = leftMargin *(positionOffset + position) ;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = (int) left;
            ivRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == ids.length - 1) {
                btnStartMain.setVisibility(View.VISIBLE);
            } else {
                btnStartMain.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //ImageView imageView = new ImageView(GuideActivity.this);
           // imageView.setBackgroundResource(ids[position]);
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    @OnClick(R.id.btn_start_main)
    public void onViewClicked() {
        CacheUitls.putBoolean(this,"start_main",true);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
