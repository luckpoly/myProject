package com.goodfood86.tiaoshi.order121Project.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.goodfood86.tiaoshi.order121Project.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TitleBarView {
    public View view;
    @ViewInject(R.id.nav_back)
    public ImageView nav_back;
    @ViewInject(R.id.nav_title)
    public TextView nav_title;
    @ViewInject(R.id.nav_right)
    public ImageView nav_right;
    @ViewInject(R.id.nav_right_text)
    public TextView nav_right_text;
    /**
     * 初始化所有按钮，用到哪个显示哪个
     *
     * @param view
     */
    public TitleBarView(View view, String titleText) {
        this.view = view;
        ViewUtils.inject(this, view);
        initViews(titleText);
    }


    /**
     * 初始化按钮，并对back绑定默认返回事件
     *
     * @param context
     * @param view
     */
    public TitleBarView(final Activity context, View view, String titleText) {

        this.view = view;
        ViewUtils.inject(this, view);
        initViews(titleText);

        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }

    private void initViews(String titleText) {
        nav_back.setVisibility(View.GONE);
        nav_right.setVisibility(View.GONE);
        if (titleText != null) {
            nav_title.setText(titleText);
        }
    }

}
