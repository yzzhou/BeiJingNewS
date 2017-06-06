package myapplication.beijingnews.activity.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import myapplication.beijingnews.activity.DetailPager.InteractMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.NewsMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.PhotosMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.TopicMenuDetailPager;
import myapplication.beijingnews.activity.DetailPager.VoteMenuDetailPager;
import myapplication.beijingnews.activity.activity.MainActivity;
import myapplication.beijingnews.activity.base.BasePager;
import myapplication.beijingnews.activity.base.MenuDetailBasePager;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.fragment.LeftMenuFragment;
import myapplication.beijingnewslibrary.uitls.CacheUitls;
import myapplication.beijingnewslibrary.uitls.ConstantUtils;
import okhttp3.Call;

/**
 * Created by zhouzhou on 2017/6/2.
 */

public class NewsPager extends BasePager{
    private List<NewsCenterBean.DataBean> datas;
    private  List<MenuDetailBasePager>  basePagers;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");
        ib_menu.setVisibility(View.VISIBLE);

        TextView textView = new TextView(context);
        textView.setText("新闻页面的内容");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
        String string = CacheUitls.getString(context, ConstantUtils.NEWSCENTER_PAGER_URL);
        if(!TextUtils.isEmpty(string)){
            processData(string);
            Log.e("TAG","取出缓存的数据..=="+string);
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = ConstantUtils.NEWSCENTER_PAGER_URL;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","请求数据失败=="+ e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","请求数据成功=="+ response);
                        CacheUitls.putString(context,ConstantUtils.NEWSCENTER_PAGER_URL,response);
                        processData(response);
                    }


                });
    }

    private void processData(String json) {
        //NewsCenterBean newsCenterBean = new Gson().fromJson(json,NewsCenterBean.class);
        NewsCenterBean newsCenterBean = parsJson(json);
        Log.e("TAG","解析成功了=="+newsCenterBean.getData().get(0).getChildren().get(0).getTitle());
        datas=newsCenterBean.getData();
        MainActivity mainActivity = (MainActivity) context;

        basePagers = new ArrayList<>();
        basePagers.add(new NewsMenuDetailPager(context,datas.get(0).getChildren()));
        basePagers.add(new TopicMenuDetailPager(context));
        basePagers.add(new PhotosMenuDetailPager(context));
        basePagers.add(new InteractMenuDetailPager(context));
        basePagers.add(new VoteMenuDetailPager(context));

        LeftMenuFragment leftMenuFragment = mainActivity.getleftMenuFragment();
        leftMenuFragment.setData(datas);

    }

    public void switchPager(int prePosition) {
        MenuDetailBasePager basePager= basePagers.get(prePosition);
        View rootView = basePager.rootView;
        fl_content.removeAllViews();
        fl_content.addView(rootView);
        basePager.initData();
    }
    private NewsCenterBean parsJson(String json){
        NewsCenterBean newsCenterBean = new NewsCenterBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int retcode = jsonObject.optInt("retcode");
            newsCenterBean.setRetcode(retcode);
            JSONArray data= jsonObject.optJSONArray("data");
            if(data !=null && data.length()>0){
                List<NewsCenterBean.DataBean> dataBeen = new ArrayList<NewsCenterBean.DataBean>();
                newsCenterBean.setData(dataBeen);
                for(int i = 0; i<data.length();i++){
                    JSONObject jsonObject1 = (JSONObject) data.get(i);
                    if(jsonObject1 !=null){
                        NewsCenterBean.DataBean dataBean1 = new NewsCenterBean.DataBean();
                        dataBean1.setId(jsonObject1.optInt("id"));
                        dataBean1.setTitle(jsonObject1.optString("title"));
                        dataBean1.setType(jsonObject1.optInt("type"));
                        dataBean1.setUrl(jsonObject1.optString("url"));
                        dataBeen.add(dataBean1);
                        JSONArray childrenData = jsonObject1.optJSONArray("children");
                        if(childrenData !=null &&childrenData.length()>0){
                            List<NewsCenterBean.DataBean.ChildrenBean> childrenBeen=new ArrayList<>();
                            dataBean1.setChildren(childrenBeen);
                            for(int j=0;j<childrenData.length();j++){
                                JSONObject childrenJson = (JSONObject) childrenData.get(j);
                                if(childrenJson !=null){
                                    NewsCenterBean.DataBean.ChildrenBean childrenData1 =new NewsCenterBean.DataBean.ChildrenBean();
                                    childrenData1.setId(childrenJson.optInt("id"));
                                    childrenData1.setTitle(childrenJson.optString("title"));
                                    childrenData1.setType(childrenJson.optInt("type"));
                                    childrenData1.setUrl(childrenJson.optString("url"));
                                    childrenBeen.add(childrenData1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  newsCenterBean;
    }
}
