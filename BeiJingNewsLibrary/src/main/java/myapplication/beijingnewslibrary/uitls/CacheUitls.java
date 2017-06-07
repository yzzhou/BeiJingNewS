package myapplication.beijingnewslibrary.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class CacheUitls {

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();


    }
    public  static  boolean getBoolean(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return  sp.getBoolean(key,false);
    }

    public static String getString(Context context, String key) {
        String values = "";

        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        //return sp.getString(key,"");
        values = sp.getString(key,"");
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                //sdcard可用
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                String dir = Environment.getExternalStorageDirectory()+"/beijingnews/files";
                //文件名称
                String fileName = MD5Encoder.encode(key);
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                File file = new File(dir, fileName);
                //得到 //sdcard/beijingnews/
                if(file.exists()){
                    //读取文件
                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((length=fis.read(buffer)) != -1){
                        baos.write(buffer,0,length);
                    }
                    String content = baos.toString();
                    if(!TextUtils.isEmpty(content)){
                        values = content;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return values;
    }

    public static void putString(Context context, String key, String values) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,values).commit();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            try {
                String dir = Environment.getExternalStorageDirectory()+"/beijingnews/files";
                String fileName = MD5Encoder.encode(key);
                File file = new File(dir,fileName);
                File parentFile = file.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                if(!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(values.getBytes());
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
