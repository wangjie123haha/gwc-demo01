package com.example.wangjie20180917.di;

/**
 * Created by lenovo on 2018/9/17.
 */

public interface icontract {
    /**
     * iview
     */
    public  interface  iview{
        //显示
        void  showdata(String json);

    }
    /**
     * ipresenter
     */
    public  interface  ipresenter<iview>{
        //关联
        void  attachview(iview iview);
        //取消
        void  detachview(iview iview);
        //逻辑
        void  requestinfo();
    }
    /**
     * imoudle
     */
    public  interface  imoudle{
        //接口回调
        interface  callisten{
            //信息显示
            void  responsemsg(String json);
        }
        //代码
        void  requestdata(callisten callisten);
    }
}
