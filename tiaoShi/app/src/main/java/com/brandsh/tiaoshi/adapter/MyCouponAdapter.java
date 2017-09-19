package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.brandsh.tiaoshi.model.CouponModel;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class MyCouponAdapter extends BaseAdapter {
    private Context context;
    private List<CouponModel.DataBean> mList;

    public MyCouponAdapter(Context context, List<CouponModel.DataBean> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CouponHolder couponHolder = null;
        final CouponModel.DataBean entity = mList.get(position);
        if (convertView == null) {
            couponHolder = new CouponHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_item, null);
            couponHolder.ll_couponbg = (LinearLayout) convertView.findViewById(R.id.ll_coupon_bg);
            couponHolder.tv_rmb = (TextView) convertView.findViewById(R.id.tv_rmb);
            couponHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            couponHolder.tv_discount = (TextView) convertView.findViewById(R.id.tv_discount_price);
            couponHolder.tv_condition = (TextView) convertView.findViewById(R.id.tv_condition_price);
            couponHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            couponHolder.iv_coupon_icon = (ImageView) convertView.findViewById(R.id.iv_coupon_icon);
            couponHolder.iv_yiguoqi = (ImageView) convertView.findViewById(R.id.iv_yiguoqi);
            convertView.setTag(couponHolder);
        } else {
            couponHolder = (CouponHolder) convertView.getTag();
        }
        if (Double.parseDouble(entity.getUseDataEnd()) < System.currentTimeMillis() / 1000) {
            couponHolder.tv_rmb.setTextColor(Color.parseColor("#c6c6c6"));
            couponHolder.tv_discount.setTextColor(Color.parseColor("#c6c6c6"));
            couponHolder.iv_yiguoqi.setVisibility(View.VISIBLE);
        } else {
            couponHolder.tv_rmb.setTextColor(Color.parseColor("#f29b02"));
            couponHolder.tv_discount.setTextColor(Color.parseColor("#f29b02"));
            couponHolder.iv_yiguoqi.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(entity.getReduceCost())) {
            couponHolder.tv_discount.setText(Double.parseDouble(entity.getReduceCost()) / 100 + "");
        }
        couponHolder.tv_date.setText("·  使用期限 " + formatDate(entity.getUseDataBegin() + "") + "-" + formatDate(entity.getUseDataEnd() + ""));
        TiaoshiApplication.getGlobalBitmapUtils().display(couponHolder.iv_coupon_icon, entity.getLogoUrl());
        //判断优惠券类型
        switch (entity.getCouponType()) {
            case "CASH":
                //代金券
                couponHolder.tv_name.setText("代金券");
                couponHolder.tv_condition.setText("·  满" + Double.parseDouble(entity.getLeastCost()) / 100 + "可使用");
                couponHolder.tv_rmb.setText("￥");
                couponHolder.tv_rmb.setVisibility(View.VISIBLE);
                couponHolder.tv_discount.setVisibility(View.VISIBLE);
                break;
            case "DISCOUNT":
                //折扣券
                couponHolder.tv_name.setText("折扣券");
                if (TextUtils.isEmpty(entity.getLeastCost())) {
                    couponHolder.tv_condition.setText("·  满0可使用");
                } else {
                    couponHolder.tv_condition.setText("·  满" + Double.parseDouble(entity.getLeastCost()) / 100 + "可使用");
                }
                couponHolder.tv_rmb.setText("折");
                couponHolder.tv_discount.setText(entity.getDiscount());
                couponHolder.tv_rmb.setVisibility(View.VISIBLE);
                couponHolder.tv_discount.setVisibility(View.VISIBLE);
                break;
            case "GIFT":
                //兑换券
                couponHolder.tv_name.setText("兑换券");
                couponHolder.tv_condition.setText("·  " + entity.getGift().split("#")[1]);
                couponHolder.tv_rmb.setVisibility(View.GONE);
                couponHolder.tv_discount.setVisibility(View.GONE);
                break;
            case "GROUPON":
                //团购券
                couponHolder.tv_name.setText("团购券");
                break;
            case "GENERAL_COUPON":
                //优惠券
                couponHolder.tv_name.setText("优惠券");
                break;
        }

        return convertView;
    }

    class CouponHolder {
        LinearLayout ll_couponbg;
        TextView tv_rmb, tv_name;
        TextView tv_discount;
        TextView tv_condition;
        TextView tv_date;
        ImageView iv_coupon_icon, iv_yiguoqi;
    }

    private String formatDate(String str) {
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(gc.getTime());
    }

}
