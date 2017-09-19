package com.brandsh.tiaoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.HomeStoreModel1;
import com.brandsh.tiaoshi.utils.DensityUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by XianXianGe on 2016/1/7.
 */
public class HomeStoreListAdapter extends BaseAdapter {
    private List<HomeStoreModel1.DataBean.ListBean> resList;
    private Context context;
    private BitmapUtils bitmapUtils;
    private HomeStoreModel1.DataBean.ListBean listEntity;
    private int poi;
    private int issend=999;
    public HomeStoreListAdapter(List<HomeStoreModel1.DataBean.ListBean> resList, Context context) {
        this.resList = resList;
        this.context = context;
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
    }

    @Override
    public int getCount() {
        if(resList!=null){
            if (resList.size()==0){
                return 1;
            }
            return resList.size();
        }
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return resList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position>=resList.size()){
            View view=LayoutInflater.from(context).inflate(R.layout.item_nodata, null);
            view.setOnClickListener(null);
            view.setTag("noData");
            return view;
        }
        listEntity = resList.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null||convertView.getTag().equals("noData")){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
            viewHolder.home_item_iv = (ImageView) convertView.findViewById(R.id.home_item_iv);
            viewHolder.home_item_tvNotice = (TextView) convertView.findViewById(R.id.home_item_tvNotice);
            viewHolder.home_item_tvName = (TextView) convertView.findViewById(R.id.home_item_tvName);
            viewHolder.home_item_tvMoney = (TextView) convertView.findViewById(R.id.home_item_tvMoney);
            viewHolder.home_item_tvNumber = (TextView) convertView.findViewById(R.id.home_item_tvNumber);
            viewHolder.home_item_ivStar1 = (ImageView) convertView.findViewById(R.id.home_item_ivStar1);
            viewHolder.home_item_ivStar2 = (ImageView) convertView.findViewById(R.id.home_item_ivStar2);
            viewHolder.home_item_ivStar3 = (ImageView) convertView.findViewById(R.id.home_item_ivStar3);
            viewHolder.home_item_ivStar4 = (ImageView) convertView.findViewById(R.id.home_item_ivStar4);
            viewHolder.home_item_ivStar5 = (ImageView) convertView.findViewById(R.id.home_item_ivStar5);
            viewHolder.home_item_tvDistance = (TextView) convertView.findViewById(R.id.home_item_tvDistance);
            viewHolder.home_item_tvratingNo= (TextView) convertView.findViewById(R.id.tv_ratingNo);
            viewHolder.home_item_llminus= (LinearLayout) convertView.findViewById(R.id.home_item_llminus);
            viewHolder.home_item_ispay= (LinearLayout) convertView.findViewById(R.id.home_item_ispay);
            viewHolder.home_item_tvCoupon= (TextView) convertView.findViewById(R.id.home_item_tvCoupon);
            viewHolder.home_item_tvsalesvolume= (TextView) convertView.findViewById(R.id.tv_salesvolume);
            viewHolder.iv_recommend=(ImageView)convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_istitle=(TextView)convertView.findViewById(R.id.tv_istitle);
            viewHolder.home_item_tvNumber0 = (TextView) convertView.findViewById(R.id.home_item_tvNumber0);
            viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //置顶推荐
        if (listEntity.getTop().equals("1")){
            viewHolder.iv_recommend.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_recommend.setVisibility(View.GONE);
        }
        if (issend==999){
            if (listEntity.getIsSend().equals("fales")){
                issend=position;
            }
        }
        if (position<issend){
            convertView.setAlpha(1f);
            viewHolder.tv_istitle.setVisibility(View.GONE);
            viewHolder.home_item_tvNumber0.setVisibility(View.GONE);
            viewHolder.home_item_tvNumber.setVisibility(View.VISIBLE);
        }else if(position==issend) {
            viewHolder.rl_item.setAlpha(0.4f);
            viewHolder.tv_istitle.setVisibility(View.VISIBLE);
            viewHolder.home_item_tvNumber0.setVisibility(View.VISIBLE);
            viewHolder.home_item_tvNumber.setVisibility(View.GONE);
        }else {
            convertView.setAlpha(0.4f);
            viewHolder.tv_istitle.setVisibility(View.GONE);
            viewHolder.home_item_tvNumber0.setVisibility(View.VISIBLE);
            viewHolder.home_item_tvNumber.setVisibility(View.GONE);
        }
        bitmapUtils.display(viewHolder.home_item_iv, listEntity.getIcon());
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(context,R.anim. viewbig);
        convertView.setAnimation(mAnimation );
        mAnimation.start();
        viewHolder.home_item_tvNotice.setText(listEntity.getGoods_sc_count()+"");
        viewHolder.home_item_tvratingNo.setText(listEntity.getStar()+".0");
        viewHolder.home_item_tvsalesvolume.setText("月售"+listEntity.getTotalMonthSales()+"单");
        if (listEntity.getGoods_sc_count() == 0){
            viewHolder.home_item_tvNotice.setVisibility(View.GONE);
        }else {
            viewHolder.home_item_tvNotice.setVisibility(View.VISIBLE);
        }
        String coupon="在线支付";

        if (listEntity.getShopPreferential().size()==0 ){
            viewHolder.home_item_llminus.setVisibility(View.GONE);
        }else if (listEntity.getShopPreferential().size()>0){
            viewHolder.home_item_llminus.setVisibility(View.VISIBLE);
            for (int i=0;i<listEntity.getShopPreferential().size();i++){
                if(i<listEntity.getShopPreferential().size()-1){
                    coupon=coupon+"满"+listEntity.getShopPreferential().get(i).getFull()+"减"+listEntity.getShopPreferential().get(i).getSubtract()+",";
                }else {
                    coupon=coupon+"满"+listEntity.getShopPreferential().get(i).getFull()+"减"+listEntity.getShopPreferential().get(i).getSubtract();
                }
                if (i==1){
                    break;
                }
            }
        }
        viewHolder.home_item_tvCoupon.setText(coupon);
        viewHolder.home_item_tvName.setText(listEntity.getName());
        viewHolder.home_item_tvMoney.setText("满￥"+listEntity.getFreeSend()+"元免费配送");
        viewHolder.home_item_tvNumber.setText("有" + listEntity.getGoodsCount() + "种水果供您选择");
        if (null!=listEntity.getBalancePay()&&listEntity.getBalancePay().equals("YES")){
            viewHolder.home_item_ispay.setVisibility(View.VISIBLE);
        }else {
            viewHolder.home_item_ispay.setVisibility(View.GONE);
        }
        DensityUtil.setDistance(viewHolder.home_item_tvDistance,"",listEntity.getDistance());
        int starCount = Integer.parseInt(listEntity.getStar());
        if(starCount == 1){
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 2){
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 3){
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if(starCount == 4){
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 5){
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_focus);
        }else {
            viewHolder.home_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            viewHolder.home_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }
        return convertView;
    }


    class ViewHolder{
        private ImageView home_item_iv,iv_recommend;
        private TextView home_item_tvNotice,tv_istitle,home_item_tvNumber0;
        private ImageView home_item_tag;
        private TextView home_item_tvName;
        private TextView home_item_tvMoney;
        private TextView home_item_tvNumber;
        private ImageView home_item_ivStar1;
        private ImageView home_item_ivStar2;
        private ImageView home_item_ivStar3;
        private ImageView home_item_ivStar4;
        private ImageView home_item_ivStar5;
        private TextView home_item_tvDistance;
        private TextView home_item_tvratingNo;
        private LinearLayout home_item_llminus;
        private LinearLayout home_item_ispay;
        private TextView home_item_tvCoupon;
        private TextView home_item_tvsalesvolume;
        private RelativeLayout rl_item;
    }
}
