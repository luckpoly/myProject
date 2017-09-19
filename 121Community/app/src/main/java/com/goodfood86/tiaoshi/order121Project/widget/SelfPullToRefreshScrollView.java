package com.goodfood86.tiaoshi.order121Project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by Administrator on 2016/12/6.
 */

public class SelfPullToRefreshScrollView extends PullToRefreshScrollView {
    public SelfPullToRefreshScrollView(Context context) {
        super(context);
    }

    public SelfPullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfPullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public SelfPullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    private TextView mHeaderText;
}
