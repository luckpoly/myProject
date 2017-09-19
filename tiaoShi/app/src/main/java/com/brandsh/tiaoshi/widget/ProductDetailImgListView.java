package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by XianXianGe on 2016/1/11.
 */
public class ProductDetailImgListView extends ListView {
    public ProductDetailImgListView(Context context) {
        super(context);
    }

    public ProductDetailImgListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductDetailImgListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
