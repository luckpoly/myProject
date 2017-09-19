package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

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
    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        if (mode == Mode.PULL_FROM_START){
            SelfLoadingLayout selfLoadingLayout = new SelfLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
            mHeaderText = (TextView) selfLoadingLayout.findViewById(R.id.pull_to_refresh_text);
            mHeaderText.setVisibility(GONE);
            return selfLoadingLayout;
        }else {
            LoadingLayout loadingLayout = super.createLoadingLayout(context, mode, attrs);
            mHeaderText = (TextView) loadingLayout.findViewById(R.id.pull_to_refresh_text);
            mHeaderText.setVisibility(VISIBLE);
            return loadingLayout;
        }
    }
}
