package com.brandsh.tiaoshi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.StoreDetailActivity;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.DiyShoppingCartJsonData;
import com.brandsh.tiaoshi.model.StoreProductJsonData;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.BitmapUtils;

import java.util.LinkedList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by libokang on 15/10/26.
 */
public class StoreProductAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context context;
    private List<StoreProductJsonData.DataBean.ListBean> mDataset;
    private Handler handler;
    private String shop_id;
    private StoreProductJsonData.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private AlertDialog.Builder builder;
    private BitmapUtils tagBitmapUtils;
    private BadgeView badgeView;
    private ImageView buyImg;
    private ViewGroup anim_mask_layout;
    private Dialog chooseWeightDialog;
    int type = 0;

    public StoreProductAdapter(final Context context, List<StoreProductJsonData.DataBean.ListBean> mDataset, final Handler handler, String shop_id, BadgeView badgeView) {
        this.context = context;
        this.mDataset = mDataset;
        this.handler = handler;
        this.shop_id = shop_id;
        this.badgeView = badgeView;
        this.bitmapUtils = TiaoshiApplication.getGlobalBitmapUtils();
        this.tagBitmapUtils = new BitmapUtils(context);
        builder = new AlertDialog.Builder(context).setTitle("系统提示").setMessage("当前购物车还有其他商铺商品,请清空后再添加").setNegativeButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_count(0);
                TiaoshiApplication.diyShoppingCartJsonData.setGoods_total_cash(0);
                TiaoshiApplication.diyShoppingCartJsonData.setShop_id(null);
                TiaoshiApplication.diyShoppingCartJsonData.setGoodsList(new LinkedList<DiyShoppingCartJsonData.GoodsListEntity>());
                Intent intent = new Intent("clearCount");
                context.sendBroadcast(intent);
            }
        }).setPositiveButton("取消", null);
    }

    @Override
    public int getCount() {
        return mDataset.size();
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
        ViewHolder viewHolder = null;
        listEntity = mDataset.get(position);
        int goods_sc_count = listEntity.getGoods_sc_count();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.store_product_item, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.store_product_tvUnit = (TextView) convertView.findViewById(R.id.store_product_tvUnit);
            viewHolder.store_product_tvOldPrice = (TextView) convertView.findViewById(R.id.store_product_tvOldPrice);
            viewHolder.store_product_view = convertView.findViewById(R.id.store_product_view);
            viewHolder.store_product_ivAdd = (ImageView) convertView.findViewById(R.id.store_product_ivAdd);
            viewHolder.store_product_tvCount = (TextView) convertView.findViewById(R.id.store_product_tvCount);
            viewHolder.store_product_ivDelete = (ImageView) convertView.findViewById(R.id.store_product_ivDelete);
            viewHolder.store_product_tvSaleCount = (TextView) convertView.findViewById(R.id.store_product_tvSaleCount);
            viewHolder.store_product_juiceDelete = (ImageView) convertView.findViewById(R.id.store_product_juiceDelete);
            viewHolder.tv_pack_choose = (TextView) convertView.findViewById(R.id.tv_pack_choose);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bitmapUtils.display(viewHolder.img, listEntity.getThumImg());
        /**
         * 动画
         */
        Animation mAnimation = null ;
        /**
         * 显示动画的ImageView
         */
        mAnimation = AnimationUtils.loadAnimation(context,R.anim. picturebig);
        viewHolder.img.setAnimation(mAnimation );
        mAnimation.start();
        viewHolder.title.setText(listEntity.getGoodsName());
        if (TextUtils.isEmpty(listEntity.getSalesNum()) || listEntity.getSalesNum().equals("1")) {
            viewHolder.store_product_tvUnit.setText("元/" + listEntity.getSalesUnit());
        } else {
            viewHolder.store_product_tvUnit.setText("元/" + listEntity.getSalesNum() + listEntity.getSalesUnit());
        }
        viewHolder.store_product_tvSaleCount.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(listEntity.getTotalSoldOut())) {

            viewHolder.store_product_tvSaleCount.setText("销量:" + listEntity.getTotalSoldOut());
        } else {
            viewHolder.store_product_tvSaleCount.setText("销量:0");
        }
        if (!TextUtils.isEmpty(listEntity.getPack())) {
            viewHolder.tv_pack_choose.setVisibility(View.VISIBLE);
            viewHolder.store_product_ivAdd.setVisibility(View.GONE);
        } else {
            viewHolder.tv_pack_choose.setVisibility(View.GONE);
            viewHolder.store_product_ivAdd.setVisibility(View.VISIBLE);
        }
        //设置原价
        if ("0".equals(listEntity.getPromotePrice()) || TextUtils.isEmpty(listEntity.getPromotePrice())) {
            viewHolder.price.setText("￥" + listEntity.getPrice());
            viewHolder.store_product_tvOldPrice.setVisibility(View.GONE);
            viewHolder.store_product_view.setVisibility(View.GONE);
        } else {
            viewHolder.store_product_tvOldPrice.setVisibility(View.VISIBLE);
            viewHolder.store_product_view.setVisibility(View.VISIBLE);
            viewHolder.store_product_tvOldPrice.setText("￥" + listEntity.getPrice());
            viewHolder.price.setText("￥" + listEntity.getPromotePrice());
        }

        //设置加入购物车
        viewHolder.store_product_tvCount.setText(goods_sc_count + "");
        if (goods_sc_count == 0) {
            viewHolder.store_product_tvCount.setVisibility(View.GONE);
            viewHolder.store_product_ivDelete.setVisibility(View.GONE);
            viewHolder.store_product_juiceDelete.setVisibility(View.GONE);
        } else {
            viewHolder.store_product_tvCount.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(listEntity.getPack())) {
                viewHolder.store_product_juiceDelete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.store_product_ivDelete.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.store_product_ivAdd.setOnClickListener(new AddClickListener(position, goods_sc_count));
        viewHolder.store_product_ivDelete.setOnClickListener(new DeleteClickListener(position, goods_sc_count));
        viewHolder.tv_pack_choose.setOnClickListener(new AddjuiceClickListener(position, goods_sc_count, listEntity.getPack(), listEntity.getGoodsName()));
        viewHolder.store_product_juiceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(context, "多规格商品只能到购物车删除哦");
            }
        });
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_header_view, parent, false);
        }
        ((TextView) (convertView)).setText(mDataset.get(position).getCustomCategoryName());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return Integer.parseInt(mDataset.get(position).getCustomCategoryId());
    }

    private class AddjuiceClickListener implements View.OnClickListener {
        private int position;
        private int goods_sc_count;
        private String pack;
        private String name;
        private String[] juicepack;

        public AddjuiceClickListener(int position, int goods_sc_count, String pack, String name) {
            this.position = position;
            this.goods_sc_count = goods_sc_count;
            this.pack = pack;
            this.name = name;
            juicepack = pack.split(",");
        }

        @Override
        public void onClick(View v) {
            final Dialog finalDialog;
            View view = LayoutInflater.from(context).inflate(R.layout.choose_goods_dialog, null);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_chooseWeight);
            TextView tv_dismiss_pack = (TextView) view.findViewById(R.id.tv_dismiss_pack);
            TextView tv_addjuice = (TextView) view.findViewById(R.id.tv_addjuice);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(name);
            RadioButton rb1 = (RadioButton) view.findViewById(R.id.rb1);
            RadioButton rb2 = (RadioButton) view.findViewById(R.id.rb2);
            RadioButton rb3 = (RadioButton) view.findViewById(R.id.rb3);
            switch (juicepack.length) {
                case 1:
                    rb1.setVisibility(View.VISIBLE);
                    rb1.setText(juicepack[0]);
                    break;
                case 2:
                    rb1.setVisibility(View.VISIBLE);
                    rb2.setVisibility(View.VISIBLE);
                    rb1.setText(juicepack[0]);
                    rb2.setText(juicepack[1]);
                    break;
                case 3:
                    rb1.setVisibility(View.VISIBLE);
                    rb2.setVisibility(View.VISIBLE);
                    rb3.setVisibility(View.VISIBLE);
                    rb1.setText(juicepack[0]);
                    rb2.setText(juicepack[1]);
                    rb3.setText(juicepack[2]);
                    break;
            }

            radioGroup.check(R.id.rb1);
            type = 1;
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb1:
                            type = 1;
                            break;
                        case R.id.rb2:
                            type = 2;
                            break;
                        case R.id.rb3:
                            type = 3;
                            break;
                    }
                }
            });
            builder1.setCancelable(false);
            builder1.setView(view);
            finalDialog = builder1.show();
            tv_dismiss_pack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalDialog.dismiss();
                }
            });
            tv_addjuice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() != null) {
                        if (!TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(shop_id)) {
                            builder.create().show();
                            return;
                        }
                    } else {
                        TiaoshiApplication.diyShoppingCartJsonData.setShop_id(shop_id);
                    }
                    ++goods_sc_count;
                    mDataset.get(position).setGoods_sc_count(goods_sc_count);
                    notifyDataSetChanged();
                    finalDialog.dismiss();
                    Message message = handler.obtainMessage();
                    message.obj = position;
                    message.arg1 = type;
                    message.what = 3;
                    handler.sendMessage(message);
                }
            });
        }
    }

    private class AddClickListener implements View.OnClickListener {
        private int position;
        private int goods_sc_count;

        public AddClickListener(int position, int goods_sc_count) {
            this.position = position;
            this.goods_sc_count = goods_sc_count;
        }

        @Override
        public void onClick(View v) {
            if (TiaoshiApplication.diyShoppingCartJsonData.getShop_id() != null) {
                if (!TiaoshiApplication.diyShoppingCartJsonData.getShop_id().equals(shop_id)) {
                    builder.create().show();
                    return;
                }
            } else {
                TiaoshiApplication.diyShoppingCartJsonData.setShop_id(shop_id);
            }
            ++goods_sc_count;
            mDataset.get(position).setGoods_sc_count(goods_sc_count);
            notifyDataSetChanged();
            int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
            v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
            buyImg = new ImageView(context);
            buyImg.setImageResource(R.mipmap.icon_btn_add);// 设置buyImg的图片
            setAnim(buyImg, start_location);// 开始执行动画
            Message message = handler.obtainMessage();
            message.obj = position;
            message.what = 3;
            handler.sendMessage(message);

        }
    }

    private class DeleteClickListener implements View.OnClickListener {
        private int position;
        private int goods_sc_count;

        public DeleteClickListener(int position, int goods_sc_count) {
            this.position = position;
            this.goods_sc_count = goods_sc_count;
        }

        @Override
        public void onClick(View v) {
            --goods_sc_count;
            mDataset.get(position).setGoods_sc_count(goods_sc_count);
            notifyDataSetChanged();
            Message message = handler.obtainMessage();
            message.obj = position;
            message.what = 4;
            handler.sendMessage(message);
        }
    }

    public class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView price, tv_pack_choose;
        private TextView store_product_tvOldPrice;
        private View store_product_view;
        private TextView store_product_tvUnit;
        private ImageView store_product_ivAdd;
        private TextView store_product_tvCount;
        private ImageView store_product_ivDelete, store_product_juiceDelete;
        private TextView store_product_tvSaleCount;
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                start_location);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
        badgeView.getLocationInWindow(end_location);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }
}
