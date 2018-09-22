package com.example.wangjie20180917.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.wangjie20180917.R;
import com.example.wangjie20180917.bean.news;
import com.example.wangjie20180917.ui.MainActivity;

import java.util.List;

/**
 * Created by lenovo on 2018/9/17.
 */

public class myadapter extends RecyclerView.Adapter<myadapter.oneholder> {

    private Context context;
    private List<news.DataBean> list;
    private int layoutPosition;

    public myadapter(Context context, List<news.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnCheckedChangeLisenter {
       void onChecked(int layoutPosition, boolean isChecked);

        void onOutterItemChecked(int layoutPosition, boolean checked);
    }

    public void setOnCheckedChangeLisenter(OnCheckedChangeLisenter onCheckedChangeLisenter) {
        this.onCheckedChangeLisenter = onCheckedChangeLisenter;
    }

    private RecyclerView rv_innerContainer;
    OnCheckedChangeLisenter onCheckedChangeLisenter;
    @Override
    public oneholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.include1, null);
        oneholder oneholder = new oneholder(view);
        return oneholder;
    }

    @Override
    public void onBindViewHolder(final oneholder holder, final int position) {
        //获取下表
        layoutPosition = holder.getLayoutPosition();
        //外层被点击之后及时通过接口回调的形式通知全选/反选按钮
        holder.cb_02.setOnCheckedChangeListener(null);
        holder.cb_02.setChecked(list.get(position).getOutchecked());
        holder.cb_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ischecked = holder.cb_02.isChecked();
                list.get(position).setOutchecked(ischecked);
                if (ischecked){
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                        list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(true);
                    }
                }else {
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                        list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(false);
                    }
                }
                onCheckedChangeLisenter.onOutterItemChecked(holder.getLayoutPosition(),ischecked);
            }
        });
    /*    holder.cb_02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                list.get(position).setOutchecked(ischecked);
                if (ischecked){
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                         list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(true);
                    }
                }else {
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                        list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(false);
                    }
                }
                onCheckedChangeLisenter.onOutterItemChecked(holder.getLayoutPosition(),ischecked);
            }
        });*/
         //全选状态
        holder.cb_02.setChecked(list.get(position).getOutchecked());
          //赋值
         holder.cb_02.setText(list.get(position).getSellerName());
         //创建布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        //设置布局
        holder.recycleview01.setLayoutManager(manager);
        //创建布局
        inneradapter inneradapter = new inneradapter(context,list.get(position).getList());
        holder.recycleview01.setAdapter(inneradapter);
        //接口回传inneradapter
        inneradapter.setCheckedChangeListen(new inneradapter.OnCheckedChangeListen() {
            @Override
            public void onchecked(int layoutPosition, boolean checked) {
                //默认定义一个值为false
                boolean isAllInnerChecked = true;
                for (int i = 0; i <list.get(holder.getLayoutPosition()).getList().size() ; i++) {
                    boolean innerchecked = list.get(holder.getLayoutPosition()).getList().get(i).getInnerchecked();
                    isAllInnerChecked=(isAllInnerChecked&innerchecked);
                }
                //通过条目的设置状态给商家设置状态
                holder.cb_02.setChecked(isAllInnerChecked);
                //外层条目
                list.get(position).setOutchecked(isAllInnerChecked);
                //接口回调
                onCheckedChangeLisenter.onChecked(holder.getLayoutPosition(),checked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建视图
    class  oneholder extends  RecyclerView.ViewHolder{

        private final CheckBox cb_02;
        private final RecyclerView recycleview01;

        public oneholder(View itemView) {
            super(itemView);
            cb_02 = itemView.findViewById(R.id.cb_02);
            recycleview01 = itemView.findViewById(R.id.recycleview01);
        }
    }
}
