package myapplication.beijingnews.activity.DetailPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.domain.TabDetailPagerBean;
import myapplication.beijingnews.activity.uitls.ConstantUtils;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/5.
 */

public class TabDetailPager extends MenuDetailBasePager {
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @Bind(R.id.lv)
    ListView lv;

    private TextView textView;
    private String url;

    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;

    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_tab_detail, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = ConstantUtils.BASE_URL + childrenBean.getUrl();
        Log.e("TAG","url=="+url);
        tvTitle.setText(childrenBean.getTitle());
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                                .get()
                                .url(url)
                //                .addParams("username", "hyman")
                //                .addParams("password", "123")
                                        .build()                 .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                        Log.e("TAG", "请求失败==" + e.getMessage());

                                            }

                                        @Override
                                public void onResponse(String response, int id) {
                //                        Log.e("TAG", "请求成功==" + response);
                                                //缓存数据
                                                        processData(response);
                                    }


                                    });
            }

                private void processData(String response) {
                TabDetailPagerBean bean = new Gson().fromJson(response,TabDetailPagerBean.class);
               ;
                Log.e("TAG",""+bean.getData().getNews().get(0).getTitle());
            }



}
