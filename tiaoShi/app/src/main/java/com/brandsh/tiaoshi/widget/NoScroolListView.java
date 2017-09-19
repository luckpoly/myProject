package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/12/28.
 */

public class NoScroolListView extends ListView{
    public NoScroolListView(Context context) {
        super(context);
    }

    public NoScroolListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
