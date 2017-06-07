package myapplication.beijingnews.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.PicassoSampleActivity;
import myapplication.beijingnews.activity.domain.PhotosMenuDetailPagerBean;
import myapplication.beijingnewslibrary.uitls.BitmapCacheTtils;
import myapplication.beijingnewslibrary.uitls.ConstantUtils;
import myapplication.beijingnewslibrary.uitls.NetCacheUtils;

/**
 * Created by zhouzhou on 2017/6/6.
 */

public class PhotosMenuDetailPagerAdapater extends RecyclerView.Adapter<PhotosMenuDetailPagerAdapater.MyViewHolder> {
    //private final List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private final Context context;
    private BitmapCacheTtils bitmapCacheTtils;
    private final List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private RecyclerView recyclerView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NetCacheUtils.SUCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    int position = msg.arg1;
                    Log.e("TAG","请求图片成功=="+position);
                   // ImageView imageview = (ImageView) recyclerview.findViewWithTag(position);
                    ImageView imageview = (ImageView) recyclerView.findViewById(position);
                    if(imageview != null && bitmap != null){
                        imageview.setImageBitmap(bitmap);
                    }

                    break;
                case NetCacheUtils.FAIL:
                    position = msg.arg1;
                    Log.e("TAG","请求图片失败=="+position);
                    break;
            }
        }
    };


    public PhotosMenuDetailPagerAdapater(Context context, List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas,RecyclerView recyclerView) {
        this.context=context;
        this.datas =datas;
        bitmapCacheTtils = new BitmapCacheTtils(handler);
        this.recyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_photos, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //1.根据位置得到对应的数据
        PhotosMenuDetailPagerBean.DataBean.NewsBean newsBean = datas.get(position);
        //2.绑定数据
        holder.tvTitle.setText(newsBean.getTitle());
        //3.设置点击事件
        String imageUrl = ConstantUtils.BASE_URL+newsBean.getListimage();
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.pic_item_list_default)
//                .error(R.drawable.pic_item_list_default)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.ivIcon);
        //使用自定义的方式请求图片
        Bitmap bitmap = bitmapCacheTtils.getBitam(imageUrl,position);
        holder.ivIcon.setTag(position);
        if(bitmap !=null){
            holder.ivIcon.setImageBitmap(bitmap);
        }

    }

    /**
     * 返回总大小
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String listimage =ConstantUtils.BASE_URL+ datas.get(getLayoutPosition()).getListimage();
                    Intent intent = new Intent(context, PicassoSampleActivity.class);
                    intent.setData(Uri.parse(listimage));
                    context.startActivity(intent);
                }
            });
        }
    }
}
