package myapplication.beijingnews.activity.uitls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class CacheUitls {

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
    public  static  boolean getBoolean(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return  sp.getBoolean(key,false);
    }
}
