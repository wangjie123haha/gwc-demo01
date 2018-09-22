package com.example.wangjie20180917.widge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjie20180917.R;
import com.example.wangjie20180917.bean.haha;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lenovo on 2018/9/17.
 */

public class togglebutton extends LinearLayout implements View.OnClickListener {

    private Button jia;
    private Button jian;
    private TextView num;
    private  int w =122;

    public togglebutton(Context context) {
        super(context);
    }

    public togglebutton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    private void initview(Context context) {
        LayoutInflater.from(context).inflate(R.layout.include3,this);
        jia = findViewById(R.id.jia);
        jian = findViewById(R.id.jian);
        num = findViewById(R.id.num);
        //点击事件
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //获取值
        String s = num.getText().toString();
        int i = Integer.parseInt(s);
        switch (view.getId()){
            case R.id.jian:
                /*if (i==1){
                    Toast.makeText(getContext(), "最小数量为1", Toast.LENGTH_SHORT).show();
                }else {
                    i-=1;
                    num.setText(String.valueOf(i));

                }*/
                if (i > 1) {
                    if (addAndMinus != null) {
                        addAndMinus.minus();
                    }
                }
                break;
            case R.id.jia:
               /* i+=1;
                num.setText(String.valueOf(i));
                //发送
                haha haha = new haha();
                haha.setW(i);
                EventBus.getDefault().post(haha);*/
                if (addAndMinus != null) {
                    addAndMinus.add();
                }
                break;
        }
    }
    private AddAndMinus addAndMinus;

    public void setAddAndMinus(AddAndMinus addAndMinus) {
        this.addAndMinus = addAndMinus;
    }

    public interface AddAndMinus {
        void add();
        void minus();
    }
}
