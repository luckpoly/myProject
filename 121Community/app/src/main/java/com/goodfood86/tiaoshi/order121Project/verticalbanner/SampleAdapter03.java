package com.goodfood86.tiaoshi.order121Project.verticalbanner;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.WebViewShowActivity;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;

import java.util.List;

/**
 * Description:
 * <p/>
 * Created by rowandjj(chuyi)<br/>
 * Date: 16/1/7<br/>
 * Time: 下午2:41<br/>
 */
public class SampleAdapter03 extends BaseBannerAdapter<Model01> {
    private List<Model01> mDatas;
    private Context context;
    public SampleAdapter03(Context context,List<Model01> datas) {
        super(datas);
        this.context=context;
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_03,null);
    }
    @Override
    public void setItem(final View view, final Model01 data) {
        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(data.title);
        TextView tag = (TextView) view.findViewById(R.id.tag);
        tag.setText(data.url);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewShowActivity.class);
                intent.putExtra("url", data.link);
                if (!TextUtils.isEmpty(data.link)){
                    context. startActivity(intent);
                }else if (!TextUtils.isEmpty(data.content)){
                    intent.putExtra("content", data.content);
                    context. startActivity(intent);
                }
            }
        });

    }
}
