package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.adapter.PhotosMenuDetailPagerAdapater;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.domain.PhotosMenuDetailPagerBean;
import myapplication.beijingnewslibrary.uitls.ConstantUtils;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/3.
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean dataBean;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private String url;
    private PhotosMenuDetailPagerAdapater adapater;
    private List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private boolean isShowList = true;

    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
//        textView = new TextView(context);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);
        View view = View.inflate(context, R.layout.pager_photos_menu_detail, null);
        ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(url);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //textView.setText("组图详情页面的内容");
        url = ConstantUtils.BASE_URL + dataBean.getUrl();
        getDataFromNet(url);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "图组请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "图组请求成功==" + response);
                        processData(response);

                    }

                });
    }

    private void processData(String json) {
        PhotosMenuDetailPagerBean bean = new Gson().fromJson(json, PhotosMenuDetailPagerBean.class);
        datas = bean.getData().getNews();
        if (datas != null && datas.size() > 0) {
            progressbar.setVisibility(View.GONE);
            adapater = new PhotosMenuDetailPagerAdapater(context, datas);
            recyclerview.setAdapter(adapater);
            recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            progressbar.setVisibility(View.VISIBLE);
        }
        refreshLayout.setRefreshing(false);

    }

    public void swichListAndGrid(ImageButton iv) {
        if (isShowList) {
            recyclerview.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
            isShowList = false;
            iv.setImageResource(R.drawable.icon_pic_list_type);
        } else {
            recyclerview.setLayoutManager(new LinearLayoutManager(context, GridLayoutManager.VERTICAL, false));
            isShowList = true;
            iv.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }
}
