package com.brandsh.tiaoshi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.GuanZhuJsonData;
import com.brandsh.tiaoshi.model.GuanZhuListJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 16/2/22.
 */
public class GuanZhuListItemAdapter extends BaseAdapter {
    private List<GuanZhuListJsonData.DataBean.ListBean> resList;
    private Context context;
    private BitmapUtils bitmapUtils;
    private GuanZhuListJsonData.DataBean.ListBean listEntity;
    private int poi;
    private HttpUtils httpUtils;
    private HashMap requestMap;
    private Handler handler;

    public GuanZhuListItemAdapter(List<GuanZhuListJsonData.DataBean.ListBean> resList, Context context, Handler handler) {
        this.resList = resList;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        if (resList!=null){
            return resList.size();
        }
        return 0;
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
        bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        listEntity = resList.get(position);

        GuanZhuListItemViewHolder guanZhuListItemViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.guanzhu_list_item, null);
            guanZhuListItemViewHolder = new GuanZhuListItemViewHolder();
            guanZhuListItemViewHolder.guanzhu_list_item_iv = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_iv);
            guanZhuListItemViewHolder.guanzhu_list_item_tvName = (TextView) convertView.findViewById(R.id.guanzhu_list_item_tvName);
            guanZhuListItemViewHolder.guanzhu_list_item_tvNumber = (TextView) convertView.findViewById(R.id.guanzhu_list_item_tvNumber);
            guanZhuListItemViewHolder.guanzhu_list_item_tvAddress = (TextView) convertView.findViewById(R.id.guanzhu_list_item_tvAddress);
            guanZhuListItemViewHolder.guanzhu_list_item_tvDistance = (TextView) convertView.findViewById(R.id.guanzhu_list_item_tvDistance);
            guanZhuListItemViewHolder.guanzhu_list_item_llGuanZhu = (LinearLayout) convertView.findViewById(R.id.guanzhu_list_item_llGuanZhu);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1 = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_ivStar1);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2 = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_ivStar2);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3 = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_ivStar3);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4 = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_ivStar4);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5 = (ImageView) convertView.findViewById(R.id.guanzhu_list_item_ivStar5);
            convertView.setTag(guanZhuListItemViewHolder);
        }else {
            guanZhuListItemViewHolder = (GuanZhuListItemViewHolder) convertView.getTag();
        }
        bitmapUtils.display(guanZhuListItemViewHolder.guanzhu_list_item_iv, listEntity.getIcon());
        guanZhuListItemViewHolder.guanzhu_list_item_tvName.setText(listEntity.getName());
        guanZhuListItemViewHolder.guanzhu_list_item_tvAddress.setText(listEntity.getAddress());
        guanZhuListItemViewHolder.guanzhu_list_item_tvNumber.setText("有" + listEntity.getGoodsCount() + "种水果供您选择");
        int starCount = Integer.parseInt(listEntity.getStar());
        if(starCount == 1){
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 2){
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 3){
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if(starCount == 4){
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }else if(starCount == 5){
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_focus);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_focus);
        }else {
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar1.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar2.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar3.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar4.setImageResource(R.mipmap.rating_bar_list_normal);
            guanZhuListItemViewHolder.guanzhu_list_item_ivStar5.setImageResource(R.mipmap.rating_bar_list_normal);
        }
        String juli=Double.parseDouble(listEntity.getDistance())/1000.0+"";
        for (int i =0; i<juli.length(); i++){
            if (".".equals(juli.charAt(i)+"")){
                poi = i;
            }
        }
        //距离
        if (poi==1&&"0".equals(juli.charAt(0)+"")){
            if ("0".equals(juli.charAt(2)+"")){
                if ("0".equals(juli.charAt(3)+"")){
                    guanZhuListItemViewHolder.guanzhu_list_item_tvDistance.setText(juli.substring(4, poi + 4)+"m");
                }else {
                    guanZhuListItemViewHolder.guanzhu_list_item_tvDistance.setText(juli.substring(3, poi + 4)+"m");
                }
            }else {
                guanZhuListItemViewHolder.guanzhu_list_item_tvDistance.setText(juli.substring(2, poi + 4)+"m");
            }        }else {
            guanZhuListItemViewHolder.guanzhu_list_item_tvDistance.setText(juli.substring(0, poi+3)+"km");
        }
        guanZhuListItemViewHolder.guanzhu_list_item_llGuanZhu.setOnClickListener(new GuanZhuClickListener(position));
        return convertView;
    }

    private class GuanZhuClickListener implements View.OnClickListener{
        private int position;
        private AlertDialog.Builder builder;

        public GuanZhuClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            builder = new AlertDialog.Builder(context).setTitle("系统提示").setMessage("是否取消关注");
            httpUtils = TiaoshiApplication.getGlobalHttpUtils();
            requestMap=new HashMap<>();

            requestMap.put("token", TiaoshiApplication.globalToken);
            requestMap.put("shopId", resList.get(position).getShopId());
            requestMap.put("actReq","123456");
            requestMap.put("actTime",System.currentTimeMillis()/1000+"");
            String sign= SignUtil.getSign(requestMap);
            requestMap.put("sign", Md5.toMd5(sign));
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    OkHttpManager.postAsync(G.Host.GUANZHU, requestMap, new MyCallBack(3, context, new GuanZhuJsonData(), handler));
                }
            }).setNegativeButton("取消", null).create().show();
        }
    }

    private class GuanZhuListItemViewHolder{
        private ImageView guanzhu_list_item_iv;
        private TextView guanzhu_list_item_tvAddress;
        private TextView guanzhu_list_item_tvNumber;
        private TextView guanzhu_list_item_tvName;
        private TextView guanzhu_list_item_tvDistance;
        private LinearLayout guanzhu_list_item_llGuanZhu;
        private ImageView guanzhu_list_item_ivStar1;
        private ImageView guanzhu_list_item_ivStar2;
        private ImageView guanzhu_list_item_ivStar3;
        private ImageView guanzhu_list_item_ivStar4;
        private ImageView guanzhu_list_item_ivStar5;
    }
}
