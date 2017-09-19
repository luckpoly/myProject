package com.brandsh.tiaoshi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.model.EvaluateJsonData1;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.widget.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class EvaluateDetailActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.evaluate_detail_ivBack)
    ImageView evaluate_detail_ivBack;
    @ViewInject(R.id.evaluate_detail_civ_head)
    CircleImageView evaluate_detail_civ_head;
    @ViewInject(R.id.evaluate_detail_tv_nickname)
    TextView evaluate_detail_tv_nickname;
    @ViewInject(R.id.evaluate_detail_ivstart1)
    ImageView evaluate_detail_ivstart1;
    @ViewInject(R.id.evaluate_detail_ivstart2)
    ImageView evaluate_detail_ivstart2;
    @ViewInject(R.id.evaluate_detail_ivstart3)
    ImageView evaluate_detail_ivstart3;
    @ViewInject(R.id.evaluate_detail_ivstart4)
    ImageView evaluate_detail_ivstart4;
    @ViewInject(R.id.evaluate_detail_ivstart5)
    ImageView evaluate_detail_ivstart5;
    @ViewInject(R.id.evaluate_detail_tv_time)
    TextView evaluate_detail_tv_time;
    @ViewInject(R.id.evaluate_detail_tv_content)
    TextView evaluate_detail_tv_content;
    @ViewInject(R.id.evaluate_detail_ll_piclist)
    LinearLayout evaluate_detail_ll_piclist;
    @ViewInject(R.id.evaluate_detail_iv1)
    ImageView evaluate_detail_iv1;
    @ViewInject(R.id.evaluate_detail_iv2)
    ImageView evaluate_detail_iv2;
    @ViewInject(R.id.evaluate_detail_iv3)
    ImageView evaluate_detail_iv3;
    @ViewInject(R.id.evaluate_detail_iv4)
    ImageView evaluate_detail_iv4;
    @ViewInject(R.id.evaluate_detail_iv5)
    ImageView evaluate_detail_iv5;

    private EvaluateJsonData1.DataBean.ListBean listEntity;
    private BitmapUtils bitmapUtils;
    private BitmapUtils bitmapUtils1;
    private String[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);

        init();
    }

    private void init() {
        bitmapUtils = TiaoshiApplication.getHeadImgBitmapUtils();
        bitmapUtils1 = TiaoshiApplication.getGlobalBitmapUtils();
        evaluate_detail_ivBack.setOnClickListener(this);
        evaluate_detail_iv1.setOnClickListener(this);
        evaluate_detail_iv2.setOnClickListener(this);
        evaluate_detail_iv3.setOnClickListener(this);
        evaluate_detail_iv4.setOnClickListener(this);
        evaluate_detail_iv5.setOnClickListener(this);

        listEntity = (EvaluateJsonData1.DataBean.ListBean) getIntent().getExtras().getSerializable("evaluateDetail");
        bitmapUtils.display(evaluate_detail_civ_head, listEntity.getHeadImgUrl());
        String name = listEntity.getNickName();
        if (!TextUtils.isEmpty(name)) {
            if (name.length() == 1) {
                name = name + "";
            } else {
                String first = name.substring(0, 1);
                String last = name.substring(name.length() - 1);
                name = first + "**" + last;
            }
            evaluate_detail_tv_nickname.setText(name);
        }
        if (!TextUtils.isEmpty(listEntity.getCreateTime())) {
            evaluate_detail_tv_time.setText(formatDate(listEntity.getCreateTime()));
        }
        evaluate_detail_tv_content.setText(listEntity.getDescription());
        int score = Integer.parseInt(listEntity.getScore());
        if (score == 1) {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 2) {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 3) {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 4) {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        } else if (score == 5) {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_focus);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_focus);
        } else {
            evaluate_detail_ivstart1.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart2.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart3.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart4.setImageResource(R.mipmap.rating_bar_list_normal);
            evaluate_detail_ivstart5.setImageResource(R.mipmap.rating_bar_list_normal);
        }

        imgs = listEntity.getImgs().split(",");
        int imgCount = imgs.length;
        if (imgCount == 5) {
            evaluate_detail_ll_piclist.setVisibility(View.VISIBLE);
            evaluate_detail_iv5.setVisibility(View.VISIBLE);
            evaluate_detail_iv4.setVisibility(View.VISIBLE);
            evaluate_detail_iv3.setVisibility(View.VISIBLE);
            evaluate_detail_iv2.setVisibility(View.VISIBLE);
            evaluate_detail_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(evaluate_detail_iv5, imgs[4]);
            bitmapUtils1.display(evaluate_detail_iv4, imgs[3]);
            bitmapUtils1.display(evaluate_detail_iv3, imgs[2]);
            bitmapUtils1.display(evaluate_detail_iv2, imgs[1]);
            bitmapUtils1.display(evaluate_detail_iv1, imgs[0]);
        } else if (imgCount == 4) {
            evaluate_detail_ll_piclist.setVisibility(View.VISIBLE);
            evaluate_detail_iv5.setVisibility(View.GONE);
            evaluate_detail_iv4.setVisibility(View.VISIBLE);
            evaluate_detail_iv3.setVisibility(View.VISIBLE);
            evaluate_detail_iv2.setVisibility(View.VISIBLE);
            evaluate_detail_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(evaluate_detail_iv4, imgs[3]);
            bitmapUtils1.display(evaluate_detail_iv3, imgs[2]);
            bitmapUtils1.display(evaluate_detail_iv2,imgs[1]);
            bitmapUtils1.display(evaluate_detail_iv1,imgs[0]);
        } else if (imgCount == 3) {
            evaluate_detail_ll_piclist.setVisibility(View.VISIBLE);
            evaluate_detail_iv5.setVisibility(View.GONE);
            evaluate_detail_iv4.setVisibility(View.GONE);
            evaluate_detail_iv3.setVisibility(View.VISIBLE);
            evaluate_detail_iv2.setVisibility(View.VISIBLE);
            evaluate_detail_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(evaluate_detail_iv3, imgs[2]);
            bitmapUtils1.display(evaluate_detail_iv2, imgs[1]);
            bitmapUtils1.display(evaluate_detail_iv1, imgs[0]);
        } else if (imgCount == 2) {
            evaluate_detail_ll_piclist.setVisibility(View.VISIBLE);
            evaluate_detail_iv5.setVisibility(View.GONE);
            evaluate_detail_iv4.setVisibility(View.GONE);
            evaluate_detail_iv3.setVisibility(View.GONE);
            evaluate_detail_iv2.setVisibility(View.VISIBLE);
            evaluate_detail_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(evaluate_detail_iv2, imgs[1]);
            bitmapUtils1.display(evaluate_detail_iv1, imgs[0]);
        } else if (imgCount == 1) {
            if (!TextUtils.isEmpty(imgs[0])){
                evaluate_detail_ll_piclist.setVisibility(View.VISIBLE);
            }else {
                evaluate_detail_ll_piclist.setVisibility(View.GONE);
            }

            evaluate_detail_iv5.setVisibility(View.GONE);
            evaluate_detail_iv4.setVisibility(View.GONE);
            evaluate_detail_iv3.setVisibility(View.GONE);
            evaluate_detail_iv2.setVisibility(View.GONE);
            evaluate_detail_iv1.setVisibility(View.VISIBLE);
            bitmapUtils1.display(evaluate_detail_iv1, imgs[0]);
        } else if (imgCount == 0) {
            evaluate_detail_ll_piclist.setVisibility(View.GONE);
        }
    }

    private String formatDate(String str) {
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(gc.getTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_detail_ivBack:
                finish();
                break;
            case R.id.evaluate_detail_iv1:
                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("No",0);
                intent.putExtra("array",imgs);
                startActivity(intent);
                break;
            case R.id.evaluate_detail_iv2:
                Intent intent1 = new Intent(this, ShowImageActivity.class);
                intent1.putExtra("No",1);
                intent1.putExtra("array",imgs);
                startActivity(intent1);
                break;
            case R.id.evaluate_detail_iv3:
                Intent intent2 = new Intent(this, ShowImageActivity.class);
                intent2.putExtra("No",2);
                intent2.putExtra("array",imgs);
                startActivity(intent2);
                break;
            case R.id.evaluate_detail_iv4:
                Intent intent3 = new Intent(this, ShowImageActivity.class);
                intent3.putExtra("No",3);
                intent3.putExtra("array",imgs);
                startActivity(intent3);
                break;
            case R.id.evaluate_detail_iv5:
                Intent intent4 = new Intent(this, ShowImageActivity.class);
                intent4.putExtra("No",4);
                intent4.putExtra("array",imgs);
                startActivity(intent4);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
