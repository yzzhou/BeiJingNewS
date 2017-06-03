package myapplication.beijingnews.activity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.fragment.ContentFragment;
import myapplication.beijingnews.activity.fragment.LeftMenuFragment;
import myapplication.beijingnews.activity.uitls.DensityUtil;


public class MainActivity extends SlidingFragmentActivity {
    private static final String MAIN_CONTENT = "main_content";
    private static final String LEFT_MENU = "left_menu";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSlidingNenu();
        initFragment();
    }

    private void initSlidingNenu() {
        setBehindContentView(R.layout.activity_left);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this,200));
    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_left, new LeftMenuFragment(), LEFT_MENU);
        ft.replace(R.id.fl_main, new ContentFragment(), MAIN_CONTENT);
        ft.commit();
    }
    public  LeftMenuFragment getleftMenuFragment(){
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFT_MENU);

    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT);
    }
}
