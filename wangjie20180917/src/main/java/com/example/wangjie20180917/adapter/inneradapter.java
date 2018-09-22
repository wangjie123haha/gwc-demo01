package com.example.wangjie20180917.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.wangjie20180917.R;
import com.example.wangjie20180917.bean.haha;
import com.example.wangjie20180917.bean.news;
import com.example.wangjie20180917.widge.togglebutton;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by lenovo on 2018/9/17.
 */

public class inneradapter extends RecyclerView.Adapter<inneradapter.twoholder> {

    private Context context;
    private List<news.DataBean.ListBean> list;

    public inneradapter(Context context, List<news.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public twoholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.include2, null);
        twoholder twoholder = new twoholder(view);
        return twoholder;
    }

    @Override
    public void onBindViewHolder(final twoholder holder, final int position) {
        TextView num = holder.toggle.findViewById(R.id.num);
        holder.toggle.setAddAndMinus(new togglebutton.AddAndMinus() {
            @Override
            public void add() {
                list.get(position).setNum(list.get(position).getNum() + 1);
                haha haha = new haha();
                // haha.setW(1);
                EventBus.getDefault().post(haha);
            }
            @Override
            public void minus() {
                list.get(position).setNum(list.get(position).getNum() - 1);
                haha haha = new haha();
                //haha.setW(1);
                EventBus.getDefault().post(haha);
            }
        });
        num.setText(list.get(position).getNum()+"");
        holder.cb_03.setChecked(list.get(position).getInnerchecked());
         //赋值
        holder.price.setText("单价是:"+list.get(position).getPrice());
        String[] strings = list.get(position).getImages().split("\\|");
        Uri uri = Uri.parse(strings[0]);
        holder.my_image_view.setImageURI(uri);

        holder.cb_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //内部状态更改完
                      list.get(position).setInnerchecked(holder.cb_03.isChecked());
                 //接口回传
                    OnCheckedChangeListen.onchecked(holder.getLayoutPosition(),holder.cb_03.isChecked());

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建视图
    class  twoholder extends  RecyclerView.ViewHolder{

        private final CheckBox cb_03;
        private final SimpleDraweeView my_image_view;
        private final TextView price;
        private final togglebutton toggle;

        public twoholder(View itemView) {
            super(itemView);
            cb_03 = itemView.findViewById(R.id.cb_03);
            my_image_view = itemView.findViewById(R.id.my_image_view);
            price = itemView.findViewById(R.id.price);
            toggle = itemView.findViewById(R.id.toggle);
        }
    }
    private  OnCheckedChangeListen OnCheckedChangeListen;
    //接口回调
    public  interface  OnCheckedChangeListen{
        void  onchecked(int layoutPosition, boolean checked);
    }
    public  void  setCheckedChangeListen(OnCheckedChangeListen OnCheckedChangeListen){
        this.OnCheckedChangeListen =OnCheckedChangeListen;
    }
}
