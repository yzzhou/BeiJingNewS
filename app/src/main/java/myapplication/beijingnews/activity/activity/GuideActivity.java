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
    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private int leftMagin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        viewpager.setAdapter(new MyPagerAdapter());
        viewpager.addOnPageChangeListener(new MyOnPagerChanfeListener());

        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,10), DensityUtil.dip2px(this,10));
            if (i != 0) {
                params.leftMargin = 10;
            }
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.point_normal);
            llGroupPoint.addView(imageView);
        }
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }



    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            leftMagin = llGroupPoint.getChildAt(1).getLeft() - llGroupPoint.getChildAt(0).getLeft();

        }
    }

    class MyOnPagerChanfeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int maginLeft = (int) (leftMagin * positionOffset);
            maginLeft = position * leftMagin + (int) (leftMagin * positionOffset);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = maginLeft;
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
            return ids.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(ids[position]);
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
        Intent intent = new Intent(GuideActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
