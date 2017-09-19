package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.goodfood86.tiaoshi.order121Project.R;
//import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.BaseActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.HomeCategoryGVModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class HomeFLGridviewAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<HomeCategoryGVModel.DataBean> nodesBeanT;

    public HomeFLGridviewAdapter(Context context, List<HomeCategoryGVModel.DataBean> nodesBeanT) {
        this.context = context;
        this.nodesBeanT = nodesBeanT;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nodesBeanT.size();
    }

    @Override
    public Object getItem(int position) {
        return nodesBeanT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_gridview_homefl, null);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_image);
        ImageView iv_image_jy = (ImageView) view.findViewById(R.id.iv_image_jy);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_type_name);

//            tv_title.setText(nodesBeanT.get(position).getName());
//            TiaoshiApplication.getGlobalBitmapUtils().display(iv_img,nodesBeanT.get(position).getIcon());
        //更改图片
        switch (position){
            case 0:
                iv_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.fl_sxsg));
                break;
            case 1:
                iv_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.fl_scly));
                break;
            case 2:
                iv_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.fl_sjgz));
                break;
            case 3:
                iv_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.fl_lsjg));
                break;
        }

        return view;
    }
}
