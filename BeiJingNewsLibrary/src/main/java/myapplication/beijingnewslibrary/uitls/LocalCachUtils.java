package myapplication.beijingnewslibrary.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zhouzhou on 2017/6/7.
 */
public class LocalCachUtils {
    private final MemoryCachUtils memoryCachUtils;
    public LocalCachUtils(MemoryCachUtils memoryCachUtils){
        this.memoryCachUtils = memoryCachUtils;
    }
    public void putBitmap2local(String imageUrl, Bitmap  bitmap){

        try {
            String dsr = Environment.getExternalStorageDirectory() + "/beijingnews/";
            String fileName = MD5Encoder.encode(imageUrl);
            File file = new File(dsr,fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                //创建多级目录
                parentFile.mkdirs();
            }
            //创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Bitmap getBitmap(String imageUrl) {

        try {
            String dsr = Environment.getExternalStorageDirectory() + "/beijingnews/";
            String fileName = MD5Encoder.encode(imageUrl);
            File file  =new File(dsr,fileName);
            if(file.exists()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                if(bitmap!=null){
                    memoryCachUtils.putBitmap2Memory(imageUrl,bitmap);
                }
                return  bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
