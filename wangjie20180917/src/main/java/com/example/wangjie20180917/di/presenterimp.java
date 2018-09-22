package com.example.wangjie20180917.di;

import java.lang.ref.WeakReference;

/**
 * Created by lenovo on 2018/9/17.
 */

public class presenterimp implements  icontract.ipresenter<icontract.iview> {
    private  icontract.iview iview;
    private moudleimp moudleimp;
    private WeakReference<icontract.iview> iviewWeakReference;
    private WeakReference<icontract.imoudle> imoudleWeakReference;

    @Override
    public void attachview(icontract.iview iview) {
       this.iview = iview;
        moudleimp = new moudleimp();
        //弱引用
        iviewWeakReference = new WeakReference<>(iview);
        imoudleWeakReference = new WeakReference<icontract.imoudle>(moudleimp);
    }

    @Override
    public void detachview(icontract.iview iview) {
        iviewWeakReference.clear();
        imoudleWeakReference.clear();
    }

    @Override
    public void requestinfo() {
          moudleimp.requestdata(new icontract.imoudle.callisten() {
              @Override
              public void responsemsg(String json) {
                  iview.showdata(json);
              }
          });
    }
}
