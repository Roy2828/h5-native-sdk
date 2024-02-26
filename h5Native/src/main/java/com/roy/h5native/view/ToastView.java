package com.roy.h5native.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roy.h5native.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 类描述：toastView
 */
public class ToastView extends LinearLayout {
    private TextView tvTip;
    private ImageView ivTip;

    public static final int NORMAL_TYPE = 0;
    public static final int SUCCESS_TYPE = 1;
    public static final int FAILURE_TYPE = 2;

    public ToastView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_toast, this, true);

        tvTip = findViewById(R.id.tv_tip);
        ivTip = findViewById(R.id.iv_tip);
    }

    public ToastView setText(String text) {
        if (tvTip != null) {
            tvTip.setText(text);
        }
        return this;
    }

    public ToastView setImage(int type) {
        switch (type) {
            case SUCCESS_TYPE:
                ivTip.setBackground(getResources().getDrawable(R.mipmap.toast_yes));
                ivTip.setVisibility(VISIBLE);
                break;
            default:
                ivTip.setVisibility(GONE);
                break;
        }
        return this;
    }
}
