package com.roy.h5native.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roy.h5native.R;
import com.roy.h5native.dialog.base.BaseDialogCommon;

import androidx.annotation.NonNull;

/**
 * 类描述：基类弹窗
 */
public class BaseDialog extends BaseDialogCommon {

    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTitle;
    private TextView tvContent;
    private View divider;
    private IBaseDialog iBaseDialog;
    private String title;
    private Object content;
    private String leftText;
    private String rightText;
    private int rightColor;
    private int leftColor;
    private boolean isDrawable;
    private boolean isDismiss = true;


    public BaseDialog(Context context, String title, Object content, String leftText, String rightText, int leftColor, int rightColor, boolean isDrawable, IBaseDialog iBaseDialog) {
        super(context, R.style.BaseDialogStyle);
        this.iBaseDialog = iBaseDialog;
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.rightColor = rightColor;
        this.leftColor = leftColor;
        this.isDrawable = isDrawable;
    }


    public BaseDialog(@NonNull Context context, String title, String content, @NonNull String leftText, @NonNull String rightText, @NonNull int leftColor, @NonNull int rightColor, @NonNull boolean isDrawable, boolean isDismiss, @NonNull IBaseDialog iBaseDialog) {
        super(context, R.style.BaseDialogStyle);
        this.iBaseDialog = iBaseDialog;
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.rightColor = rightColor;
        this.leftColor = leftColor;
        this.isDrawable = isDrawable;
        this.isDismiss = isDismiss;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_base);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        divider = (View) findViewById(R.id.divider);

        //无标题
        if (title != null && !"".equals(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        if (content != null) {
            if (content instanceof SpannableString) {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setHighlightColor(getContext().getResources().getColor(android.R.color.transparent));
                tvContent.setText((SpannableString) content);
                tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            }else if (content instanceof String && !"".equals(content)){
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText((String)content);
            }else{
                tvContent.setVisibility(View.GONE);
            }
        }else{
            tvContent.setVisibility(View.GONE);
        }

        //无左按钮
        if (leftText == null || "".equals(leftText)) {
            tvLeft.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }
        //无右按钮
        if (rightText == null || "".equals(rightText)) {
            tvRight.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }

        tvLeft.setText(leftText);
        tvRight.setText(rightText);

        //字体颜色(是否为Drawable文件)
        if (isDrawable) {
            if (rightColor != 0) {
                tvRight.setTextColor(getContext().getResources().getColorStateList(rightColor));
            }
            if (leftColor != 0) {
                tvLeft.setTextColor(getContext().getResources().getColorStateList(leftColor));
            }
        } else {
            if (rightColor != 0) {
                tvRight.setTextColor(getContext().getResources().getColor(rightColor));
            }
            if (leftColor != 0) {
                tvLeft.setTextColor(getContext().getResources().getColor(leftColor));
            }

        }

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDismiss) {
                    dismiss();
                }
                if (iBaseDialog != null) {
                    iBaseDialog.clickLeft(v);
                }
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDismiss) {
                    dismiss();
                }
                if (iBaseDialog != null) {
                    iBaseDialog.clickRight(v);
                }
            }
        });
    }


    public BaseDialog setTvRight(String progress) {
        tvRight.setText(progress);
        return this;
    }

    public BaseDialog setTvRightAble(boolean isAble) {
        tvRight.setClickable(isAble);
        return this;
    }

    /**
     * 设置文本相对容器居中 换行 左边对齐
     */
    public void setTvContentCenterLineFeedLeft() {
        if (tvContent != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvContent.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.CENTER;//此处相当于布局文件中的Android:layout_gravity属性
            tvContent.setGravity(Gravity.LEFT); //此处相当于布局文件中的Android：gravity属性
            tvContent.setLayoutParams(layoutParams);
        }
    }

    public interface IBaseDialog {
        void clickLeft(View v);

        void clickRight(View v);
    }
}
