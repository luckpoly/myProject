package com.goodfood86.tiaoshi.order121Project.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CommonDialog extends Dialog {
    private Context context;
    public CommonDialog(Context context) {
        super(context);
        this.context=context;
    }
    public CommonDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }
    public static CommonDialog show(final Activity context, CharSequence message,
                                    boolean cancelable, CharSequence title) {
        final CommonDialog dialog = new CommonDialog(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.common_dialog);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.content).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.tv_info);
            txt.setText(message);
        }
        if (title == null || title.length() == 0) {
            dialog.findViewById(R.id.li_title).setVisibility(View.GONE);
            dialog.findViewById(R.id.content).setBackgroundResource(R.drawable.bg_title_custom_dialog);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.tv_title_show);
            txt.setText(title);
        }
        Button btn_sure= (Button) dialog.findViewById(R.id.sure_perfect_btn);
        Button btn_cancel= (Button) dialog.findViewById(R.id.cancel_perfect_btn);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
//         dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//         dialog.show();
        return dialog;
    }


}
