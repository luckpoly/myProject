package com.goodfood86.tiaoshi.order121Project.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;


public class ProgressHUD extends Dialog {
    private Context context;
    public ProgressHUD(Context context) {
        super(context);
        this.context=context;
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.pro_hud_animation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.setAnimation(operatingAnim);
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static ProgressHUD show(Context context, CharSequence message,
                                   boolean cancelable, OnCancelListener cancelListener) {
        ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.setCanceledOnTouchOutside(cancelable);
        if(cancelListener!=null){
            dialog.setOnCancelListener(cancelListener);
        }
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
//         dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//         dialog.show();
        return dialog;
    }
}
