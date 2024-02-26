package com.roy.h5native.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.roy.h5native.R;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2021/11/22 16:16
 * author : Roy
 * version: 1.0
 */
public class BaseDialogCommon extends Dialog {
    Context context;

    public BaseDialogCommon(Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialogCommon(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected BaseDialogCommon(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.dialog_style);


    }



    @Override
    public void show() {
        if(isActiveActivity()) {
            super.show();
        }
    }


    @Override
    public void dismiss() {
        if(isActiveActivity()){
            close();
        }

    }


    private boolean isActiveActivity(){
        if (context!=null && context instanceof Activity){
            if(!((Activity)context).isFinishing() && !((Activity) context).isDestroyed()){
                 return true; //activity 还活着
            }
            return false;

        }
        return false;
    }

    private void close() {
        if(isShowing()) {
            super.dismiss();
        }
    }

}
