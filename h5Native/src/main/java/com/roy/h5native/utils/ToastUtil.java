package com.roy.h5native.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.roy.h5native.jsNative.NativeManager;
import com.roy.h5native.R;
import com.roy.h5native.dialog.BaseDialog;
import com.roy.h5native.view.ToastView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;

/**
 * 类描述：toast
 */
public class ToastUtil {

    //private static Toast mToast;//toast样式


    public static void showToast(String msg, int time, int from, Context context) {
        showToast(msg, time, from, context, 1500);
    }

    private static void showToast(String msg, int time, int from, Context context, int cnt) {
        if (context == null) return;
        Toast mToast = new Toast(context.getApplicationContext()); //Toast.makeText(context.getApplicationContext(), msg, time);
        ToastView mToastView = new ToastView(context);
        mToastView.setText(msg);
        mToastView.setImage(from);
        mToast.setDuration(time);
        mToast.setView(mToastView);

        mToast.setGravity(Gravity.CENTER, 0, 0);

        //不设置的话，最高显示到状态栏下面
        mToast.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //自定义toast 显示时长
//         showMyToast(mToast, cnt);
        mToast.show();
        mToast.getView().removeCallbacks(null);
        mToast.getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mToast.cancel();
                mToastView.removeAllViews();
            }
        }, cnt);
    }


    public static void showToastTwoSeconds(String msg) {
        showToast(msg, 2000, ToastView.FAILURE_TYPE, NativeManager.Companion.getInstance().getAppContext(), 2000);
    }

    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, cnt);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    public static BaseDialog errorDialog(Activity activity, String title, String msg, String leftText, String rightText, BaseDialog.IBaseDialog iBaseDialog) {
        return errorDialog(activity, title, msg, leftText, rightText, 0, 0, iBaseDialog);
    }

    public static BaseDialog errorDialog(Activity activity, String title, String msg, String leftText, @NonNull String rightText, int leftColor, int rightColor, BaseDialog.IBaseDialog iBaseDialog) {
        if (activity == null) {
            return null;
        }

        BaseDialog dialog = new BaseDialog(activity,
                title,
                msg,
                leftText,
                rightText,        // activity.getResources().getString(R.string.confirm),
                leftColor, rightColor, false,
                iBaseDialog);

        if (!activity.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    public static void errorDialog(Activity activity, String msg) {
        errorDialog(activity, null, msg, null, activity.getResources().getString(R.string.confirm), null);
    }

    public static void errorDialog(Activity activity, String msg, String confirm) {
        errorDialog(activity, null, msg, null, confirm, null);
    }


    public static void errorDialog(Activity activity, String title, String msg, String confirm) {
        errorDialog(activity, title, msg, null, confirm, null);
    }

    /**
     * 设置内容文本相对容器居中 换行 左边对齐
     *
     * @param activity
     * @param msg
     * @param contentCenterLineFeedLeft
     */
    public static void errorDialog(Activity activity, String msg, boolean contentCenterLineFeedLeft) {
        BaseDialog dialog = errorDialog(activity, null, msg, null, activity.getResources().getString(R.string.confirm), null);
        if (dialog != null && contentCenterLineFeedLeft) {
            dialog.setTvContentCenterLineFeedLeft();
        }
    }

    public static void errorBtnLeftAndRightDialog(Activity activity, String title, String msg, BaseDialog.IBaseDialog baseDialog) {
        errorDialog(activity, title, msg, activity.getResources().getString(R.string.cancel), activity.getResources().getString(R.string.confirm), baseDialog);
    }

    public static void errorBtnLeftAndRightDialog(Activity activity, String title, String msg, String rightText, BaseDialog.IBaseDialog baseDialog) {
        errorDialog(activity, title, msg, activity.getResources().getString(R.string.cancel), rightText, baseDialog);
    }

    public static void success(String msg) {
        showToast(msg, Toast.LENGTH_LONG, ToastView.SUCCESS_TYPE, NativeManager.Companion.getInstance().getAppContext());
    }

    public static void error(String msg) {
        showToast(msg, Toast.LENGTH_LONG, ToastView.FAILURE_TYPE, NativeManager.Companion.getInstance().getAppContext());
    }

    public static void normal(String msg) {
        showToast(msg, Toast.LENGTH_LONG, ToastView.NORMAL_TYPE, NativeManager.Companion.getInstance().getAppContext());
    }

    /**
     * 关闭当前Toast
     */
    private void cancelCurrentToast() {
      /*  if (mToast != null) {
            mToast.cancel();
        }*/
    }

    /**
     * 重置toast 信息
     */
    private void resetToast() {
     /*  // mToastView = null;
        mToast = null;*/
    }
}
