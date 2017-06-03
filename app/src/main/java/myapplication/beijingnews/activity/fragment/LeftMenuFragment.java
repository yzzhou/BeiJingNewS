package myapplication.beijingnews.activity.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import myapplication.beijingnews.R;
import myapplication.beijingnews.activity.activity.MainActivity;
import myapplication.beijingnews.activity.base.BaseFragment;
import myapplication.beijingnews.activity.domain.NewsCenterBean;
import myapplication.beijingnews.activity.pager.NewsPager;

/**
 * Created by zhouzhou on 2017/6/1.
 */

public class LeftMenuFragment extends BaseFragment {
    private ListView listView;
    private List<NewsCenterBean.DataBean> datas;
    private int prePosition;
    private LeftMenuAdapter adapter;
    @Override
    public View initVeiw() {
        listView = new ListView(context);

//        listView.setTextSize(20);
//        listView.setTextColor(Color.BLACK);
        listView.setPadding(0,40,0,0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prePosition = position;
                adapter.notifyDataSetChanged();

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
                switchPager(prePosition);
            }
        });
        return listView;
    }

    private void switchPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment =  mainActivity.getContentFragment();
        NewsPager  newsPager =contentFragment.getNewSpager();
        newsPager.switchPager(position);

    }

    @Override
    public void initData() {
        super.initData();
        //textView.setText("左侧菜单");
    }
    public  void setData(List<NewsCenterBean.DataBean> datas){
            this.datas = datas;
        for(int i = 0;i<datas.size();i++){
            Log.e("TAG",""+datas.get(i).getTitle());
            adapter= new LeftMenuAdapter();
            listView.setAdapter(adapter);
            switchPager(prePosition);
        }
    }

    private class LeftMenuAdapter  extends BaseAdapter{
        @Override
        public int getCount() {
            return datas ==null ? 0 : datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu,null);
            if(prePosition ==position){
                textView.setEnabled(true);
            }else{
                textView.setEnabled(false);
            }
            NewsCenterBean.DataBean dataBean = datas.get(position);
            textView.setText(dataBean.getTitle());
            return textView;
        }
    }
}
