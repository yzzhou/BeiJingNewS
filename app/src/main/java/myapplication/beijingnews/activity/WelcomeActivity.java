package myapplication.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.GuideActivity;
import myapplication.beijingnews.activity.activity.MainActivity;
import myapplication.beijingnewslibrary.uitls.CacheUitls;

public class WelcomeActivity extends AppCompatActivity {
    private RelativeLayout activity_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity_welcome = (RelativeLayout)findViewById(R.id.activity_welcome);
        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setFillAfter(true);
        ra.setDuration(2000);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setFillAfter(true);
        aa.setDuration(2000);

        AnimationSet set =new AnimationSet(false);
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);

        activity_welcome.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());
    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            boolean startMain= CacheUitls.getBoolean(WelcomeActivity.this,"start_main");
            Intent intent =null;
            if(startMain){
            intent = new Intent(WelcomeActivity.this, MainActivity.class);

            }else{
                intent = new Intent(WelcomeActivity.this, GuideActivity.class);

            }
            startActivity(intent);

            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
