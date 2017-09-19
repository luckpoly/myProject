package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 猪猪~ on 2016/3/12.
 */
public class UploadImgGridView extends GridView{
    public UploadImgGridView(Context context) {
        super(context);
    }

    public UploadImgGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UploadImgGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
