package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.ShowImageActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class JuiceImgAdapter extends BaseAdapter {
    Context context;
    List<String> list;
    LayoutInflater inflater;

    public JuiceImgAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.juice_img_item,null);
       ImageView iv_img=(ImageView) view.findViewById(R.id.iv_img);
        TiaoshiApplication.getGlobalBitmapUtils().display(iv_img,list.get(position));
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowImageActivity.class);
                intent.putExtra("No",position);
                String [] str=new String[list.size()];
                for (int i=0;i<list.size();i++){
                    str[i]=list.get(i);
                }
                intent.putExtra("array",str);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
