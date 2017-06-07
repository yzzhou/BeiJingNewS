package myapplication.beijingnewslibrary.uitls;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhouzhou on 2017/6/7.
 */

public class MemoryCachUtils {
    private LruCache<String,Bitmap> lruCache;
    public MemoryCachUtils(){
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        lruCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public void putBitmap2Memory(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl,bitmap);
    }

    public Bitmap getBiamapMemory(String imageUrl) {
        return lruCache.get(imageUrl);
    }
}
