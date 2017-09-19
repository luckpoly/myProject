package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by sisi on 16/3/10.
 */
public class OrderListItemListView extends ListView {
    public OrderListItemListView(Context context) {
        super(context);
    }

    public OrderListItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderListItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
