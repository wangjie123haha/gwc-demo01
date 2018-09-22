package com.example.wangjie20180917.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjie20180917.R;
import com.example.wangjie20180917.adapter.myadapter;
import com.example.wangjie20180917.bean.haha;
import com.example.wangjie20180917.bean.news;
import com.example.wangjie20180917.di.icontract;
import com.example.wangjie20180917.di.presenterimp;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  implements icontract.iview {

    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.cb_01)
    CheckBox cb01;
    @BindView(R.id.zj)
    TextView zj;
    @BindView(R.id.js)
    Button js;
    private presenterimp presenterimp;
    int i;
    private List<news.DataBean> data;
    private LinearLayoutManager manager;
    private com.example.wangjie20180917.adapter.myadapter myadapter;
    private com.example.wangjie20180917.bean.news news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //注册eventbus
        EventBus.getDefault().register(this);
        //初始化
        presenterimp = new presenterimp();
        presenterimp.attachview(this);
        presenterimp.requestinfo();
    }

    @OnClick({R.id.title1, R.id.cb_01, R.id.js})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title1:
                i =10/0;
                break;
            case R.id.cb_01:

                if (cb01.isChecked()){
                    for (int i= 0; i <data.size() ; i++) {
                        data.get(i).setOutchecked(true);
                        for (int j = 0; j < data.get(i).getList().size(); j++) {
                           data.get(i).getList().get(j).setInnerchecked(true);
                        }
                    }
                }else {
                    for (int i= 0; i <data.size() ; i++) {
                        data.get(i).setOutchecked(false);
                        for (int j = 0; j < data.get(i).getList().size(); j++) {
                            data.get(i).getList().get(j).setInnerchecked(false);
                        }
                    }
                }
                initview();
                //刷新适配器
                myadapter.notifyDataSetChanged();
                break;
            case R.id.js:
                break;
        }
    }
   //总价
    private void initview() {
        int zong=0;
        for (int i = 0; i <news.getData().size(); i++) {
            for (int j = 0; j < news.getData().get(i).getList().size(); j++) {
                if (news.getData().get(i).getList().get(j).getInnerchecked()){

                    zong+=news.getData().get(i).getList().get(j).getPrice() * news.getData().get(i).getList().get(j).getNum();
                }
            }
        }
        zj.setText("总价是:"+zong);
        myadapter.notifyDataSetChanged();
    }

    @Override
    public void showdata(String json) {
        //解析
        Gson gson = new Gson();
        news = gson.fromJson(json, news.class);
        //获取对象
        data = news.getData();
      //  Toast.makeText(this, "data:" + data, Toast.LENGTH_SHORT).show();
        //创建布局管理器
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        //设置布局
        recycleview.setLayoutManager(manager);
        //设置适配器
        myadapter = new myadapter(MainActivity.this, data);
        //设置
        recycleview.setAdapter(myadapter);
        //接口回调
        myadapter.setOnCheckedChangeLisenter(new myadapter.OnCheckedChangeLisenter() {
            @Override
            public void onChecked(int layoutPosition, boolean isChecked) {
                //全局标识
                boolean isAllChecked = true;
                //外层条目的判断
                for (int i = 0; i <news.getData().size() ; i++) {
                    boolean outchecked = news.getData().get(i).getOutchecked();
                    //内层条目的判断
                    for (int j = 0; j <news.getData().get(i).getList().size() ; j++) {
                        boolean innerchecked = news.getData().get(i).getList().get(j).getInnerchecked();
                        isAllChecked=(isAllChecked&outchecked&innerchecked);
                    }
                }
                //刷新适配器
                cb01.setChecked(isAllChecked);
                //刷新总体适配器
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onOutterItemChecked(int layoutPosition, boolean checked) {
                  news.getData().get(layoutPosition).setOutchecked(checked);
                  //全局条目的标识
                boolean isAllChecked =true;
                //外层条目的判断
                for (int i = 0; i <news.getData().size() ; i++) {
                    boolean outchecked = news.getData().get(i).getOutchecked();
                    //内层条目的判断
                    for (int j = 0; j <news.getData().get(i).getList().size() ; j++) {
                        boolean innerchecked = news.getData().get(i).getList().get(j).getInnerchecked();
                        isAllChecked=(isAllChecked&outchecked&innerchecked);
                    }
                }
                cb01.setChecked(isAllChecked);
                //刷新总体适配器
                myadapter.notifyDataSetChanged();
            }
        });
    }
   //定义订阅者
        @Subscribe(threadMode = ThreadMode.MAIN)
        public  void  msg(haha ha){
            //  int w = ha.getW();
            //  Toast.makeText(this, "w:" + w, Toast.LENGTH_SHORT).show();
            initview();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterimp.detachview(this);
        //注销
        EventBus.getDefault().unregister(this);
    }
}
