package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.EvaluateJsonData1;
import com.brandsh.tiaoshi.widget.CircleImageView;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by libokang on 15/10/26.
 */
public class StoreEvaluateAdapter extends BaseAdapter {

    private Context context;
    private List<EvaluateJsonData1.DataBean.ListBean> mDataset;
    private EvaluateJsonData1.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private BitmapUtils bitmapUtils1;

    public StoreEvaluateAdapter(List<EvaluateJsonData1.DataBean.ListBean> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
        bitmapUtils = TiaoshiApplication.getHeadImgBitmapUtils();
        bitmapUtils1 = TiaoshiApplication.getGlobalBitmapUtils();
    }

    private BitmapUtils mBitmapUtil;

    @Override
    public int getCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listEntity = mDataset.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.store_evalute_item, null);
            holder.store_evaluate_civ_head = (CircleImageView) convertView.findViewById(R.id.store_evaluate_civ_head);
            holder.store_evaluate_iv1 = (ImageView) convertView.findViewById(R.id.store_evaluate_iv1);
            holder.store_evaluate_iv2 = (ImageView) convertView.findViewById(R.id.store_evaluate_iv2);
            holder.store_evaluate_iv3 = (ImageView) convertView.findViewById(R.id.store_evaluate_iv3);
            holder.store_evaluate_iv4 = (ImageView) convertView.findViewById(R.id.store_evaluate_iv4);
            holder.store_evaluate_iv5 = (ImageView) convertView.findViewById(R.id.store_evaluate_iv5);
            holder.store_evaluate_ivstart1 = (ImageView) convertView.findViewById(R.id.store_evaluate_ivstart1);
            holder.store_evaluate_ivstart2 = (ImageView) convertView.findViewById(R.id.store_evaluate_ivstart2);
            holder.store_evaluate_ivstart3 = (ImageView) convertView.findViewById(R.id.store_evaluate_ivstart3);
            holder.store_evaluate_ivstart4 = (ImageView) convertView.findViewById(R.id.store_evaluate_ivstart4);
            holder.store_evaluate_ivstart5 = (ImageView) convertView.findViewById(R.id.store_evaluate_ivstart5);
            holder.store_evaluate_tv_content = (TextView) convertView.findViewById(R.id.store_evaluate_tv_content);
            holder.store_evaluate_tv_nickname = (TextView) convertView.findViewById(R.id.store_evaluate_tv_nickname);
            holder.store_evaluate_tv_time = (TextView) convertView.findViewById(R.id.store_evaluate_tv_time);
            holder.store_evaluate_ll_piclist = (LinearLayout) convertView.findViewById(R.id.store_evaluate_ll_piclist);
            holder.store_evaluate_ll_star = (LinearLayout) convertView.findViewById(R.id.store_evaluate_ll_star);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = listEntity.getNickName();
        if (name != null && !TextUtils.isEmpty(name)) {
            if (name.length() == 1) {
                name = name + "";
            } else {
                String first = name.substring(0, 1);
                String last = name.substring(name.length() - 1);
                name = first + "**" + last;
            }
            holder.store_evaluate_tv_nickname.setText(name);
        }
        if (!TextUtils.isEmpty(listEntity.getCreateTime() + "")) {
            holder.store_evaluate_tv_time.setText(formatDate(listEntity.getCreateTime()));
        }
        if (!TextUtils.isEmpty(listEntity.getDescription())) {
            holder.store_evaluate_tv_content.setText(listEntity.getDescription());
        }
        if (!TextUtils.isEmpty(listEntity.getHeadImgUrl())) {
            bitmapUtils.display(holder.store_evaluate_civ_head, listEntity.getHeadImgUrl());
        }
       String[] imgs = listEntity.getImgs().split(",");
        int imgCount = imgs.length;
        if (imgCount == 5) {
            holder.store_evaluate_ll_piclist.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv5.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv4.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv3.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv2.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(holder.store_evaluate_iv5, imgs[4]);
            bitmapUtils1.display(holder.store_evaluate_iv4, imgs[3]);
            bitmapUtils1.display(holder.store_evaluate_iv3, imgs[2]);
            bitmapUtils1.display(holder.store_evaluate_iv2, imgs[1]);
            bitmapUtils1.display(holder.store_evaluate_iv1, imgs[0]);
        } else if (imgCount == 4) {
            holder.store_evaluate_ll_piclist.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv5.setVisibility(View.GONE);
            holder.store_evaluate_iv4.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv3.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv2.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(holder.store_evaluate_iv4, imgs[3]);
            bitmapUtils1.display(holder.store_evaluate_iv3, imgs[2]);
            bitmapUtils1.display(holder.store_evaluate_iv2, imgs[1]);
            bitmapUtils1.display(holder.store_evaluate_iv1, imgs[0]);
        } else if (imgCount == 3) {
            holder.store_evaluate_ll_piclist.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv5.setVisibility(View.GONE);
            holder.store_evaluate_iv4.setVisibility(View.GONE);
            holder.store_evaluate_iv3.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv2.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(holder.store_evaluate_iv3, imgs[2]);
            bitmapUtils1.display(holder.store_evaluate_iv2, imgs[1]);
            bitmapUtils1.display(holder.store_evaluate_iv1, imgs[0]);
        } else if (imgCount == 2) {
            holder.store_evaluate_ll_piclist.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv5.setVisibility(View.GONE);
            holder.store_evaluate_iv4.setVisibility(View.GONE);
            holder.store_evaluate_iv3.setVisibility(View.GONE);
            holder.store_evaluate_iv2.setVisibility(View.VISIBLE);
            holder.store_evaluate_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(holder.store_evaluate_iv2, imgs[1]);
            bitmapUtils1.display(holder.store_evaluate_iv1, imgs[0]);
        } else if (imgCount == 1) {
            if (!TextUtils.isEmpty(imgs[0])){
                holder.store_evaluate_ll_piclist.setVisibility(View.VISIBLE);
            }else {
                holder.store_evaluate_ll_piclist.setVisibility(View.GONE);
            }
            holder.store_evaluate_iv5.setVisibility(View.GONE);
            holder.store_evaluate_iv4.setVisibility(View.GONE);
            holder.store_evaluate_iv3.setVisibility(View.GONE);
            holder.store_evaluate_iv2.setVisibility(View.GONE);
            holder.store_evaluate_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(holder.store_evaluate_iv1, imgs[0]);
        } else if (imgCount == 0) {
            holder.store_evaluate_ll_piclist.setVisibility(View.GONE);
            holder.store_evaluate_iv5.setVisibility(View.GONE);
            holder.store_evaluate_iv4.setVisibility(View.GONE);
            holder.store_evaluate_iv3.setVisibility(View.GONE);
            holder.store_evaluate_iv2.setVisibility(View.GONE);
            holder.store_evaluate_iv1.setVisibility(View.GONE);
        }

        int score = Integer.parseInt(listEntity.getScore());
        if (score == 1) {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 2) {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 3) {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 4) {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 5) {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_focus);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_focus);
        } else {
            holder.store_evaluate_ivstart1.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart2.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            holder.store_evaluate_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView store_evaluate_civ_head;
        LinearLayout store_evaluate_ll_star, store_evaluate_ll_piclist;
        ImageView store_evaluate_ivstart1, store_evaluate_ivstart2, store_evaluate_ivstart3, store_evaluate_ivstart4, store_evaluate_ivstart5, store_evaluate_iv1, store_evaluate_iv2, store_evaluate_iv3, store_evaluate_iv4, store_evaluate_iv5;
        TextView store_evaluate_tv_nickname, store_evaluate_tv_time, store_evaluate_tv_content;
    }

    private String formatDate(String str) {
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(gc.getTime());
    }
}