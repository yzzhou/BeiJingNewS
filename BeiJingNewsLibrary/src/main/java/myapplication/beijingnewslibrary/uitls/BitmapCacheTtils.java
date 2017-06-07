package myapplication.beijingnewslibrary.uitls;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * Created by zhouzhou on 2017/6/7.
 */

public class BitmapCacheTtils {

    private MemoryCachUtils memoryCachUtils;
    private  LocalCachUtils localCachUtils;
    private NetCacheUtils netCacheUtils;


    public BitmapCacheTtils(Handler handler) {
        memoryCachUtils  =new MemoryCachUtils();
        localCachUtils = new LocalCachUtils(memoryCachUtils);
        netCacheUtils = new NetCacheUtils(handler,localCachUtils,memoryCachUtils);
    }
    public Bitmap getBitam(String imageUrl, int position) {
        if(memoryCachUtils!=null){
            Bitmap bitmap = memoryCachUtils.getBiamapMemory(imageUrl);
            if(bitmap !=null){
                Log.e("TAG", "图片是从内存获取的哦==" + position);
                return  bitmap;
            }
        }

        if (localCachUtils != null) {
            Bitmap bitmap = localCachUtils.getBitmap(imageUrl);
            if (bitmap != null) {
                Log.e("TAG", "图片是从本地获取的哦==" + position);
                //netCacheUtils.getBitmapFromNet(imageUrl, position);
                return bitmap;
            }
        }
        netCacheUtils.getBitmapFromNet(imageUrl, position);
        return null;
    }

}
